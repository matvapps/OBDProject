package com.carzis.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.main.MainActivity;
import com.carzis.main.adapter.DashboardItemsAdapter;
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.model.DashboardItem;
import com.carzis.model.PID;
import com.carzis.obd.PidItem;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.github.matvapps.dashboarddevices.Speedometer;
import com.github.matvapps.dashboarddevices.Tachometer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment implements ActivityToDashboardCallbackListener {
    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardItemsAdapter dashboardItemsAdapter;
    private KeyValueStorage keyValueStorage;
    private LocalRepository localRepository;
    private ArrayList<String> supportedDevices;

    private DashboardToActivityCallbackListener dashboardToActivityCallbackListener;

    private Speedometer speedometer;
    private Tachometer tachometer;
    private RecyclerView deviceList;
//    private ImageView backround;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        speedometer = rootView.findViewById(R.id.pointerSpeedometer);
        tachometer = rootView.findViewById(R.id.pointerTachometer);
        deviceList = rootView.findViewById(R.id.devices_list);
//        backround = rootView.findViewById(R.id.background);

        dashboardItemsAdapter = new DashboardItemsAdapter();
        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));
        supportedDevices = new ArrayList<>();

        setupDevices(keyValueStorage.getUserDashboardDevices());
        deviceList.setAdapter(dashboardItemsAdapter);

        // reset gauges on dashboard start
        resetgauges();

        return rootView;
    }

    // lets start animate gauges
    public void resetgauges() {
        speedometer.moveTo(240, 1300); // move to max speed, duration 1300
        tachometer.moveTo(7000, 1300); // move to max turnovers, duration 1300
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    // uncomment this to enable revert action
                    speedometer.moveTo(0, 1000);
                    tachometer.moveTo(0, 1000);
                });
            }
        }).start();
    }


    private void setupDevices(String devices) {
        String devicesStr = devices.replaceAll("\\s+", "");
        dashboardItemsAdapter.setItems(new ArrayList<>());

        for (int i = 0; i < devicesStr.length(); i += 4) {
            String pidStr = devicesStr.substring(i, i + 4);

            if (PidItem.contains(pidStr)) {
                PID pid = PID.getEnumByString(pidStr);
//                Log.d(TAG, "setupDevices: supportedPIDS: " + supportedDevices);
//                Log.d(TAG, "setupDevices: supportedPIDS preaddDashboardItem" + pid.getCommand());
//                Log.d(TAG, "setupDevices: supportedPIDS pids" + supportedDevices);
//                if (isInSupportedPids(pid.getCommand())) {
//                    Log.d(TAG, "setupDevices: supportedPIDS addDashboardItem" + pid.getCommand());
                    dashboardItemsAdapter.addItem(new DashboardItem("-", pid));
//                }
//                else {
//                    keyValueStorage.removeDeviceFromDashboard(pid);
//                }
            }
        }


    }

    private boolean isInSupportedPids(String pid) {
        return supportedDevices.contains(pid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dashboardToActivityCallbackListener = (DashboardToActivityCallbackListener) context;
        ((MainActivity) context).activityToDashboardCallbackListener = this;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: supportedPIDS" + keyValueStorage.getUserDashboardDevices());
        setupDevices(keyValueStorage.getUserDashboardDevices());
    }

    @Override
    public void onPassRealDataToFragment(PID pid, String value) {
        Log.d(TAG, "onPassRealDataToFragment: PID: " + pid + ", value=" + value);

        switch (pid) {
//            case VOLTAGE:
//
//                break;
//            case PIDS_SUP_0_20:
//
//                break;
//            case DTCS_CLEARED_MIL_DTCS:
//
//                break;
//            case FREEZE_DTCS:
//
//                break;
//            case FUEL_SYSTEM_STATUS:
//
//                break;
//            case CALCULATED_ENGINE_LOAD:
//
//                break;
//            case ENGINE_COOLANT_TEMP:
//
//                break;
//            case SH_TERM_FUEL_TRIM_1:
//
//                break;
//            case LN_TERM_FUEL_PERCENT_TRIM_1:
//
//                break;
//            case SH_TERM_FUEL_TRIM_2:
//
//                break;
//            case LN_TERM_FUEL_PERCENT_TRIM_2:
//
//                break;
//            case FUEL_PRESSURE:
//
//                break;
//            case INTAKE_MANIFOLD_PRESSURE:
//
//                break;
            case ENGINE_RPM:
                float turnovers = Float.parseFloat(value);
                tachometer.moveTo(turnovers);
                break;
            case VEHICLE_SPEED:
                float speed = Float.parseFloat(value);
                speedometer.moveTo(speed);
                break;
            default:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, pid));
                break;
//            case TIMING_ADVANCE:
//
//                break;
//            case INTAKE_AIR_TEMP:
//
//                break;
//            case MAF_AIR_FLOW:
//
//                break;
//            case THROTTLE_POSITION:
//
//                break;
//            case COMMANDED_SECONDARY_AIR_STATUS:
//
//                break;
//            case OXY_SENS_PRESENT_2_BANKS:
//
//                break;
//            case OXY_SENS_VOLT_1:
//
//                break;
//            case OXY_SENS_VOLT_2:
//
//                break;
//            case OXY_SENS_VOLT_3:
//
//                break;
//            case OXY_SENS_VOLT_4:
//
//                break;
//            case OXY_SENS_VOLT_5:
//
//                break;
//            case OXY_SENS_VOLT_6:
//
//                break;
//            case OXY_SENS_VOLT_7:
//
//                break;
//            case OXY_SENS_VOLT_8:
//
//                break;
//            case OBD_STANDARDS_VEHICLE_CONFORMS_TO:
//
//                break;
//            case OXY_SENS_PRESENT_4_BANKS:
//
//                break;
//            case AUXILIARY_INPUT_STATUS:
//
//                break;
//            case RUN_TIME_SINCE_ENGINE_START:
//
//                break;
//            case PIDS_SUP_21_40:
//
//                break;
//            case DISTANCE_TRAVELED_WITH_MALFUNCTION_ON:
//
//                break;
//            case FUEL_RAIL_PRESSURE_MANIFOLD_VACUUM:
//
//                break;
//            case FUEL_RAIL_GAUGE_PRESSURE_DIESEL_GAS_DIRECT_INJECT:
//
//                break;
//            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_VOLTAGE:
//
//                break;
//            case COMMANDED_EGR:
//
//                break;
//            case EGR_ERROR:
//
//                break;
//            case COMMANDED_EVAPORATIVE_PURGE:
//
//                break;
//            case FUEL_TANK_LEVEL_INPUT:
//
//                break;
//            case WARM_UPS_SINCE_CODES_CLEARED:
//
//                break;
//            case DISTANCE_TRAVELED_SINCE_CODES_CLEARED:
//
//                break;
//            case SYSTEM_VAPOR_PRESSURE:
//
//                break;
//            case ABSOLUTE_BAROMETRIC_PRESSURE:
//
//                break;
//            case OXY_SENS_1_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_2_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_3_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_4_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_5_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_6_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_7_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case OXY_SENS_8_FUEL_AIR_EQUIVALENCE_RATIO_CURRENT:
//
//                break;
//            case CATALYST_TEMP_BANK_1_SENS_1:
//
//                break;
//            case CATALYST_TEMP_BANK_2_SENS_1:
//
//                break;
//            case CATALYST_TEMP_BANK_1_SENS_2:
//
//                break;
//            case CATALYST_TEMP_BANK_2_SENS_2:
//
//                break;
//            case PIDS_SUP_41_60:
//
//                break;
//            case MONITOR_STATUS_THIS_DRIVE_CYCLE:
//
//                break;
//            case CONTROL_MODULE_VOLTAGE:
//
//                break;
//            case ABSOLUTE_LOAD_VALUE:
//
//                break;
//            case FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO:
//
//                break;
//            case RELATIVE_THROTTLE_POSITION:
//
//                break;
//            case AMBIENT_AIR_TEMPERATURE:
//
//                break;
//            case ABSOLUTE_THROTTLE_POSITION_B:
//
//                break;
//            case ABSOLUTE_THROTTLE_POSITION_C:
//
//                break;
//            case ACCELERATOR_PEDAL_POSITION_D:
//
//                break;
//            case ACCELERATOR_PEDAL_POSITION_E:
//
//                break;
//            case ACCELERATOR_PEDAL_POSITION_F:
//
//                break;
//            case COMMANDED_THROTTLE_ACTUATOR:
//
//                break;
//            case TIME_RUN_WITH_MIL_ON:
//
//                break;
//            case TIME_SINCE_TROUBLE_CODE_CLEARED:
//
//                break;
//            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
//
//                break;
//            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
//
//                break;
//            case FUEL_TYPE:
//
//                break;
//            case ETHANOL_FUEL:
//
//                break;
//            case ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE:
//
//                break;
//            case EVAP_SYSTEM_VAPOR_PRESSURE:
//
//                break;
//            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
//
//                break;
//            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A1_B3:
//
//                break;
//            case SHORT_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
//
//                break;
//            case LONG_TERM_SECONDARY_OXY_SENS_TRIM_A2_B4:
//
//                break;
//            case FUEL_RAIL_ABSOLUTE_PRESSURE:
//
//                break;
//            case RELATIVE_ACCELERATOR_PEDAL_POSITION:
//
//                break;
//            case HYBRID_BATTERY_PACK_REMAINING_LIFE:
//
//                break;
//            case ENGINE_OIL_TEMP:
//
//                break;
//            case FUEL_INJECTION_TIMING:
//
//                break;
//            case ENGINE_FUEL_RATE:
//
//                break;
//            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
//
//                break;
//            case PIDS_SUP_61_80:
//
//                break;
//            case DRIVERS_DEMAND_ENGINE_PERCENT_TORQUE:
//
//                break;
//            case ACTUAL_ENGINE_PERCENT_TORQUE:
//
//                break;
//            case ENGINE_REFERENCE_TORQUE:
//
//                break;
//            case ENGINE_PERCENT_TORQUE_DATA:
//
//                break;
//            case AUXILIARY_IN_OUT_SUPPORTED:
//
//                break;
        }

