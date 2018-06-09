package com.carzis.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.main.MainActivity;
import com.carzis.main.Type;
import com.carzis.main.adapter.DashboardItemsAdapter;
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.model.DashboardItem;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.github.matvapps.dashboarddevices.Speedometer;
import com.github.matvapps.dashboarddevices.Tachometer;

import java.util.ArrayList;
import java.util.Objects;

import static com.carzis.model.DashboardItem.DashboardDevice.BAROMETRIC_PRESSURE;
import static com.carzis.model.DashboardItem.DashboardDevice.CATALYST_TEMP_B1S1;
import static com.carzis.model.DashboardItem.DashboardDevice.CATALYST_TEMP_B1S2;
import static com.carzis.model.DashboardItem.DashboardDevice.CATALYST_TEMP_B2S1;
import static com.carzis.model.DashboardItem.DashboardDevice.CATALYST_TEMP_B2S2;
import static com.carzis.model.DashboardItem.DashboardDevice.COMMANDED_EGR;
import static com.carzis.model.DashboardItem.DashboardDevice.ENGINE_COOLANT_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.ENGINE_LOAD;
import static com.carzis.model.DashboardItem.DashboardDevice.ENGINE_OIL_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.FUEL_AMOUNT;
import static com.carzis.model.DashboardItem.DashboardDevice.FUEL_PRESSURE;
import static com.carzis.model.DashboardItem.DashboardDevice.FUEL_RAIL_PRESSURE;
import static com.carzis.model.DashboardItem.DashboardDevice.FUEL_RAIL_PRESSURE_DIESEL;
import static com.carzis.model.DashboardItem.DashboardDevice.INTAKE_AIR_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.INTAKE_MAN_PRESSURE;
import static com.carzis.model.DashboardItem.DashboardDevice.LN_TERM_FUEL_PERCENT_TRIM_1;
import static com.carzis.model.DashboardItem.DashboardDevice.LN_TERM_FUEL_PERCENT_TRIM_2;
import static com.carzis.model.DashboardItem.DashboardDevice.MAF_AIR_FLOW;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_1_SENS_1;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_1_SENS_2;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_1_SENS_3;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_1_SENS_4;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_2_SENS_1;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_2_SENS_2;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_2_SENS_3;
import static com.carzis.model.DashboardItem.DashboardDevice.OXY_SENS_VOLT_B_2_SENS_4;
import static com.carzis.model.DashboardItem.DashboardDevice.RPM;
import static com.carzis.model.DashboardItem.DashboardDevice.SH_TERM_FUEL_TRIM_1;
import static com.carzis.model.DashboardItem.DashboardDevice.SH_TERM_FUEL_TRIM_2;
import static com.carzis.model.DashboardItem.DashboardDevice.SPEED;
import static com.carzis.model.DashboardItem.DashboardDevice.THROTTLE_POSITION;
import static com.carzis.model.DashboardItem.DashboardDevice.THROTTLE_POS_2;
import static com.carzis.model.DashboardItem.DashboardDevice.TIMING_ADVANCE;
import static com.carzis.model.DashboardItem.DashboardDevice.VOLTAGE;

public class DashboardFragment extends Fragment implements ActivityToDashboardCallbackListener {
    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardItemsAdapter dashboardItemsAdapter;
    private KeyValueStorage keyValueStorage;
    private LocalRepository localRepository;

    private DashboardToActivityCallbackListener dashboardToActivityCallbackListener;

    private Speedometer speedometer;
    private Tachometer tachometer;
    private RecyclerView deviceList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        speedometer = rootView.findViewById(R.id.pointerSpeedometer);
        tachometer = rootView.findViewById(R.id.pointerTachometer);
        deviceList = rootView.findViewById(R.id.devices_list);

