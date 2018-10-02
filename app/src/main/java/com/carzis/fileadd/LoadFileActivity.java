package com.carzis.fileadd;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.util.AndroidUtility;
import com.carzis.util.custom.view.TroubleTypeBtn;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadFileActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = LoadFileActivity.class.getSimpleName();

    private static final int CHOOSE_FILE_REQUEST_CODE = 42;


    private TroubleTypeBtn remoteBtn;
    private TroubleTypeBtn localBtn;
    private LinearLayout linkContainerView;
    private EditText linkEdtxt;
    private ImageView fileImageView;
    private TextView fileNameTxt;
    private Button addFileBtn;
    private ImageButton backBtn;
    private ProgressBar progressBar;
    private ImageView btnPaste;

    private File file;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoadFileActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file);

        remoteBtn = findViewById(R.id.btn_remote_file);
        localBtn = findViewById(R.id.btn_local_file);
        linkContainerView = findViewById(R.id.link_container);
        linkEdtxt = findViewById(R.id.link_edtxt);
        fileImageView = findViewById(R.id.file_image);
        fileNameTxt = findViewById(R.id.file_name);
        addFileBtn = findViewById(R.id.add_file_btn);
        backBtn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBar);
        btnPaste = findViewById(R.id.btn_paste);

        remoteBtn.setOnClickListener(this);
        localBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        addFileBtn.setOnClickListener(this);
        fileImageView.setOnClickListener(this);
        btnPaste.setOnClickListener(this);

        remoteBtn.callOnClick();


    }


//    private void showFileChooser() {
//
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        //intent.setType("*/*");      //all files
//        intent.setType("text/*");   //XML file only
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        try {
//            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), CHOOSE_FILE_REQUEST_CODE);
//        } catch (android.content.ActivityNotFoundException ex) {
//            // Potentially direct the user to the Market with a Dialog
//            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void performFileSearch() {
        Intent intent = new Intent();
        intent.setType("*/*");
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent = Intent.createChooser(intent, "Select file");
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            String[] mimetypes = { "*/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        }
        startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
    }

    public void writeFileToAppFolder(File file) {
        FileOutputStream outputStream;
        try {
            byte[] fileContent = read(file);
            String fileLoc = AndroidUtility.getAppFolderPath() + file.getName();
            outputStream = new FileOutputStream(fileLoc);
            outputStream.write(fileContent);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_FILE_REQUEST_CODE
                && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                file = new File(uri.getPath());
                fileNameTxt.setText(file.getName());
            }
        }
    }

    public byte[] read(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_remote_file:
                linkContainerView.setVisibility(View.VISIBLE);

                fileImageView.setVisibility(View.GONE);
                fileNameTxt.setVisibility(View.GONE);

                remoteBtn.setSelected(true);
                localBtn.setSelected(false);
                break;
            case R.id.btn_local_file:
                performFileSearch();

                linkContainerView.setVisibility(View.GONE);

                fileImageView.setVisibility(View.VISIBLE);
                fileNameTxt.setVisibility(View.VISIBLE);

                remoteBtn.setSelected(false);
                localBtn.setSelected(true);
                break;
            case R.id.add_file_btn:
                if (remoteBtn.isSelected()) {
                    //        new DownloadTask(this).execute("https://elm3.ru/wp-content/uploads/2018/04/Yanvar_5_1.csv");
                    String url = linkEdtxt.getText().toString();
                    if (!url.isEmpty()) {
                        if (URLUtil.isValidUrl(url))
                            new DownloadTask(this).execute(url);
                        else
                            Toast.makeText(this, "Not a valid URL", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Please enter a link", Toast.LENGTH_SHORT).show();
                } else if (localBtn.isSelected()) {
                    if (file != null) {
                        if (file.getName().contains("csv"))
                            writeFileToAppFolder(file);
                        else
                            Toast.makeText(this, "Not a valid file", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
                        performFileSearch();
                    }
                }
                break;
            case R.id.file_image:
                performFileSearch();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_paste:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData abc = clipboard.getPrimaryClip();
                if (abc != null) {
                    ClipData.Item item = abc.getItemAt(0);
                    String textBuf = item.getText().toString();

                    if (!textBuf.isEmpty())
                        linkEdtxt.setText(textBuf);
                }
                break;
        }
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    public class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            String fileName;

            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                String[] urlArr = url.getPath().split("/");
                fileName = urlArr[urlArr.length - 1];

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();


                AndroidUtility.createAppFolder(LoadFileActivity.this);

                String fileLoc = AndroidUtility.getAppFolderPath() + fileName;

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(fileLoc);

                Log.d(TAG, "doInBackground: " + fileLoc);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
//            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            progressBar.setIndeterminate(false);
            progressBar.setMax(100);
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
//            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }

    }

}
