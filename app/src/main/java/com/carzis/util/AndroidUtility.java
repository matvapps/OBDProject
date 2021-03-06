package com.carzis.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;

public class AndroidUtility {

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static String getDeviceId(Context context) {
        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }

//    public static String getAppFolderPath(Context context) {
//        return context.getApplicationInfo().dataDir;
//    }

    public static String getAppFolderPath() {
        return Environment.getExternalStorageDirectory() + "/com.carzis/";
    }

    public static void createAppFolder(Context context) {
        File f1 = new File(Environment.getExternalStorageDirectory() + "/com.carzis/");
        if (!f1.exists()) {
            f1.mkdirs();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}


