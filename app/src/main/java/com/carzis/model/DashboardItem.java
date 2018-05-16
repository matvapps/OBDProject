package com.carzis.model;

/**
 * Created by Alexandr.
 */
public class DashboardItem {
    private String value;
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

    public DashboardItem(String value, DashboardDevice deviceType) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
