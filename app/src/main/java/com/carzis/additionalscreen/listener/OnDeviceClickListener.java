package com.carzis.additionalscreen.listener;

import com.carzis.obd.PID;

/**
 * Created by Alexandr.
 */
public interface OnDeviceClickListener {
    void onClick(String pid, boolean enabled);
    void onLongClick(String name);
}
