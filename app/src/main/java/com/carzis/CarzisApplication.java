package com.carzis;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alexandr.
 */
public class CarzisApplication extends Application {

    public static String SUBSCRIPTION_BILLING_ID = "com.carzis.subscription";
    public static String DIAGNOSTICS_BILLING_ID = "com.carzis.product.diagnostics";



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

//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(BuildConfig.DEBUG ? true : false)
//                .build();
//        Fabric.with(fabric);

        Fabric.with(this, new Crashlytics());

    }
}
