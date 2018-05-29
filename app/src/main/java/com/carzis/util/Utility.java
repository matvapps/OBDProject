package com.carzis.util;

import android.content.Context;

import com.carzis.R;
import com.carzis.model.DashboardItem;

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


    public static String getDimenBy(Context context, DashboardItem.DashboardDevice deviceType) {
        switch (deviceType) {
            case RPM: {
                return context.getString(R.string.dimen_title_rpm);
            }
            case SPEED: {
                return context.getString(R.string.dimen_title_speed);
            }
            case VOLTAGE: {
                return context.getString(R.string.dimen_title_voltage);
            }
            case FUEL_AMOUNT: {
                return context.getString(R.string.dimen_title_gas);
            }
            case ENGINE_TEMP: {
                return context.getString(R.string.dimen_title_engine_temp);
            }
            case INTAKE_AIR_TEMP:
            case COOLANT_TEMP: {
                return context.getString(R.string.dimen_title_coolant_temp);
            }
            case ENGINE_OIL_PRESSURE: {
                return context.getString(R.string.dimen_title_oil_pressure);
            }
        }

        return "";
    }

    public static int getIconIdBy(DashboardItem.DashboardDevice deviceType) {
        switch (deviceType) {
            case RPM: {
                return R.drawable.ic_dvig;
            }
            case SPEED: {
                return R.drawable.ic_speed;
            }
            case VOLTAGE: {
                return R.drawable.ac;
            }
            case FUEL_AMOUNT: {
                return R.drawable.ic_bens;
            }
            case INTAKE_AIR_TEMP:
            case ENGINE_TEMP: {
                return R.drawable.temp;
            }
            case COOLANT_TEMP: {
                // TODO: change icon
                return R.drawable.temp;
            }
            case ENGINE_OIL_PRESSURE: {
                return R.drawable.wo;
            }
        }
        return -1;
    }

}
