package com.carzis.repository.local.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.carzis.R;
import com.carzis.obd.PID;

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
    private final String PROTOCOL = "protocol";
    private final String INIT_STRING = "init_string";
    private final String LANGUAGE = "language";
    private final String CONNECT_TYPE = "connect_type";
    private final String USER_TOKEN = "user_token";
    private final String USER_PASSWORD = "user_password";
    private final String USER_HAS_1_CHECK = "user_has_1_check";


    private Context context;

    @SuppressLint("CommitPrefEdits")
    public KeyValueStorage(@NonNull Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), 0);
        editor = preferences.edit();
        this.context = context;
    }

    public void setUserPassword(String userPassword) {
        editor.putString(USER_PASSWORD, userPassword);
        editor.commit();
    }

    public String getUserPassword() {
        return preferences.getString(USER_PASSWORD, "");
    }

    public void setUserToken(String userToken) {
        editor.putString(USER_TOKEN, userToken);
        editor.commit();
    }

    public String getUserToken() {
        return preferences.getString(USER_TOKEN, "");
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

    public void setInitString(String initString) {
        editor.putString(INIT_STRING, initString);
        editor.commit();
    }

    public String getInitString() {
        return preferences.getString(INIT_STRING, "ATZ;ATL0;ATE1;ATH1;ATAT1;ATSTFF;ATI;ATDP;ATSP0");
    }

    public int getProtocol() {
        return preferences.getInt(PROTOCOL, 0);
    }

    public void setProtocol(int protocol) {
        editor.putInt(PROTOCOL, 0);
        editor.commit();
    }

    public String getLanguage() {
        return preferences.getString(LANGUAGE, "Русский");
    }

    public void setLanguage(String language) {
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    public String getConnectType() {
        return preferences.getString(CONNECT_TYPE, "Bluetooth");
    }

    public void setConnectType(String connectType) {
        editor.putString(CONNECT_TYPE, connectType);
    }

    public void resetDashboardDevices() {
        editor.putString(USER_DASHBOARD_DEVICES, "");
        editor.commit();
    }

    public void addDeviceToDashboard(String pid) {
        String devices = preferences.getString(USER_DASHBOARD_DEVICES, "");
        devices += " " + pid;

        editor.putString(USER_DASHBOARD_DEVICES, devices);
        editor.commit();
    }

    public void removeDeviceFromDashboard(String pid) {
        String devices = preferences.getString(USER_DASHBOARD_DEVICES, "");
        devices = devices.replace(pid, "");

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


    public void setUserHas1Check(boolean isUsed) {
        editor.putBoolean(USER_HAS_1_CHECK, isUsed);
        editor.commit();
    }

    public boolean isUserHas1Check() {
        return preferences.getBoolean(USER_HAS_1_CHECK, true);
    }

}
