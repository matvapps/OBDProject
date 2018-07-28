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
                return "V";
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
                return "°C";
            case SH_TERM_FUEL_TRIM_1:
            case LN_TERM_FUEL_PERCENT_TRIM_1:
            case SH_TERM_FUEL_TRIM_2:
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return "%";
            case FUEL_PRESSURE:
                return "кПа";
            case INTAKE_MANIFOLD_PRESSURE:
                return "кПа";
            case ENGINE_RPM:
                return "rpm";
            case VEHICLE_SPEED:
                return "км/ч";
            case TIMING_ADVANCE:
                return "°";
            case INTAKE_AIR_TEMP:
                return "°C";
            case MAF_AIR_FLOW:
                return "г/сек";
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
                return "%";
            case OBD_STANDARDS_VEHICLE_CONFORMS_TO:
                return "";
            case OXY_SENS_PRESENT_4_BANKS:
                return "";
            case AUXILIARY_INPUT_STATUS:
                return "";
            case RUN_TIME_SINCE_ENGINE_START:
                return "сек";
            case PIDS_SUP_21_40:
                return "";
            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
                return "км";
            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
                return "кПа";
            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
                return "кПа";
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "ratio";
            case COMMANDED_EGR:
                return "%";
            case EGR_ERROR:
                return "%";
            case COMMANDED_EVAPORATIVE_PURGE:
                return "";
            case FUEL_TANK_LEVEL_INPUT:
                return "%";
            case WARM_UPS_SINCE_CODES_CLEARED:
                return "кол-во";
            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
                return "км";
            case SYSTEM_VAPOR_PRESSURE:
                return "Па";
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return "кПа";
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
                return "°C";
            case PIDS_SUP_41_60:
                return "";
            case MONITOR_STATUS_THIS_DRIVE_CYCLE:
                return "";
            case CONTROL_MODULE_VOLTAGE:
                return "V";
            case ABSOLUTE_LOAD_VALUE:
                return "%";
            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
                return "ratio";
            case RELATIVE_THROTTLE_POSITION:
                return "%";
            case AMBIENT_AIR_TEMPERATURE:
                return "°C";
            case ABSOLUTE_THROTTLE_POSITION_B:
            case ABSOLUTE_THROTTLE_POSITION_C:
            case ACCELERATOR_PEDAL_POSITION_D:
            case ACCELERATOR_PEDAL_POSITION_E:
            case ACCELERATOR_PEDAL_POSITION_F:
            case COMMANDED_THROTTLE_ACTUATOR:
                return "%";
            case TIME_RUN_WITH_MIL_ON:
                return "мин";
            case TIME_SINCE_TROUBLE_CODE_CLEARED:
                return "мин";
            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
                return "";
            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
                return "г/с";
            case FUEL_TYPE:
                return "";
            case ETHANOL_FUEL:
                return "%";
            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
                return "кПа";
            case EVAP_SYSTEM_VAPOR_PRESSURE:
                return "Па";
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return "%";
            case FUEL_RAIL_ABSOLUTE_PRESSURE:
                return "кПа";
            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
                return "%";
            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
                return "%";
            case ENGINE_OIL_TEMP:
                return "°C";
            case FUEL_INJECTION_TIMING:
                return "°";
            case ENGINE_FUEL_RATE:
                return "л/ч";
            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
                return "";
            case PIDS_SUP_61_80:
                return "";
            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
                return "%";
            case ACTUAL_ENGINE_PERCENT_TORQUE:
                return "%";
            case ENGINE_REFERENCE_TORQUE:
                return "нМ";
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


    public static String getDeviceNameBy(PID pid) {

        switch (pid) {
            case VOLTAGE:
                return "Напряжение";
            case PIDS_SUP_0_20:
                return "Список поддерживаемых PID[0-20]";
            case DTCS_CLEARED_MIL_DTCS:
                return "Состояние монитора после очистки DTC.";
            case FREEZE_DTCS:
                return "Обнаруженные диагностические коды ошибок";
            case FUEL_SYSTEM_STATUS:
                return "Состояние топливной системы";
            case CALCULATED_ENGINE_LOAD:
                return "Расчетное значение нагрузки на двигатель";
            case ENGINE_COOLANT_TEMP:
                return "Температура охлаждающей жидкости ";
            case SH_TERM_FUEL_TRIM_1:
                return "";
            case LN_TERM_FUEL_PERCENT_TRIM_1:
                return "";
            case SH_TERM_FUEL_TRIM_2:
                return "";
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return "";
            case FUEL_PRESSURE:
                return "Давление топлива";
            case INTAKE_MANIFOLD_PRESSURE:
                return "Давление во впускном коллекторе (абсолютное)";
            case ENGINE_RPM:
                return "Обороты двигателя ";
            case VEHICLE_SPEED:
                return "Скорость автомобиля";
            case TIMING_ADVANCE:
                return "Угол опережения зажигания";
            case INTAKE_AIR_TEMP:
                return "Температура всасываемого воздуха";
            case MAF_AIR_FLOW:
                return "Массовый расход воздуха";
            case THROTTLE_POSITION:
                return "Положение дроссельной заслонки";
            case COMMANDED_SECONDARY_AIR_STATUS:
                return "Назначенный статус вторичного воздуха";
            case OXY_SENS_PRESENT_2_BANKS:
                return "Наличие датчиков кислорода";
            case OXY_SENS_VOLT_1:
                return "";
            case OXY_SENS_VOLT_2:
                return "";
            case OXY_SENS_VOLT_3:
                return "";
            case OXY_SENS_VOLT_4:
                return "";
            case OXY_SENS_VOLT_5:
                return "";
            case OXY_SENS_VOLT_6:
                return "";
            case OXY_SENS_VOLT_7:
                return "";
            case OXY_SENS_VOLT_8:
                return "";
            case OBD_STANDARDS_VEHICLE_CONFORMS_TO:
                return "Стандарты OBD которым это ТС соответствует";
            case OXY_SENS_PRESENT_4_BANKS:
                return "Присутствие датчиков кислорода";
            case AUXILIARY_INPUT_STATUS:
                return "Состояние вспомогательного входа";
            case RUN_TIME_SINCE_ENGINE_START:
                return "Время, прошедшее с запуска двигателя ";
            case PIDS_SUP_21_40:
                return "Список поддерживаемых PID[21-40]";
            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
                return "Дистанция, пройденная с зажженной лампой «проверь двигатель»";
            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
                return "Давление в топливной рампе (относительно вакуума коллектора)";
            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
                return "Давление в топливной рампе (дизельное топливо или прямой впрыск бензина)";
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
                return "";
            case COMMANDED_EGR:
                return "Управляемый EGR";
            case EGR_ERROR:
                return "EGR ошибка";
            case COMMANDED_EVAPORATIVE_PURGE:
                return "Принудительная испарительная очистка";
            case FUEL_TANK_LEVEL_INPUT:
                return "Уровень топлива ";
            case WARM_UPS_SINCE_CODES_CLEARED:
                return "Количество прогревов со времени очистки кодов нейсправности";
            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
                return "Дистанция, пройденная со времени очистки кодов нейсправностей";
            case SYSTEM_VAPOR_PRESSURE:
                return "Evap. Давление пара в системе";
            case ABSOLUTE_BAROMETRIC_PRESSURE:
                return "Атмосферное давление (абсолютное) ";
            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
                return "";
            case CATALYST_TEMP_BANK_1_SENS_1:
                return "";
            case CATALYST_TEMP_BANK_2_SENS_1:
                return "";
            case CATALYST_TEMP_BANK_1_SENS_2:
                return "";
            case CATALYST_TEMP_BANK_2_SENS_2:
                return "";
            case PIDS_SUP_41_60:
                return "Список поддерживаемых PID[41-60]";
            case MONITOR_STATUS_THIS_DRIVE_CYCLE:
                return "Мониторинг состояния цикла привода";
            case CONTROL_MODULE_VOLTAGE:
                return "Напряжение контрольного модуля";
            case ABSOLUTE_LOAD_VALUE:
                return "Абсолютное значение нагрузки";
            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
                return "Коэффициент эквивалентности команд";
            case RELATIVE_THROTTLE_POSITION:
                return "Относительное положение дроссельной заслонки";
            case AMBIENT_AIR_TEMPERATURE:
                return "Температура окружающего воздуха";
            case ABSOLUTE_THROTTLE_POSITION_B:
                return "Абсолютное положение дроссельной заслонки B";
            case ABSOLUTE_THROTTLE_POSITION_C:
                return "Абсолютное положение дроссельной заслонки C";
            case ACCELERATOR_PEDAL_POSITION_D:
                return "Положение педали акселератора D";
            case ACCELERATOR_PEDAL_POSITION_E:
                return "Положение педали акселератора E";
            case ACCELERATOR_PEDAL_POSITION_F:
                return "Положение педали акселератора F";
            case COMMANDED_THROTTLE_ACTUATOR:
                return "Управляемый привод дроссельной заслонки";
            case TIME_RUN_WITH_MIL_ON:
                return "Время со включенной лампой «проверь двигатель»";
            case TIME_SINCE_TROUBLE_CODE_CLEARED:
                return "Время, прошедшее с момента очистки кодов неисправностей";
            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
                return "Максимальное значение для коэффициента эквивалентности";
            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
                return "Максимальное значение расхода воздуха от датчика массового расхода воздуха";
            case FUEL_TYPE:
                return "Тип топлива";
            case ETHANOL_FUEL:
                return "Этаноловое топливо";
            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
                return "Абсолютная система испарения Давление пара";
            case EVAP_SYSTEM_VAPOR_PRESSURE:
                return "Давление паров системы испарения";
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
                return "";
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
                return "";
            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return "";
            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
                return "";
            case FUEL_RAIL_ABSOLUTE_PRESSURE:
                return "Абсолютное давление на топливной рампе";
            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
                return "Относительное положение педали акселератора";
            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
                return "Заряд силовой батареи гибрида";
            case ENGINE_OIL_TEMP:
                return "Температура масла двигателя";
            case FUEL_INJECTION_TIMING:
                return "Регулирование момента впрыска";
            case ENGINE_FUEL_RATE:
                return "Расход топлива";
            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
                return "Требования к выбросам, для которых предназначено транспортное средство";
            case PIDS_SUP_61_80:
                return "Список поддерживаемых PID[61-80]";
            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
                return "Запрашиваемый момент двигателя";
            case ACTUAL_ENGINE_PERCENT_TORQUE:
                return "Реальный момент двигателя";
            case ENGINE_REFERENCE_TORQUE:
                return "Исходный момент двигателя";
            case ENGINE_PERCENT_TORQUE_DATA:
                return "Данные о крутящем моменте двигателя";
            case AUXILIARY_IN_OUT_SUPPORTED:
                return "Поддерживание дополнительный вход/выход";
        }

        return "Нет описания";

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
