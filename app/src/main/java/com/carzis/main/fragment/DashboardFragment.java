package com.carzis.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.base.BaseFragment;
import com.carzis.main.MainActivity;
import com.carzis.main.adapter.DashboardItemsAdapter;
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.model.DashboardItem;
import com.carzis.obd.PID;
import com.carzis.obd.PidItem;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.github.matvapps.dashboarddevices.Speedometer;
import com.github.matvapps.dashboarddevices.Tachometer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DashboardFragment extends BaseFragment implements ActivityToDashboardCallbackListener {
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

        Log.d(TAG, "setupDevices: " + devicesStr + " length " + devicesStr.length());

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
            case ENGINE_RPM:
                float turnovers = Float.parseFloat(value);
                tachometer.moveTo(turnovers);
                break;
            case VEHICLE_SPEED:
                float speed = Float.parseFloat(value);
                if (!Locale.getDefault().getLanguage().equals("ru"))
                    speed = speed / 1.609344f;
                speedometer.moveTo(speed);
                break;
            default:
                dashboardItemsAdapter.updateItem(new DashboardItem(value, pid));
                break;
        }

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
        if (!Locale.getDefault().getLanguage().equals("ru"))
            speed = speed / 1.609344f;
        speedometer.moveTo(speed);
    }

    private void setSpeed(float speed, long duration) {
        if (!Locale.getDefault().getLanguage().equals("ru"))
            speed = speed / 1.609344f;
        speedometer.moveTo(speed, duration);
    }

    private void setTurnovers(float turnovers) {
        tachometer.moveTo(turnovers);
    }

    private void setTurnovers(float turnovers, long duration) {
        tachometer.moveTo(turnovers, duration);
    }
}
