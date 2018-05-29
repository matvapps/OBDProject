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
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.adapter.DashboardItemsAdapter;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.main.MainActivity;
import com.carzis.main.Type;
import com.carzis.model.DashboardItem;
import com.github.matvapps.dashboarddevices.Speedometer;
import com.github.matvapps.dashboarddevices.Tachometer;

import static com.carzis.model.DashboardItem.DashboardDevice.COOLANT_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.ENGINE_OIL_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.FUEL_AMOUNT;
import static com.carzis.model.DashboardItem.DashboardDevice.INTAKE_AIR_TEMP;
import static com.carzis.model.DashboardItem.DashboardDevice.VOLTAGE;

public class DashboardFragment extends Fragment implements ActivityToDashboardCallbackListener {
    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardItemsAdapter dashboardItemsAdapter;

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

        dashboardItemsAdapter.addItem(new DashboardItem("0", VOLTAGE));
        dashboardItemsAdapter.addItem(new DashboardItem("0", INTAKE_AIR_TEMP));
        dashboardItemsAdapter.addItem(new DashboardItem("0", COOLANT_TEMP));
        dashboardItemsAdapter.addItem(new DashboardItem("0", FUEL_AMOUNT));

        deviceList.setAdapter(dashboardItemsAdapter);

        resetgauges();

        return rootView;
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
    public void onPassRealDataToFragment(Type type, String value) {
        switch (type) {
            case SPEED: {
                setSpeed(Float.parseFloat(value));
                break;
            }
            case TURNOVERS: {
                setTurnovers(Float.parseFloat(value));
                break;
            }
            case INTAKE_AIR_TEMP: {
                dashboardItemsAdapter.updateItem(new DashboardItem(value, INTAKE_AIR_TEMP));
                break;
            }
            case ENGINE_LOAD: {

                break;
            }
            case COOLANT_TEMP: {
                dashboardItemsAdapter.updateItem(new DashboardItem(value, COOLANT_TEMP));
                break;
            }
            case INTAKE_MAN_PRESSURE: {

                break;
            }
            case MAF_AIR_FLOW: {

                break;
            }
            case THROTTLE_POS: {

                break;
            }
            case FUEL_RAIL_PRESSURE: {

                break;
            }
            case FUEL_LEVEL: {

                Log.d(TAG, "onPassRealDataToFragment: fuel level" + value);
                dashboardItemsAdapter.updateItem(new DashboardItem(value, FUEL_AMOUNT));
                break;
            }
            case DISTANCE_TRAVELED: {

                break;
            }
            case AMBIENT_AIR_TEMP: {

                break;
            }
            case ENGINE_OIL_TEMP: {
                dashboardItemsAdapter.updateItem(new DashboardItem(value, ENGINE_OIL_TEMP));
                break;
            }
            case VOLTAGE: {
                dashboardItemsAdapter.updateItem(new DashboardItem(value, VOLTAGE));
                break;
            }
        }
    }
}
