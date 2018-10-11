package com.carzis.model;

import com.carzis.obd.PID;

/**
 * Created by Alexandr.
 */
public class DashboardItem {
    private String value;
    private String pid;
    private boolean checked;

    public DashboardItem(String value, String pid) {
        this.value = value;
        this.pid = pid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
