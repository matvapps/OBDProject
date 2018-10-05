package com.carzis.obd;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

/**
 * Created by Alexandr.
 */
public class PidItem {

    public static final String[] PIDS = {
            "0100", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109", "010A", "010B", "010C", "010D", "010E", "010F",
            "0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117", "0118", "0119", "011A", "011B", "011C", "011D", "011E", "011F",
            "0120", "0121", "0122", "0123", "0124", "0125", "0126", "0127", "0128", "0129", "012A", "012B", "012C", "012D", "012E", "012F",
            "0130", "0131", "0132", "0133", "0134", "0135", "0136", "0137", "0138", "0139", "013A", "013B", "013C", "013D", "013E", "013F",
            "0140", "0141", "0142", "0143", "0144", "0145", "0146", "0147", "0148", "0149", "014A", "014B", "014C", "014D", "014E", "014F",
            "0150", "0151", "0152", "0153", "0154", "0155", "0156", "0157", "0158", "0159", "015A", "015B", "015C", "015D", "015E", "015F",
            "0160", "0161", "0162", "0163", "0164", "0165"};


    private PID pid;
    private String formula = "";

    public PidItem(PID pid) {
        this.pid = pid;
        this.formula = getFormulaFor(pid);
    }

    public PidItem(PID pid, String formula) {
        this.pid = pid;
        this.formula = formula;
    }

    public PidItem(String pid, String formula, String name, String units, String header) {

    }

    public int getCommandAsInt() {
        return Integer.parseInt(pid.getCommand().substring(2), 16);
    }

    public String getValue(int A, int B/*, int C, int D*/) {
        if (formula.equals(""))
            return "-";

        Function At = new Function("At(a,b) = " + formula);

        Argument a = new Argument("a = " + A);
        Argument b = new Argument("b = " + B);
//        Argument c = new Argument("c = " + C);
//        Argument d = new Argument("d = " + D);
        Expression expression =
                new Expression("At(a,b)", At, a, b/*, c, d*/);

        return String.valueOf(expression.calculate());
    }


