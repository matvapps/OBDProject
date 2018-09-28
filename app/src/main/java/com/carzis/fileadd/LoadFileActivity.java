package com.carzis.fileadd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadFileActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = LoadFileActivity.class.getSimpleName();

    private TroubleTypeBtn remoteBtn;
    private TroubleTypeBtn localBtn;
    private LinearLayout linkContainerView;
    private EditText linkEdtxt;
    private ImageView fileImageView;
    private TextView fileNameTxt;
    private Button addFileBtn;
    private ImageButton backBtn;
    private ProgressBar progressBar;

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

        remoteBtn.setOnClickListener(this);
        localBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        fileImageView.setOnClickListener(this);

//        https://elm3.ru/wp-content/uploads/2018/04/Yanvar_5_1.csv

        new DownloadTask(this).execute("https://elm3.ru/wp-content/uploads/2018/04/Yanvar_5_1.csv");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_remote_file:
                if (remoteBtn.isSelected()) {
                    linkContainerView.setVisibility(View.VISIBLE);

                    remoteBtn.setSelected(true);
                    localBtn.setSelected(false);
                }
                break;
            case R.id.btn_local_file:
                if (remoteBtn.isSelected()) {
                    linkContainerView.setVisibility(View.GONE);

                    remoteBtn.setSelected(false);
                    localBtn.setSelected(true);
                }
                break;
            case R.id.add_file_btn:

                break;
            case R.id.file_image:

                break;
            case R.id.back_btn:
                finish();
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

                String fileLoc =  AndroidUtility.getAppFolderPath() + fileName;

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
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }

    }

}
