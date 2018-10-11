package com.carzis.model;

import com.carzis.obd.PidNew;

/**
 * Created by Alexandr
 */

public class PidListItem {

    private PidNew pidItem;
    private boolean isChecked;

    public PidListItem() {
    }
    public PidListItem(PidNew pidItem, boolean isChecked) {
        this.pidItem = pidItem;
        this.isChecked = isChecked;
    }


    public PidNew getPidItem() {
        return pidItem;
    }
    public void setPidItem(PidNew pidItem) {
        this.pidItem = pidItem;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