        dashboardItemsAdapter = new DashboardItemsAdapter();
        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));


        setupDevices(keyValueStorage.getUserDashboardDevices());

        deviceList.setAdapter(dashboardItemsAdapter);

        resetgauges();

        return rootView;
    }

    private void setupDevices(String devices) {
        String devicesStr = devices.replaceAll("\\s+", "");
        dashboardItemsAdapter.setItems(new ArrayList<>());

        for (int i = 0; i < devicesStr.length(); i += 3) {
            String type = devicesStr.substring(i, i + 3);

            switch (type) {
                case "101": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", SPEED));
                    break;
                }
                case "102": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", RPM));
                    break;
                }
                case "107": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", ENGINE_OIL_TEMP));
                    break;
                }
                case "108": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", VOLTAGE));
                    break;
                }
                case "109": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", FUEL_AMOUNT));
                    break;
                }
                case "110": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", ENGINE_LOAD));

                    break;
                }
                case "111": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", ENGINE_COOLANT_TEMP));

                    break;
                }
                case "112": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", SH_TERM_FUEL_TRIM_1));

                    break;
                }
                case "113": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", LN_TERM_FUEL_PERCENT_TRIM_1));

                    break;
                }
                case "114": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", SH_TERM_FUEL_TRIM_2));

                    break;
                }
                case "115": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", LN_TERM_FUEL_PERCENT_TRIM_2));

                    break;
                }
                case "116": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", FUEL_PRESSURE));

                    break;
                }
                case "117": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", INTAKE_MAN_PRESSURE));

                    break;
                }
                case "118": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", TIMING_ADVANCE));

                    break;
                }
                case "119": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", MAF_AIR_FLOW));

                    break;
                }
                case "120": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", THROTTLE_POSITION));

                    break;
                }
                case "121": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_1_SENS_1));

                    break;
                }
                case "122": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_1_SENS_2));

                    break;
                }
                case "123": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_1_SENS_3));

                    break;
                }
                case "124": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_1_SENS_4));

                    break;
                }
                case "125": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_2_SENS_1));

                    break;
                }
                case "126": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_2_SENS_2));

                    break;
                }
                case "127": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_2_SENS_3));

                    break;
                }
                case "128": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", OXY_SENS_VOLT_B_2_SENS_4));

                    break;
                }
                case "129": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", FUEL_RAIL_PRESSURE));

                    break;
                }
                case "130": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", FUEL_RAIL_PRESSURE_DIESEL));

                    break;
                }
                case "131": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", COMMANDED_EGR));

                    break;
                }
                case "132": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", BAROMETRIC_PRESSURE));

                    break;
                }
                case "133": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", CATALYST_TEMP_B1S1));

                    break;
                }
                case "134": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", CATALYST_TEMP_B2S1));

                    break;
                }
                case "135": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", CATALYST_TEMP_B1S2));

                    break;
                }
                case "136": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", CATALYST_TEMP_B2S2));

                    break;
                }
                case "137": {
                    dashboardItemsAdapter.addItem(new DashboardItem("- ", THROTTLE_POS_2));

                    break;
                }
            }
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dashboardToActivityCallbackListener = (DashboardToActivityCallbackListener) context;
        ((MainActivity) context).activityFragmentCallbackListener = this;

    }

    public void resetgauges() {

        speedometer.speedTo(240, 1300);
        tachometer.speedTo(7001, 1300);

        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(() -> {
                speedometer.speedTo(0, 1000);
                tachometer.speedTo(0, 1000);
            });
        }).start();
    }


    public void setSpeed(float speed) {
        speedometer.speedTo(speed);
    }

    public void setSpeed(float speed, long duration) {
        speedometer.speedTo(speed, duration);
    }

    public void setTurnovers(float turnovers) {
        tachometer.speedTo(turnovers);
    }

    public void setTurnovers(float turnovers, long duration) {
        tachometer.speedTo(turnovers, duration);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupDevices(keyValueStorage.getUserDashboardDevices());
    }

    @Override
    public void onPassRealDataToFragment(Type type, String value) {

        switch (type) {

            case SPEED:
                speedometer.speedTo(Integer.parseInt(value));
                dashboardItemsAdapter.updateItem(new DashboardItem(value, SPEED));

//                localRepository.addHistoryItem(new HistoryItem(carName, SPEED.value, value, time));
                break;
            case RPM:
                tachometer.speedTo(Integer.parseInt(value));
                dashboardItemsAdapter.updateItem(new DashboardItem(value, RPM));

//                localRepository.addHistoryItem(new HistoryItem(carName, RPM.value, value, time));
                break;
            case INTAKE_AIR_TEMP:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, INTAKE_AIR_TEMP));

//                localRepository.addHistoryItem(new HistoryItem(carName, INTAKE_AIR_TEMP.value, value, time));
                break;
            case ENGINE_LOAD:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_LOAD));

//                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_LOAD.value, value, time));
                break;
            case INTAKE_MAN_PRESSURE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, INTAKE_MAN_PRESSURE));

//                localRepository.addHistoryItem(new HistoryItem(carName, INTAKE_MAN_PRESSURE.value, value, time));
                break;
            case MAF_AIR_FLOW:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, MAF_AIR_FLOW));

//                localRepository.addHistoryItem(new HistoryItem(carName, MAF_AIR_FLOW.value, value, time));
                break;
            case THROTTLE_POS:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POSITION));

//                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POSITION.value, value, time));
                break;
            case FUEL_RAIL_PRESSURE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_RAIL_PRESSURE));

//                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_RAIL_PRESSURE.value, value, time));
                break;
            case ENGINE_OIL_TEMP:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_OIL_TEMP));

