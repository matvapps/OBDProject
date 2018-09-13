package com.carzis;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.carzis.main.MainActivity;
import com.carzis.obd.OBDReader;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alexandr.
 */
public class CarzisApplication extends Application {

    public static String SUBSCRIPTION_BILLING_ID = "com.carzis.subscription";
    public static String DIAGNOSTICS_BILLING_ID = "com.carzis.product.diagnostics";

    public static OBDReader obdReader;

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            multiDexException.printStackTrace();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.class.getSimpleName(), "Application onCreate: ");
        obdReader = new OBDReader(getApplicationContext());
        Fabric.with(this, new Crashlytics());

    }
}
