package com.carzis.util;

import android.content.Context;

import com.carzis.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.carzis.model.DashboardItem.DashboardDevice;

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


    public static String getDeviceDimenBy(Context context, DashboardDevice deviceType) {
        switch (deviceType) {
            case RPM: {
                return context.getString(R.string.dimen_title_rpm);
            }
            case SPEED: {
                return context.getString(R.string.dimen_title_speed);
            }
            case OXY_SENS_VOLT_B_1_SENS_1:
            case OXY_SENS_VOLT_B_1_SENS_2:
            case OXY_SENS_VOLT_B_1_SENS_3:
            case OXY_SENS_VOLT_B_1_SENS_4:
            case OXY_SENS_VOLT_B_2_SENS_1:
            case OXY_SENS_VOLT_B_2_SENS_2:
            case OXY_SENS_VOLT_B_2_SENS_3:
            case OXY_SENS_VOLT_B_2_SENS_4:
            case VOLTAGE: {
                return context.getString(R.string.dimen_title_voltage);
            }

            case COMMANDED_EGR:
            case THROTTLE_POS_2:
            case THROTTLE_POSITION:
            case SH_TERM_FUEL_TRIM_1 :
            case LN_TERM_FUEL_PERCENT_TRIM_1:
            case SH_TERM_FUEL_TRIM_2 :
            case LN_TERM_FUEL_PERCENT_TRIM_2:
            case ENGINE_LOAD:
            case FUEL_AMOUNT: {
                return context.getString(R.string.dimen_title_gas);
            }
            case ENGINE_OIL_TEMP:
            case INTAKE_AIR_TEMP:
            case ENGINE_COOLANT_TEMP:
            case CATALYST_TEMP_B1S1:
            case CATALYST_TEMP_B1S2:
            case CATALYST_TEMP_B2S1:
            case CATALYST_TEMP_B2S2:
                return context.getString(R.string.dimen_title_engine_temp);

            case BAROMETRIC_PRESSURE:
            case FUEL_RAIL_PRESSURE:
            case FUEL_RAIL_PRESSURE_DIESEL:
            case INTAKE_MAN_PRESSURE:
            case FUEL_PRESSURE:
                return "кПа";

            case TIMING_ADVANCE:
                return "°";

            case MAF_AIR_FLOW:
                return "г/с";
        }

        return "";
    }

    public static int getDeviceIconIdBy(DashboardDevice deviceType) {
        switch (deviceType) {
            case RPM: {
                return R.drawable.ic_trouble_engine;
            }
            case SPEED: {
                return R.drawable.ic_speed;
            }
            case OXY_SENS_VOLT_B_1_SENS_1:
            case OXY_SENS_VOLT_B_1_SENS_2:
            case OXY_SENS_VOLT_B_1_SENS_3:
            case OXY_SENS_VOLT_B_1_SENS_4:
            case OXY_SENS_VOLT_B_2_SENS_1:
            case OXY_SENS_VOLT_B_2_SENS_2:
            case OXY_SENS_VOLT_B_2_SENS_3:
            case OXY_SENS_VOLT_B_2_SENS_4:
            case VOLTAGE: {
                return R.drawable.ac;
            }
            case FUEL_AMOUNT: {
                return R.drawable.ic_bens;
            }
            case ENGINE_LOAD:
                return R.drawable.ic_dvig;
            case FUEL_PRESSURE:
                return R.drawable.ic_topl;
            case BAROMETRIC_PRESSURE:
                return R.drawable.ic_atmos;
            case INTAKE_AIR_TEMP:
            case ENGINE_COOLANT_TEMP:
            case CATALYST_TEMP_B1S1:
            case CATALYST_TEMP_B1S2:
            case CATALYST_TEMP_B2S1:
            case CATALYST_TEMP_B2S2:
                return R.drawable.temp;

            case ENGINE_OIL_TEMP:
                return R.drawable.wo;
        }
        return R.drawable.ic_zaglush;
    }


    public static String getDeviceNameBy(DashboardDevice deviceType) {

        switch (deviceType) {
            case VOLTAGE:
                return "Напряжение аккумулятора";
            case ENGINE_LOAD :
                return "Нагрузка на двигатель";
            case ENGINE_COOLANT_TEMP :
                return "Температура охлаждающей жидкости";
            case SH_TERM_FUEL_TRIM_1 :
                return "Кратковременный топливный баланс (Bank 1)";
            case LN_TERM_FUEL_PERCENT_TRIM_1:
                return "Долговременный топливный баланс (Bank 1)";
            case SH_TERM_FUEL_TRIM_2 :
                return "Кратковременный топливный баланс (Bank 2)";
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                return "Долговременный топливный баланс (Bank 2)";
            case FUEL_PRESSURE :
                return "Давление топлива";
            case INTAKE_MAN_PRESSURE :
                return "Давление во впускном коллекторе ";
            case RPM:
                return "Обороты двигателя";
            case SPEED:
                return "Скорость автомобиля";
            case TIMING_ADVANCE :
                return "Угол опережения зажигания";
            case INTAKE_AIR_TEMP:
                return "Температура воздуха на впуске";
            case MAF_AIR_FLOW :
                return "Массовый расход воздуха";
            case THROTTLE_POSITION :
                return "Положение дроссельной заслонки";
            case OXY_SENS_VOLT_B_1_SENS_1 :
                return "Напряжение цепи датчика O2 Bank 1, Sensor 1";
            case OXY_SENS_VOLT_B_1_SENS_2 :
                return "Напряжение цепи датчика O2 Bank 1, Sensor 2";
            case OXY_SENS_VOLT_B_1_SENS_3 :
                return "Напряжение цепи датчика O2 Bank 1, Sensor 3";
            case OXY_SENS_VOLT_B_1_SENS_4 :
                return "Напряжение цепи датчика O2 Bank 1, Sensor 4";
            case OXY_SENS_VOLT_B_2_SENS_1 :
                return "Напряжение цепи датчика O2 Bank 2, Sensor 1";
            case OXY_SENS_VOLT_B_2_SENS_2 :
                return "Напряжение цепи датчика O2 Bank 2, Sensor 2";
            case OXY_SENS_VOLT_B_2_SENS_3 :
                return "Напряжение цепи датчика O2 Bank 2, Sensor 3";
            case OXY_SENS_VOLT_B_2_SENS_4 :
                return "Напряжение цепи датчика O2 Bank 2, Sensor 4";
            case FUEL_RAIL_PRESSURE :
                return "Давления топлива";
            case FUEL_RAIL_PRESSURE_DIESEL :
                return "Давления топлива (дизель, газ)";
            case COMMANDED_EGR :
                return "Клапан EGR";
            case FUEL_AMOUNT:
                return "Уровень топлива";
            case BAROMETRIC_PRESSURE :
                return "Атмосферное давление";
            case CATALYST_TEMP_B1S1 :
                return "Температура катализатора Bank 1, Sensor 1";
            case CATALYST_TEMP_B2S1 :
                return "Температура катализатора Bank 2, Sensor 1";
            case CATALYST_TEMP_B1S2 :
                return "Температура катализатора Bank 1, Sensor 2";
            case CATALYST_TEMP_B2S2 :
                return "Температура катализатора Bank 2, Sensor 2";
            case THROTTLE_POS_2 :
                return "Положение дроссельной заслонки";
            case ENGINE_OIL_TEMP:
                return "Температура масла двигателя";
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
