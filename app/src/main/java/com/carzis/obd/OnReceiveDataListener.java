package com.carzis.obd;

import com.carzis.model.PID;

/**
 * Created by Alexandr.
 */
public interface OnReceiveDataListener {
    void onReceiveData(PID pid, String value);
    void onReceiveVoltage(String voltage);
    void onConnected(String deviceName);
    void onDisconnected();
}
