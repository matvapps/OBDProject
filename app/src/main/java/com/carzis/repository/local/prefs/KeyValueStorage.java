package com.carzis.repository.local.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.carzis.R;
import com.carzis.model.DashboardItem.DashboardDevice;

/**
 * Created by Alexandr.
 */
public class KeyValueStorage {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String USER_FULL_NAME = "user_full_name";
    private final String USER_BDAY = "user_bDay";
    private final String USER_PHONE = "user_phone";
    private final String USER_EMAIL = "user_email";
    private final String USER_IMAGE = "user_image";
    private final String USER_DASHBOARD_DEVICES = "user_dashboard_devices";
    private final String FIRST_TIME_LAUNCH = "first_time_launch";
    private final String CURRENT_CAR = "current_car";

    @SuppressLint("CommitPrefEdits")
    public KeyValueStorage(@NonNull Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), 0);
        editor = preferences.edit();
    }

    public void setUserFullName(String fullName) {
        editor.putString(USER_FULL_NAME, fullName);
        editor.commit();
    }

    public String getUserFullName() {
        return preferences.getString(USER_FULL_NAME, "");
    }

    public void setUserBDay(String bDay) {
        editor.putString(USER_BDAY, bDay);
        editor.commit();
    }

    public String getUserBDay() {
        return preferences.getString(USER_BDAY, "");
    }

    public void setUserPhoneNum(String phoneNum) {
        editor.putString(USER_PHONE, phoneNum);
        editor.commit();
    }

    public String getUserPhoneNum() {
        return preferences.getString(USER_PHONE, "");
    }

    public void setUserEmail(String email) {
        editor.putString(USER_EMAIL, email);
        editor.commit();
    }

    public String getUserEmail() {
        return preferences.getString(USER_EMAIL, "");
    }

    public void setUserImagePath(String userImagePath) {
        editor.putString(USER_IMAGE, userImagePath);
        editor.commit();
    }

    public String getUserImagePath() {
        return preferences.getString(USER_IMAGE, "");
    }


    public void addDeviceToDashboard(DashboardDevice device) {
        String devices = preferences.getString(USER_DASHBOARD_DEVICES, "");

        devices += " " + device.value;


        editor.putString(USER_DASHBOARD_DEVICES, devices);
        editor.commit();
    }

    public void removeDeviceFromDashboard(DashboardDevice device) {

        String devices = preferences.getString(USER_DASHBOARD_DEVICES, "");
//        String comma = ",";
//        if (!devices.isEmpty() && !devices.contains(","))
//            comma = "";

        devices = devices.replace(String.valueOf(device.value), "");

        editor.putString(USER_DASHBOARD_DEVICES, devices);
        editor.commit();
    }

    public void setCurrentCarName(String carName) {
        editor.putString(CURRENT_CAR, carName);
        editor.commit();
    }

    public String getCurrentCarName() {
        return preferences.getString(CURRENT_CAR, "");
    }

    public String getUserDashboardDevices() {
        return preferences.getString(USER_DASHBOARD_DEVICES, "");
    }

    public void setFirstTimeLaunch(boolean firstTime) {
        editor.putBoolean(FIRST_TIME_LAUNCH, firstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(FIRST_TIME_LAUNCH, true);
    }

}
