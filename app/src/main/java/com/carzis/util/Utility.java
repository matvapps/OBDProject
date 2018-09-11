package com.carzis.util;

import android.content.Context;

import com.carzis.R;
import com.carzis.obd.PID;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexandr.
 */
public class Utility {

    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static byte hexStringToByteArray(char s) {
        return (byte) ((Character.digit(s, 16) << 4));
    }


    public static String getDeviceDimenBy(Context context, PID pid) {
        switch (pid) {
            case VOLTAGE:
                return context.getString(R.string.dimen_title_voltage);
            case PIDS_SUP_0_20:
                return "";
            case DTCS_CLEARED_MIL_DTCS:
                return "";
            case FREEZE_DTCS:
                return "";
            case FUEL_SYSTEM_STATUS:
                return "";
            case CALCULATED_ENGINE_LOAD:
                return "%";
            case ENGINE_COOLANT_TEMP:
                return context.getString(R.string.grad);
            case SH_TERM_FUEL_TRIM_1:
            case LN_TERM_FUEL_PERCENT_TRIM_1:
            case SH_TERM_FUEL_TRIM_2:
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return "%";
            case FUEL_PRESSURE:
                return context.getString(R.string.kPa);
            case INTAKE_MANIFOLD_PRESSURE:
                return context.getString(R.string.kPa);
            case ENGINE_RPM:
                return "rpm";
            case VEHICLE_SPEED:
                return context.getString(R.string.kmh);
            case TIMING_ADVANCE:
                return "°";
            case INTAKE_AIR_TEMP:
                return context.getString(R.string.grad);
            case MAF_AIR_FLOW:
                return context.getString(R.string.gramsec);
            case THROTTLE_POSITION:
                return "%";
            case COMMANDED_SECONDARY_AIR_STATUS:
                return "";
            case OXY_SENS_PRESENT_2_BANKS:
                return "";
            case OXY_SENS_VOLT_1:
            case OXY_SENS_VOLT_2:
            case OXY_SENS_VOLT_3:
            case OXY_SENS_VOLT_4:
            case OXY_SENS_VOLT_5:
            case OXY_SENS_VOLT_6:
            case OXY_SENS_VOLT_7:
            case OXY_SENS_VOLT_8:
                return context.getString(R.string.dimen_title_voltage);
            case OBD_STANDARDS_VEHICLE_CONFORMS_TO:
                return "";
            case OXY_SENS_PRESENT_4_BANKS:
                return "";
            case AUXILIARY_INPUT_STATUS:
                return "";
            case RUN_TIME_SINCE_ENGINE_START:
                return context.getString(R.string.sec);
            case PIDS_SUP_21_40:
                return "";
            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
                return context.getString(R.string.mil);
            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
                return context.getString(R.string.kPa);
            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
                return context.getString(R.string.kPa);
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.dimen_title_voltage);
            case COMMANDED_EGR:
                return "%";
            case EGR_ERROR:
                return "%";
            case COMMANDED_EVAPORATIVE_PURGE:
                return "";
            case FUEL_TANK_LEVEL_INPUT:
                return "%";
            case WARM_UPS_SINCE_CODES_CLEARED:
                return context.getString(R.string.quant);
            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
                return context.getString(R.string.mil);
            case SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.Pa);
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return context.getString(R.string.kPa);
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "mA";
            case CATALYST_TEMP_BANK_1_SENS_1:
            case CATALYST_TEMP_BANK_2_SENS_1:
            case CATALYST_TEMP_BANK_1_SENS_2:
            case CATALYST_TEMP_BANK_2_SENS_2:
                return context.getString(R.string.grad);
            case PIDS_SUP_41_60:
                return "";
            case MONITOR_STATUS_THIS_DRIVE_CYCLE:
                return "";
            case CONTROL_MODULE_VOLTAGE:
                return context.getString(R.string.dimen_title_voltage);
            case ABSOLUTE_LOAD_VALUE:
                return "%";
            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
                return "ratio";
            case RELATIVE_THROTTLE_POSITION:
                return "%";
            case AMBIENT_AIR_TEMPERATURE:
                return context.getString(R.string.grad);
            case ABSOLUTE_THROTTLE_POSITION_B:
            case ABSOLUTE_THROTTLE_POSITION_C:
            case ACCELERATOR_PEDAL_POSITION_D:
            case ACCELERATOR_PEDAL_POSITION_E:
            case ACCELERATOR_PEDAL_POSITION_F:
            case COMMANDED_THROTTLE_ACTUATOR:
                return "%";
            case TIME_RUN_WITH_MIL_ON:
                return context.getString(R.string.min);
            case TIME_SINCE_TROUBLE_CODE_CLEARED:
                return context.getString(R.string.min);
            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
                return "";
            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
                return context.getString(R.string.gramsec);
            case FUEL_TYPE:
                return "";
            case ETHANOL_FUEL:
                return "%";
            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.kPa);
            case EVAP_SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.Pa);
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return "%";
            case FUEL_RAIL_ABSOLUTE_PRESSURE:
                return context.getString(R.string.kPa);
            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
                return "%";
            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
                return "%";
            case ENGINE_OIL_TEMP:
                return context.getString(R.string.grad);
            case FUEL_INJECTION_TIMING:
                return "°";
            case ENGINE_FUEL_RATE:
                return context.getString(R.string.lh);
            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
                return "";
            case PIDS_SUP_61_80:
                return "";
            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
                return "%";
            case ACTUAL_ENGINE_PERCENT_TORQUE:
                return "%";
            case ENGINE_REFERENCE_TORQUE:
                return context.getString(R.string.nm);
            case ENGINE_PERCENT_TORQUE_DATA:
                return "%";
            case AUXILIARY_IN_OUT_SUPPORTED:
                return "";
        }

        return "";
    }


