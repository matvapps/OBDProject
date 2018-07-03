package com.carzis.main.listener;

import com.carzis.model.PID;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface ActivityToDashboardCallbackListener {

    void onPassRealDataToFragment(PID pid, String value);
    void onAddNewDevice(List<String> supportedPIDS);
}
