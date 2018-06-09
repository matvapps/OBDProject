package com.carzis.main.listener;

import com.carzis.main.Type;

/**
 * Created by Alexandr.
 */
public interface ActivityToDashboardCallbackListener {

    void onPassRealDataToFragment(Type type, String value);
    void onAddNewDevice();
}
