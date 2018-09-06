package com.carzis.model;

import com.carzis.obd.PidItem;

/**
 * Created by Alexandr
 */

public class PidListItem {

    private PidItem pidItem;
    private boolean isChecked;

    public PidListItem() {
    }
    public PidListItem(PidItem pidItem, boolean isChecked) {
        this.pidItem = pidItem;
        this.isChecked = isChecked;
    }


    public PidItem getPidItem() {
        return pidItem;
    }
    public void setPidItem(PidItem pidItem) {
        this.pidItem = pidItem;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
