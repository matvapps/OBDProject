package com.obdelm327pro.util;

import android.content.Context;

import com.obdelm327pro.R;
import com.obdelm327pro.model.DashboardItem;

/**
 * Created by Alexandr.
 */
public class Utility {

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
            case GAS_AMOUNT: {
                return context.getString(R.string.dimen_title_gas);
            }
            case ENGINE_TEMP: {
                return context.getString(R.string.dimen_title_engine_temp);
            }
            case INTAKE_TEMP:
            case COOLANT_TEMP: {
                return context.getString(R.string.dimen_title_coolant_temp);
            }
            case OIL_PRESSURE: {
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
            case GAS_AMOUNT: {
                return R.drawable.ic_bens;
            }
            case INTAKE_TEMP:
            case ENGINE_TEMP: {
                return R.drawable.temp;
            }
            case COOLANT_TEMP: {
                // TODO: change icon
                return R.drawable.temp;
            }
            case OIL_PRESSURE: {
                return R.drawable.wo;
            }
        }
        return -1;
    }

}