//        switch (type) {
//
//            case SPEED:
//                speedometer.moveTo(Integer.parseInt(value));
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, SPEED));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, SPEED.value, value, time));
//                break;
//            case RPM:
//                tachometer.moveTo(Integer.parseInt(value));
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, RPM));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, RPM.value, value, time));
//                break;
//            case INTAKE_AIR_TEMP:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, INTAKE_AIR_TEMP));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, INTAKE_AIR_TEMP.value, value, time));
//                break;
//            case ENGINE_LOAD:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_LOAD));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_LOAD.value, value, time));
//                break;
//            case INTAKE_MAN_PRESSURE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, INTAKE_MAN_PRESSURE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, INTAKE_MAN_PRESSURE.value, value, time));
//                break;
//            case MAF_AIR_FLOW:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, MAF_AIR_FLOW));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, MAF_AIR_FLOW.value, value, time));
//                break;
//            case THROTTLE_POS:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POSITION));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POSITION.value, value, time));
//                break;
//            case FUEL_RAIL_PRESSURE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_RAIL_PRESSURE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_RAIL_PRESSURE.value, value, time));
//                break;
//            case ENGINE_OIL_TEMP:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_OIL_TEMP));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_OIL_TEMP.value, value, time));
//                break;
//            case VOLTAGE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, VOLTAGE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, VOLTAGE.value, value, time));
//                break;
//            case ENGINE_COOLANT_TEMP:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_COOLANT_TEMP));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_COOLANT_TEMP.value, value, time));
//                break;
//            case SH_TERM_FUEL_TRIM_1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, SH_TERM_FUEL_TRIM_1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, SH_TERM_FUEL_TRIM_1.value, value, time));
//                break;
//            case LN_TERM_FUEL_PERCENT_TRIM_1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, LN_TERM_FUEL_PERCENT_TRIM_1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, LN_TERM_FUEL_PERCENT_TRIM_1.value, value, time));
//                break;
//            case SH_TERM_FUEL_TRIM_2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, SH_TERM_FUEL_TRIM_2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, SH_TERM_FUEL_TRIM_2.value, value, time));
//                break;
//            case LN_TERM_FUEL_PERCENT_TRIM_2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, LN_TERM_FUEL_PERCENT_TRIM_2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, LN_TERM_FUEL_PERCENT_TRIM_2.value, value, time));
//                break;
//            case FUEL_PRESSURE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_PRESSURE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_PRESSURE.value, value, time));
//                break;
//            case TIMING_ADVANCE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, TIMING_ADVANCE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, TIMING_ADVANCE.value, value, time));
//                break;
//            case THROTTLE_POSITION:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POSITION));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POSITION.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_1_SENS_1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_1.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_1_SENS_2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_2.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_1_SENS_3:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_3));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_3.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_1_SENS_4:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_4));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_4.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_2_SENS_1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_1.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_2_SENS_2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_2.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_2_SENS_3:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_3));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_3.value, value, time));
//                break;
//            case OXY_SENS_VOLT_B_2_SENS_4:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_4));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_4.value, value, time));
//                break;
//            case FUEL_RAIL_PRESSURE_DIESEL:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_RAIL_PRESSURE_DIESEL));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_RAIL_PRESSURE_DIESEL.value, value, time));
//                break;
//            case COMMANDED_EGR:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, COMMANDED_EGR));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, COMMANDED_EGR.value, value, time));
//                break;
//            case FUEL_AMOUNT:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_AMOUNT));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_AMOUNT.value, value, time));
//                break;
//            case BAROMETRIC_PRESSURE:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, BAROMETRIC_PRESSURE));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, BAROMETRIC_PRESSURE.value, value, time));
//                break;
//            case CATALYST_TEMP_B1S1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B1S1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B1S1.value, value, time));
//                break;
//            case CATALYST_TEMP_B2S1:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B2S1));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B2S1.value, value, time));
//                break;
//            case CATALYST_TEMP_B1S2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B1S2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B1S2.value, value, time));
//                break;
//            case CATALYST_TEMP_B2S2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B2S2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B2S2.value, value, time));
//                break;
//            case THROTTLE_POS_2:
//                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POS_2));
//
////                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POS_2.value, value, time));
//                break;
//        }
    }

    @Override
    public void onAddNewDevice(List<String> supportedPIDS) {
//        Log.d(TAG, "onAddNewDevice: supportedPIDS" + supportedPIDS);
        setSupportedDevices(supportedDevices);
        AdditionalActivity.start(getActivity(), AdditionalActivity.ADD_DEVICE_FRAGMENT, (ArrayList<String>) supportedPIDS);
    }

    public ArrayList<String> getSupportedDevices() {
        return supportedDevices;
    }

    public void setSupportedDevices(ArrayList<String> supportedDevices) {
        this.supportedDevices = supportedDevices;
        setupDevices(keyValueStorage.getUserDashboardDevices());
//        Log.d(TAG, "setSupportedDevices: supportedPIDS " + this.supportedDevices);
    }

    private void setSpeed(float speed) {
        speedometer.moveTo(speed);
    }
    private void setSpeed(float speed, long duration) {
        speedometer.moveTo(speed, duration);
    }
    private void setTurnovers(float turnovers) {
        tachometer.moveTo(turnovers);
    }
    private void setTurnovers(float turnovers, long duration) {
        tachometer.moveTo(turnovers, duration);
    }
}
