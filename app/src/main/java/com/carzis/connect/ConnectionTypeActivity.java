package com.carzis.connect;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carzis.R;

import static com.carzis.connect.ConnectActivity.EXTRA_DEVICE_ADDRESS;
import static com.carzis.connect.ConnectActivity.EXTRA_DEVICE_NAME;

public class ConnectionTypeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_GET_DEVICE_DATA = 55;

    private Button btnWifiType;
    private Button btnBtType;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConnectionTypeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_type);

        btnWifiType = findViewById(R.id.btn_wifi_type);
        btnBtType = findViewById(R.id.btn_bt_type);

        btnWifiType.setOnClickListener(this);
        btnBtType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wifi_type:
                SelectModeActivity.start(ConnectionTypeActivity.this, ConnectActivity.CONNECTION_WIFI);
                finish();
                break;
            case R.id.btn_bt_type:
                SelectModeActivity.start(ConnectionTypeActivity.this, ConnectActivity.CONNECTION_BT);
                finish();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_GET_DEVICE_DATA:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == RESULT_OK) {

                    String address = data.getStringExtra(EXTRA_DEVICE_ADDRESS);
                    String name = data.getStringExtra(EXTRA_DEVICE_NAME);

                    // Create the result Intent and include the MAC address
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                    intent.putExtra(EXTRA_DEVICE_NAME, name);

                    // Set result and finish this Activity
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                }
                break;
        }
    }
}