//    public static String getDeviceDimenBy(Context context, DashboardDevice deviceType) {
//        switch (deviceType) {
//            case RPM: {
//                return context.getString(R.string.dimen_title_rpm);
//            }
//            case SPEED: {
//                return context.getString(R.string.dimen_title_speed);
//            }
//            case OXY_SENS_VOLT_B_1_SENS_1:
//            case OXY_SENS_VOLT_B_1_SENS_2:
//            case OXY_SENS_VOLT_B_1_SENS_3:
//            case OXY_SENS_VOLT_B_1_SENS_4:
//            case OXY_SENS_VOLT_B_2_SENS_1:
//            case OXY_SENS_VOLT_B_2_SENS_2:
//            case OXY_SENS_VOLT_B_2_SENS_3:
//            case OXY_SENS_VOLT_B_2_SENS_4:
//            case VOLTAGE: {
//                return context.getString(R.string.dimen_title_voltage);
//            }
//
//            case COMMANDED_EGR:
//            case THROTTLE_POS_2:
//            case THROTTLE_POSITION:
//            case SH_TERM_FUEL_TRIM_1 :
//            case LN_TERM_FUEL_PERCENT_TRIM_1:
//            case SH_TERM_FUEL_TRIM_2 :
//            case LN_TERM_FUEL_PERCENT_TRIM_2:
//            case ENGINE_LOAD:
//            case FUEL_AMOUNT: {
//                return context.getString(R.string.dimen_title_gas);
//            }
//            case ENGINE_OIL_TEMP:
//            case INTAKE_AIR_TEMP:
//            case ENGINE_COOLANT_TEMP:
//            case CATALYST_TEMP_B1S1:
//            case CATALYST_TEMP_B1S2:
//            case CATALYST_TEMP_B2S1:
//            case CATALYST_TEMP_B2S2:
//                return context.getString(R.string.dimen_title_engine_temp);
//
//            case BAROMETRIC_PRESSURE:
//            case FUEL_RAIL_PRESSURE:
//            case FUEL_RAIL_PRESSURE_DIESEL:
//            case INTAKE_MAN_PRESSURE:
//            case FUEL_PRESSURE:
//                return "кПа";
//
//            case TIMING_ADVANCE:
//                return "°";
//
//            case MAF_AIR_FLOW:
//                return "г/с";
//        }
//
//        return "";
//    }

    public static int getDeviceIconIdBy(PID pid) {
        switch (pid) {
            case VOLTAGE:
                return R.drawable.ac;
            case ENGINE_COOLANT_TEMP:
                return R.drawable.temp;
            case FUEL_PRESSURE:
                return R.drawable.ic_topl;
            case INTAKE_AIR_TEMP:
                return R.drawable.temp;
            case FUEL_TANK_LEVEL_INPUT:
                return R.drawable.ic_bens;
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return R.drawable.ic_atmos;
            case CATALYST_TEMP_BANK_1_SENS_1:
            case CATALYST_TEMP_BANK_2_SENS_1:
            case CATALYST_TEMP_BANK_1_SENS_2:
            case CATALYST_TEMP_BANK_2_SENS_2:
                return R.drawable.temp;
            case AMBIENT_AIR_TEMPERATURE:
                return R.drawable.temp;
            case ENGINE_OIL_TEMP:
                return R.drawable.wo;
        }
        return R.drawable.ic_zaglush;
    }


    public static String getDeviceNameBy(Context context, PID pid) {

        switch (pid) {
            case VOLTAGE:
                return context.getString(R.string.voltage);
            case PIDS_SUP_0_20:
                return context.getString(R.string.pids_0_20);
            case DTCS_CLEARED_MIL_DTCS:
                return context.getString(R.string.dtc_cleared_mil_dtc);
            case FREEZE_DTCS:
                return context.getString(R.string.freeze_dtc);
            case FUEL_SYSTEM_STATUS:
                return context.getString(R.string.fuel_system_status);
            case CALCULATED_ENGINE_LOAD:
                return context.getString(R.string.calc_engine_load);
            case ENGINE_COOLANT_TEMP:
                return context.getString(R.string.engine_coolant_temp);
            case SH_TERM_FUEL_TRIM_1:
                return context.getString(R.string.sh_term_fuel_trim_1);
            case LN_TERM_FUEL_PERCENT_TRIM_1:
                return context.getString(R.string.ln_term_fuel_percent_trim_1);
            case SH_TERM_FUEL_TRIM_2:
                return context.getString(R.string.sh_term_fuel_trim_2);
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return context.getString(R.string.ln_term_fuel_percent_trim_2);
            case FUEL_PRESSURE:
                return context.getString(R.string.fuel_pressure);
            case INTAKE_MANIFOLD_PRESSURE:
                return context.getString(R.string.intake_manifold_pressure);
            case ENGINE_RPM:
                return context.getString(R.string.engine_rpm);
            case VEHICLE_SPEED:
                return context.getString(R.string.vehicle_speed);
            case TIMING_ADVANCE:
                return context.getString(R.string.timing_advance);
            case INTAKE_AIR_TEMP:
                return context.getString(R.string.intake_air_temp);
            case MAF_AIR_FLOW:
                return context.getString(R.string.maf_air_flow);
            case THROTTLE_POSITION:
                return context.getString(R.string.throttle_pos);
            case COMMANDED_SECONDARY_AIR_STATUS:
                return context.getString(R.string.commanded_secondary_air_status);
            case OXY_SENS_PRESENT_2_BANKS:
                return context.getString(R.string.oxy_sens_pres_2_banks);
            case OXY_SENS_VOLT_1:
                return context.getString(R.string.oxy_sens_1);
            case OXY_SENS_VOLT_2:
                return context.getString(R.string.oxy_sens_2);
            case OXY_SENS_VOLT_3:
                return context.getString(R.string.oxy_sens_3);
            case OXY_SENS_VOLT_4:
                return context.getString(R.string.oxy_sens_4);
            case OXY_SENS_VOLT_5:
                return context.getString(R.string.oxy_sens_5);
            case OXY_SENS_VOLT_6:
                return context.getString(R.string.oxy_sens_6);
            case OXY_SENS_VOLT_7:
                return context.getString(R.string.oxy_sens_7);
            case OXY_SENS_VOLT_8:
                return context.getString(R.string.oxy_sens_8);
            case OBD_STANDARDS_VEHICLE_CONFORMS_TO:
                return context.getString(R.string.obd_standards);
            case OXY_SENS_PRESENT_4_BANKS:
                return context.getString(R.string.oxy_sens_pres_4_banks);
            case AUXILIARY_INPUT_STATUS:
                return context.getString(R.string.auxiliary_input_status);
            case RUN_TIME_SINCE_ENGINE_START:
                return context.getString(R.string.run_time_since_engine_start);
            case PIDS_SUP_21_40:
                return context.getString(R.string.pids_21_40);
            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
                return context.getString(R.string.distance_travel_with_mal_on);
            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
                return context.getString(R.string.fuel_rail_pressure_man_vacuum);
            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
                return context.getString(R.string.furl_rail_gauge_pressure_diesel_gas_direct_inject);
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_1_fuel_air);
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_2_fuel_air);
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_3_fuel_air);
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_4_fuel_air);
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_5_fuel_air);
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_6_fuel_air);
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_7_fuel_air);
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return context.getString(R.string.oxy_sens_8_fuel_air);
            case COMMANDED_EGR:
                return context.getString(R.string.commanded_egr);
            case EGR_ERROR:
                return context.getString(R.string.egr_error);
            case COMMANDED_EVAPORATIVE_PURGE:
                return context.getString(R.string.commanded_evaporative_purge);
            case FUEL_TANK_LEVEL_INPUT:
                return context.getString(R.string.fuel_tank_level_input);
            case WARM_UPS_SINCE_CODES_CLEARED:
                return context.getString(R.string.warm_ups_since_codes_cleared);
            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
                return context.getString(R.string.distance_traveled_since_codes_cleared);
            case SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.system_vapor_pressure);
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return context.getString(R.string.absolute_barometric_pressure);
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_1_fuel_air_equivalence);
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_2_fuel_air_equivalence);
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_3_fuel_air_equivalence);
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_4_fuel_air_equivalence);
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_5_fuel_air_equivalence);
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_6_fuel_air_equivalence);
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_7_fuel_air_equivalence);
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return context.getString(R.string.oxy_sens_8_fuel_air_equivalence);
            case CATALYST_TEMP_BANK_1_SENS_1:
                return context.getString(R.string.catalyst_temp_bank_1_sens_1);
            case CATALYST_TEMP_BANK_2_SENS_1:
                return context.getString(R.string.catalyst_temp_bank_2_sens_1);
            case CATALYST_TEMP_BANK_1_SENS_2:
                return context.getString(R.string.catalyst_temp_bank_1_sens_2);
            case CATALYST_TEMP_BANK_2_SENS_2:
                return context.getString(R.string.catalyst_temp_bank_2_sens_2);
            case PIDS_SUP_41_60:
                return context.getString(R.string.pids_41_60);
            case MONITOR_STATUS_THIS_DRIVE_CYCLE:
                return context.getString(R.string.monitor_status_this_drive_cycle);
            case CONTROL_MODULE_VOLTAGE:
                return context.getString(R.string.control_module_voltage);
            case ABSOLUTE_LOAD_VALUE:
                return context.getString(R.string.absolute_load_value);
            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
                return context.getString(R.string.fuel_air_commanded_equivalence_ratio);
            case RELATIVE_THROTTLE_POSITION:
                return context.getString(R.string.relative_throttle_pos);
            case AMBIENT_AIR_TEMPERATURE:
                return context.getString(R.string.ambient_air_temp);
            case ABSOLUTE_THROTTLE_POSITION_B:
                return context.getString(R.string.absolute_throttle_pos_b);
            case ABSOLUTE_THROTTLE_POSITION_C:
                return context.getString(R.string.absolute_throttle_pos_c);
            case ACCELERATOR_PEDAL_POSITION_D:
                return context.getString(R.string.absolute_throttle_pos_d);
            case ACCELERATOR_PEDAL_POSITION_E:
                return context.getString(R.string.absolute_throttle_pos_e);
            case ACCELERATOR_PEDAL_POSITION_F:
                return context.getString(R.string.absolute_throttle_pos_f);
            case COMMANDED_THROTTLE_ACTUATOR:
                return context.getString(R.string.commanded_throttle_actuator);
            case TIME_RUN_WITH_MIL_ON:
                return context.getString(R.string.time_run_with_mil_on);
            case TIME_SINCE_TROUBLE_CODE_CLEARED:
                return context.getString(R.string.time_since_trouble_code_cleared);
            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
                return context.getString(R.string.max_val_for_fuel_air_equivalence_ratio);
            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
                return context.getString(R.string.max_val_for_air_flow_rate_from_mass_air_flow_sens);
            case FUEL_TYPE:
                return context.getString(R.string.fuel_type);
            case ETHANOL_FUEL:
                return context.getString(R.string.ethanol_fuel);
            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.absolute_evap_system_vapor_pressure);
            case EVAP_SYSTEM_VAPOR_PRESSURE:
                return context.getString(R.string.evap_system_vapor_pressure);
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
                return context.getString(R.string.sh_term_seconday_oxy_sens_trim_a1_b3);
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
                return context.getString(R.string.ln_term_seconday_oxy_sens_trim_a1_b3);
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return context.getString(R.string.sh_term_seconday_oxy_sens_trim_a2_b4);
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return context.getString(R.string.ln_term_seconday_oxy_sens_trim_a2_b4);
            case FUEL_RAIL_ABSOLUTE_PRESSURE:
                return context.getString(R.string.fuel_rail_pressure);
            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
                return context.getString(R.string.relative_accelerator_pedal_position);
            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
                return context.getString(R.string.hybrid_battery_pack_remaining_life);
            case ENGINE_OIL_TEMP:
                return context.getString(R.string.engine_oil_temp);
            case FUEL_INJECTION_TIMING:
                return context.getString(R.string.fuel_injection_timing);
            case ENGINE_FUEL_RATE:
                return context.getString(R.string.engine_fuel_rate);
            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
                return context.getString(R.string.emission_requirements_to_which_vehicle_is_designed);
            case PIDS_SUP_61_80:
                return context.getString(R.string.pids_sup_61_80);
            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
                return context.getString(R.string.drivers_demand_engine_percent_torque);
            case ACTUAL_ENGINE_PERCENT_TORQUE:
                return context.getString(R.string.actual_engine_percent_torque);
            case ENGINE_REFERENCE_TORQUE:
                return context.getString(R.string.engine_reference_torque);
            case ENGINE_PERCENT_TORQUE_DATA:
                return context.getString(R.string.engine_percent_torque_data);
            case AUXILIARY_IN_OUT_SUPPORTED:
                return context.getString(R.string.auxiliary_in_out_supported);
        }

        return context.getString(R.string.no_description_txt);

    }


    public static class EmailValidator {

        private Pattern pattern;
        private Matcher matcher;

        private static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         *
         * @param hex hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {

            matcher = pattern.matcher(hex);
            return matcher.matches();

        }
    }

}
