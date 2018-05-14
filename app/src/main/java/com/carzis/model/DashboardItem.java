package com.carzis.model;

/**
 * Created by Alexandr.
 */
public class DashboardItem {
    private int value;
    private DashboardDevice deviceType;

    public enum DashboardDevice {
        SPEED,
        RPM,
        INTAKE_TEMP,
        ENGINE_TEMP,
        COOLANT_TEMP,
        OIL_PRESSURE,
        VOLTAGE,
        GAS_AMOUNT
    }

    public DashboardItem(int value, DashboardDevice deviceType) {
        this.value = value;
        this.deviceType = deviceType;
    }

//    public String getDimen() {
//        return dimen;
//    }
//
//    public void setDimen(String dimen) {
//        this.dimen = dimen;
//    }


    public DashboardDevice getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DashboardDevice deviceType) {
        this.deviceType = deviceType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
