package com.carzis.additionalscreen.listener;

import com.carzis.model.DashboardItem;

/**
 * Created by Alexandr.
 */
public interface OnDeviceClickListener {
    void onClick(DashboardItem.DashboardDevice deviceType, boolean enabled);
}
