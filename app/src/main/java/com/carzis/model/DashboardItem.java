package com.carzis.model;

import com.carzis.obd.PID;

/**
 * Created by Alexandr.
 */
public class DashboardItem {
    private String value;
    private PID pid;

//    public enum DashboardDevice {
//        VOLTAGE(108),
//        ENGINE_LOAD(110),
//        ENGINE_COOLANT_TEMP(111),
//        SH_TERM_FUEL_TRIM_1(112),
//        LN_TERM_FUEL_PERCENT_TRIM_1(113),
//        SH_TERM_FUEL_TRIM_2(114),
//        LN_TERM_FUEL_PERCENT_TRIM_2(115),
//        FUEL_PRESSURE(116),
//        INTAKE_MAN_PRESSURE(117),
//        RPM(102),
//        SPEED(101),
//        TIMING_ADVANCE(118),
//        INTAKE_AIR_TEMP(103),
//        MAF_AIR_FLOW(119),
//        THROTTLE_POSITION(120),
//        OXY_SENS_VOLT_B_1_SENS_1(121),
//        OXY_SENS_VOLT_B_1_SENS_2(122),
//        OXY_SENS_VOLT_B_1_SENS_3(123),
//        OXY_SENS_VOLT_B_1_SENS_4(124),
//        OXY_SENS_VOLT_B_2_SENS_1(125),
//        OXY_SENS_VOLT_B_2_SENS_2(126),
//        OXY_SENS_VOLT_B_2_SENS_3(127),
//        OXY_SENS_VOLT_B_2_SENS_4(128),
//        FUEL_RAIL_PRESSURE(129),
//        FUEL_RAIL_PRESSURE_DIESEL(130),
//        COMMANDED_EGR(131),
//        FUEL_AMOUNT(109),
//        BAROMETRIC_PRESSURE(132),
//        CATALYST_TEMP_B1S1(133),
//        CATALYST_TEMP_B2S1(134),
//        CATALYST_TEMP_B1S2(135),
//        CATALYST_TEMP_B2S2(136),
//        THROTTLE_POS_2(137),
//        ENGINE_OIL_TEMP(107);
//
//
//        public final int value;
//
//        DashboardDevice(int value) {
//            this.value = value;
//        }
//    }

    public DashboardItem(String value, PID pid) {
        this.value = value;
        this.pid = pid;
    }


    public PID getPid() {
        return pid;
    }

    public void setPid(PID pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