    public String getFormulaFor(PID pid) {
        switch (pid) {
//            case PIDS_SUP_0_20:
//                return "";
//            case DTCS_CLEARED_MIL_DTCS :
//                return "";
//            case FREEZE_DTCS:
//                return "";
//            case FUEL_SYSTEM_STATUS :
//                return "";
            case CALCULATED_ENGINE_LOAD:
                return "(100 * a) / 255";
            case ENGINE_COOLANT_TEMP:
                return "a - 40";
            case SH_TERM_FUEL_TRIM_1:
            case LN_TERM_FUEL_PERCENT_TRIM_1:
            case SH_TERM_FUEL_TRIM_2:
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return "a / 1.28 - 100";
            case FUEL_PRESSURE:
                return "3 * a";
            case INTAKE_MANIFOLD_PRESSURE:
                return "a";
            case ENGINE_RPM:
                return "(256 * a + b) / 4";
            case VEHICLE_SPEED:
                return "a";
            case TIMING_ADVANCE:
                return "a / 2 - 64";
            case INTAKE_AIR_TEMP:
                return "a - 40";
            case MAF_AIR_FLOW:
                return "(256 * a + b) / 100";
            case THROTTLE_POSITION:
                return "100 * a / 255";
//            case COMMANDED_SECONDARY_AIR_STATUS :
//                return "";
//            case OXY_SENS_PRESENT_2_BANKS:
//                return "";
            case OXY_SENS_VOLT_1:
            case OXY_SENS_VOLT_2:
            case OXY_SENS_VOLT_3:
            case OXY_SENS_VOLT_4:
            case OXY_SENS_VOLT_5:
            case OXY_SENS_VOLT_6:
            case OXY_SENS_VOLT_7:
            case OXY_SENS_VOLT_8:
                return "(100 * b) / 128 - 100";
//            case OBD_STANDARDS_VEHICLE_CONFORMS_TO :
//                return "";
//            case OXY_SENS_PRESENT_4_BANKS:
//                return "";
//            case AUXILIARY_INPUT_STATUS:
//                return "";
            case RUN_TIME_SINCE_ENGINE_START:
                return "256 * a + b";
//            case PIDS_SUP_21_40  :
//                return "";
            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
                return "256 * a + b";
            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
                return "0.079 * (256 * a + b)";
            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
                return "10 * (256 * a + b)";
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "2 * (256 * a + b) / 65536";
            case COMMANDED_EGR:
                return "100 * a / 255";
            case EGR_ERROR:
                return "100 * a / 255 - 100";
            case COMMANDED_EVAPORATIVE_PURGE:
                return "100 * a / 255";
            case FUEL_TANK_LEVEL_INPUT:
                return "100 * a / 255";
            case WARM_UPS_SINCE_CODES_CLEARED:
                return "a";
            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
                return "256 * a + b";
            case SYSTEM_VAPOR_PRESSURE:
                return "(256 * a + b) / 4";
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return "a";
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "2 * (256 * a + b) / 65536";
            case CATALYST_TEMP_BANK_1_SENS_1:
            case CATALYST_TEMP_BANK_2_SENS_1:
            case CATALYST_TEMP_BANK_1_SENS_2:
            case CATALYST_TEMP_BANK_2_SENS_2:
                return "(256 * a + b) / 10 - 40";
//            case PIDS_SUP_41_60  :
//                return "";
//            case MONITOR_STATUS_THIS_DRIVE_CYCLE :
//                return "";
            case CONTROL_MODULE_VOLTAGE:
                return "(256 * a + b) / 1000";
            case ABSOLUTE_LOAD_VALUE:
                return "100 / 255 * (256* a + b)";
            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
                return "2 / 65536 * (256 * a + b)";
            case RELATIVE_THROTTLE_POSITION:
                return "100 / 255 * a";
            case AMBIENT_AIR_TEMPERATURE:
                return "a - 40";
            case ABSOLUTE_THROTTLE_POSITION_B:
            case ABSOLUTE_THROTTLE_POSITION_C:
            case ACCELERATOR_PEDAL_POSITION_D:
            case ACCELERATOR_PEDAL_POSITION_E:
            case ACCELERATOR_PEDAL_POSITION_F:
            case COMMANDED_THROTTLE_ACTUATOR:
                return "100 / 255 * a";
            case TIME_RUN_WITH_MIL_ON:
            case TIME_SINCE_TROUBLE_CODE_CLEARED:
                return "256 * a + b";
//            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
//                return "";
//            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
//                return "";
//            case FUEL_TYPE:
//                return "";
            case ETHANOL_FUEL:
                return "100 / 255 * a";
            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
                return "(256 * a + b) / 200";
            case EVAP_SYSTEM_VAPOR_PRESSURE:
                return "((a * 256) + b) - 32767";
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3  :
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3  :
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4  :
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4  :
                return "100 / 128 * a - 100";
            case FUEL_RAIL_ABSOLUTE_PRESSURE:
                return "10 * (256 * a + b)";
            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
                return "100 / 255 * a";
            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
                return "100 / 255 * a";
            case ENGINE_OIL_TEMP:
                return "a - 40";
            case FUEL_INJECTION_TIMING:
                return "(256 * a + b) / 128 - 210";
            case ENGINE_FUEL_RATE:
                return "(256 * a + b) / 20";
//            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
//                return "";
//            case PIDS_SUP_61_80:
//                return "";
            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
                return "a - 125";
            case ACTUAL_ENGINE_PERCENT_TORQUE:
                return "a - 125";
            case ENGINE_REFERENCE_TORQUE:
                return "256 * a + b";
//            case ENGINE_PERCENT_TORQUE_DATA:
//                return "";
//            case AUXILIARY_IN_OUT_SUPPORTED:
//                return "";
        }

        return "";
    }
    public String getFormulaFor(String pidStr) {
        PID pid = PID.getEnumByString(pidStr);
        return getFormulaFor(pid);
    }

    public static boolean contains(String pid) {
        for (String item :PIDS) {
            if (item.equals(pid))
                return true;
        }
        return false;
    }

    public PID getPid() {
        return pid;
    }
    public void setPid(PID pid) {
        this.pid = pid;
    }
    public String getFormula() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }

}
