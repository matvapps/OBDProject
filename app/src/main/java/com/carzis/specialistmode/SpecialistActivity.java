package com.carzis.specialistmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.carzis.R;
import com.carzis.util.Utility;

import static com.carzis.connect.ConnectActivity.CONNECTION_TYPE_EXTRA;

public class SpecialistActivity extends AppCompatActivity implements View.OnClickListener{

    public final String TAG = SpecialistActivity.class.getSimpleName();

    private EditText initStringEdtxt;
    private RecyclerView fileListView;
    private Button selectAllBtn;
    private Button nextBtn;
    private ImageButton backBtn;

    private FileListAdapter fileListAdapter;
    private String connectionType;

    public static void start(Activity activity, String connectionType) {
        Intent intent = new Intent(activity, SpecialistActivity.class);
        intent.putExtra(CONNECTION_TYPE_EXTRA, connectionType);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        connectionType = getIntent().getStringExtra(CONNECTION_TYPE_EXTRA);

        initStringEdtxt = findViewById(R.id.init_string_edtxt);
        fileListView = findViewById(R.id.config_files_list);
        selectAllBtn = findViewById(R.id.btn_select_all);
        nextBtn = findViewById(R.id.btn_next);
        backBtn = findViewById(R.id.back_btn);

        selectAllBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        fileListAdapter = new FileListAdapter();

        fileListView.setLayoutManager(new LinearLayoutManager(this));
        fileListView.setAdapter(fileListAdapter);

        fileListAdapter.setItems(Utility.getAllFiles());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_select_all:
                if (!selectAllBtn.getText().equals("Unselect all")) {
                    fileListAdapter.selectAll(true);
                    selectAllBtn.setText("Unselect all");
                } else {
                    fileListAdapter.selectAll(false);
                    selectAllBtn.setText("Select all");
                }
                break;
            case R.id.btn_next:
                // TODO:
                //obdReader.setAdditionalPidCommands(Utility.getPidsFromFiles(fileListAdapter.getCheckedItems()));
                //obdReader.connectTo ... device
                break;
        }
    }

//    private ArrayList<PidNew> getPidsFromFiles(List<File> files) {
//        ArrayList<PidNew> pids = new ArrayList<>();
//
//        for (File file: files) {
//            try {
//                InputStream is = new FileInputStream(file);
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(is, Charset.forName("UTF-8"))
//                );
//
//                // Initialization
//                String line = "";
//
//                // Initialization
//                try {
//                    // Step over headers
//                    reader.readLine();
//
//                    // If buffer is not empty
//                    while ((line = reader.readLine()) != null) {
//                        Log.d(TAG,"Line: " + line);
//                        // use comma as separator columns of CSV
//                        String[] tokens = line.split(",");
//
//                        String name = tokens[0];
//                        String mode = tokens[1].substring(0, 2);
//                        String pid = tokens[1].substring(2);
//                        String equation = tokens[3];
//                        String units = tokens[6];
//                        String header = tokens[7];
//
//                        PidNew pidItem = new PidNew(pid, mode, name, equation, units, header);
//                        pids.add(pidItem);
//
//                        Log.d(TAG, "getPidsFromFiles: " + pidItem.getName());
//                    }
//
//                } catch (IOException e) {
//                    // Logs error with priority level
//                    Log.wtf(TAG, "Error reading data file on line" + line, e);
//
//                    // Prints throwable details
//                    e.printStackTrace();
//                }
//
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        return pids;
//    }
//
//    private List<File> getAllFiles() {
//        List<File> fileList = new ArrayList<>();
//        File directory = new File(AndroidUtility.getAppFolderPath());
//        File[] files = directory.listFiles();
//
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].getName().contains("csv"))
//                fileList.add(files[i]);
//        }
//        return fileList;
//    }
}
