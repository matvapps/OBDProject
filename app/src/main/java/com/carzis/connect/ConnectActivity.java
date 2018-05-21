package com.carzis.connect;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.carzis.MyBTConnection;
import com.carzis.R;
import com.carzis.main.MainActivity;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class ConnectActivity extends AppCompatActivity {

    private static final String TAG = ConnectActivity.class.getSimpleName();
    private ImageView iconBth;
    private PulsatorLayout pulsatorLayout;


    private MyBTConnection myBTConnection;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConnectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        iconBth = findViewById(R.id.bth_icon);
        pulsatorLayout = findViewById(R.id.pulsator);

        myBTConnection = MyBTConnection.getInstance(this);
        myBTConnection.enableBluetooth();


        pulsatorLayout.start();
//        iconBth.setOnClickListener(view -> pulsatorLayout.start());


    }

}