//                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_OIL_TEMP.value, value, time));
                break;
            case VOLTAGE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, VOLTAGE));

//                localRepository.addHistoryItem(new HistoryItem(carName, VOLTAGE.value, value, time));
                break;
            case ENGINE_COOLANT_TEMP:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_COOLANT_TEMP));

//                localRepository.addHistoryItem(new HistoryItem(carName, ENGINE_COOLANT_TEMP.value, value, time));
                break;
            case SH_TERM_FUEL_TRIM_1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, SH_TERM_FUEL_TRIM_1));

//                localRepository.addHistoryItem(new HistoryItem(carName, SH_TERM_FUEL_TRIM_1.value, value, time));
                break;
            case LN_TERM_FUEL_PERCENT_TRIM_1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, LN_TERM_FUEL_PERCENT_TRIM_1));

//                localRepository.addHistoryItem(new HistoryItem(carName, LN_TERM_FUEL_PERCENT_TRIM_1.value, value, time));
                break;
            case SH_TERM_FUEL_TRIM_2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, SH_TERM_FUEL_TRIM_2));

//                localRepository.addHistoryItem(new HistoryItem(carName, SH_TERM_FUEL_TRIM_2.value, value, time));
                break;
            case LN_TERM_FUEL_PERCENT_TRIM_2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, LN_TERM_FUEL_PERCENT_TRIM_2));

//                localRepository.addHistoryItem(new HistoryItem(carName, LN_TERM_FUEL_PERCENT_TRIM_2.value, value, time));
                break;
            case FUEL_PRESSURE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_PRESSURE));

//                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_PRESSURE.value, value, time));
                break;
            case TIMING_ADVANCE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, TIMING_ADVANCE));

//                localRepository.addHistoryItem(new HistoryItem(carName, TIMING_ADVANCE.value, value, time));
                break;
            case THROTTLE_POSITION:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POSITION));

//                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POSITION.value, value, time));
                break;
            case OXY_SENS_VOLT_B_1_SENS_1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_1));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_1.value, value, time));
                break;
            case OXY_SENS_VOLT_B_1_SENS_2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_2));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_2.value, value, time));
                break;
            case OXY_SENS_VOLT_B_1_SENS_3:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_3));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_3.value, value, time));
                break;
            case OXY_SENS_VOLT_B_1_SENS_4:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_1_SENS_4));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_1_SENS_4.value, value, time));
                break;
            case OXY_SENS_VOLT_B_2_SENS_1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_1));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_1.value, value, time));
                break;
            case OXY_SENS_VOLT_B_2_SENS_2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_2));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_2.value, value, time));
                break;
            case OXY_SENS_VOLT_B_2_SENS_3:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_3));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_3.value, value, time));
                break;
            case OXY_SENS_VOLT_B_2_SENS_4:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, OXY_SENS_VOLT_B_2_SENS_4));

//                localRepository.addHistoryItem(new HistoryItem(carName, OXY_SENS_VOLT_B_2_SENS_4.value, value, time));
                break;
            case FUEL_RAIL_PRESSURE_DIESEL:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_RAIL_PRESSURE_DIESEL));

//                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_RAIL_PRESSURE_DIESEL.value, value, time));
                break;
            case COMMANDED_EGR:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, COMMANDED_EGR));

//                localRepository.addHistoryItem(new HistoryItem(carName, COMMANDED_EGR.value, value, time));
                break;
            case FUEL_AMOUNT:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_AMOUNT));

//                localRepository.addHistoryItem(new HistoryItem(carName, FUEL_AMOUNT.value, value, time));
                break;
            case BAROMETRIC_PRESSURE:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, BAROMETRIC_PRESSURE));

//                localRepository.addHistoryItem(new HistoryItem(carName, BAROMETRIC_PRESSURE.value, value, time));
                break;
            case CATALYST_TEMP_B1S1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B1S1));

//                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B1S1.value, value, time));
                break;
            case CATALYST_TEMP_B2S1:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B2S1));

//                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B2S1.value, value, time));
                break;
            case CATALYST_TEMP_B1S2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B1S2));

//                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B1S2.value, value, time));
                break;
            case CATALYST_TEMP_B2S2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, CATALYST_TEMP_B2S2));

//                localRepository.addHistoryItem(new HistoryItem(carName, CATALYST_TEMP_B2S2.value, value, time));
                break;
            case THROTTLE_POS_2:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, THROTTLE_POS_2));

//                localRepository.addHistoryItem(new HistoryItem(carName, THROTTLE_POS_2.value, value, time));
                break;

        }
    }

    @Override
    public void onAddNewDevice() {
        AdditionalActivity.start(getActivity(), AdditionalActivity.ADD_DEVICE_FRAGMENT);
    }
}
