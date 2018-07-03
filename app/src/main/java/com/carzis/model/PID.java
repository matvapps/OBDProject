package com.carzis.model;

/**
 * Created by Alexandr.
 */
public enum PID {
    VOLTAGE("ATRV"),
    PIDS_SUP_0_20("0100"), // BIT encoded
    DTCS_CLEARED_MIL_DTCS("0101"), // BIT encoded
    FREEZE_DTCS("0102"),
    FUEL_SYSTEM_STATUS("0103"), // BIT encoded
    CALCULATED_ENGINE_LOAD("0104"),
    ENGINE_COOLANT_TEMP("0105"),
    SH_TERM_FUEL_TRIM_1("0106"),
    LN_TERM_FUEL_PERCENT_TRIM_1("0107"),
    SH_TERM_FUEL_TRIM_2("0108"),
    LN_TERM_FUEL_PERCENT_TRIM_2("0109"),
    FUEL_PRESSURE("010A"),
    INTAKE_MANIFOLD_PRESSURE("010B"),
    ENGINE_RPM("010C"),
    VEHICLE_SPEED("010D"),
    TIMING_ADVANCE("010E"),
    INTAKE_AIR_TEMP("010F"),
    MAF_AIR_FLOW("0110"),
    THROTTLE_POSITION("0111"),
    COMMANDED_SECONDARY_AIR_STATUS("0112"), // BIT encoded
    OXY_SENS_PRESENT_2_BANKS("0113"),
    OXY_SENS_VOLT_1("0114"),
    OXY_SENS_VOLT_2("0115"),
    OXY_SENS_VOLT_3("0116"),
    OXY_SENS_VOLT_4("0117"),
    OXY_SENS_VOLT_5("0118"),
    OXY_SENS_VOLT_6("0119"),
    OXY_SENS_VOLT_7("011A"),
    OXY_SENS_VOLT_8("011B"),
    OBD_STANDARDS_VEHICLE_CONFORMS_TO("011C"), // BIT encoded
    OXY_SENS_PRESENT_4_BANKS("011D"),
    AUXILIARY_INPUT_STATUS("011E"),
    RUN_TIME_SINCE_ENGINE_START("011F"),
    PIDS_SUP_21_40("0120"),  // BIT encoded
    DISTANCE_TRAVELED_WITH_MALFUNCTION_ON("0121"),
    FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM("0122"),
    FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT("0123"),
    OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0124"),
    OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0125"),
    OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0126"),
    OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0127"),
    OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0128"),
    OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("0129"),
    OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("012A"),
    OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE("012B"),
    COMMANDED_EGR("012C"),
    EGR_ERROR("012D"),
    COMMANDED_EVAPORATIVE_PURGE("012E"),
    FUEL_TANK_LEVEL_INPUT("012F"),
    WARM_UPS_SINCE_CODES_CLEARED("0130"),
    DISTANCE_TRAVELED_SINCE_CODES_CLEARED("0131"),
    SYSTEM_VAPOR_PRESSURE("0132"),
    ABSOLUTE_BAROMETRIC_PRESSURE("0133"),
    OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0134"),
    OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0135"),
    OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0136"),
    OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0137"),
    OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0138"),
    OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("0139"),
    OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("013A"),
    OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT("013B"),
    CATALYST_TEMP_BANK_1_SENS_1("013C"),
    CATALYST_TEMP_BANK_2_SENS_1("013D"),
    CATALYST_TEMP_BANK_1_SENS_2("013E"),
    CATALYST_TEMP_BANK_2_SENS_2("013F"),
    PIDS_SUP_41_60("0140"),  // BIT encoded
    MONITOR_STATUS_THIS_DRIVE_CYCLE("0141"), // BIT encoded
    CONTROL_MODULE_VOLTAGE("0142"),
    ABSOLUTE_LOAD_VALUE("0143"),
    FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO("0144"),
    RELATIVE_THROTTLE_POSITION("0145"),
    AMBIENT_AIR_TEMPERATURE("0146"),
    ABSOLUTE_THROTTLE_POSITION_B("0147"),
    ABSOLUTE_THROTTLE_POSITION_C("0148"),
    ACCELERATOR_PEDAL_POSITION_D("0149"),
    ACCELERATOR_PEDAL_POSITION_E("014A"),
    ACCELERATOR_PEDAL_POSITION_F("014B"),
    COMMANDED_THROTTLE_ACTUATOR("014C"),
    TIME_RUN_WITH_MIL_ON("014D"),
    TIME_SINCE_TROUBLE_CODE_CLEARED("014E"),
    MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE("014F"),
    MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS("0150"),
    FUEL_TYPE("0151"),
    ETHANOL_FUEL("0152"),
    ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE("0153"),
    EVAP_SYSTEM_VAPOR_PRESSURE("0154"),
    SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3("0155"),  // a - bank 1, B - bank 3
    LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3("0156"),  // a - bank 1, B - bank 3
    SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4("0157"),  // a - bank 2, B - bank 4
    LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4("0158"),  // a - bank 2, B - bank 4
    FUEL_RAIL_ABSOLUTE_PRESSURE("0159"),
    RELATIVE_ACCELERATOR_PEDAL_POSITION("015A"),
    HYBRID_BATTERY_PACK_REMAINING_LIFE("015B"),
    ENGINE_OIL_TEMP("015C"),
    FUEL_INJECTION_TIMING("015D"),
    ENGINE_FUEL_RATE("015E"),
    EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED("015F"),
    PIDS_SUP_61_80("0160"),
    DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE("0161"),
    ACTUAL_ENGINE_PERCENT_TORQUE("0162"),
    ENGINE_REFERENCE_TORQUE("0163"),
    ENGINE_PERCENT_TORQUE_DATA("0164"),
    AUXILIARY_IN_OUT_SUPPORTED("0165"),
    TROUBLE_CODES("03");

    private final String command;

    PID(String command) {
        this.command = command;
    }

    public final String getCommand() {
        return command;
    }

    public static PID getEnumByString(String command) {
        for (PID e : PID.values()) {
            if (command.equals(e.getCommand())) return e;
        }
        return null;
    }
}
