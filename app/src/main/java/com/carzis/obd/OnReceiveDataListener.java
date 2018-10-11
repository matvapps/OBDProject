package com.carzis.obd;

/**
 * Created by Alexandr.
 */
public interface OnReceiveDataListener {
    void onReceiveData(String pid, String value);
    void onReceiveVoltage(String voltage);
    void onConnected(String deviceName);
    void onDisconnected();
}
