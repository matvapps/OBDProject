package com.carzis;

import android.content.Context;
import android.os.Handler;

import me.aflak.bluetooth.Bluetooth;

/**
 * Created by Alexandr
 */

public class MyBTConnection {
    private static MyBTConnection connectionObj;

    private Handler mHandler;

    public Bluetooth bluetooth;

    public MyBTConnection(Context context) {
        bluetooth = new Bluetooth(context);
    }

    public static MyBTConnection getInstance(Context context) {
        if (connectionObj == null) {
            connectionObj = new MyBTConnection(context);
        }
        return connectionObj;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public void enableBluetooth() {
        if (!bluetooth.isEnabled())
            bluetooth.enable();
    }

}
