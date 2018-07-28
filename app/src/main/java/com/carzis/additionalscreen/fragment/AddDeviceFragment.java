package com.carzis.additionalscreen.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.additionalscreen.adapter.DeviceListAdapter;
import com.carzis.base.BaseFragment;
import com.carzis.model.DashboardItem;
import com.carzis.obd.PID;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class AddDeviceFragment extends BaseFragment implements PurchasesUpdatedListener {

    private final String TAG = AddDeviceFragment.class.getSimpleName();

    private RecyclerView deviceListView;
    private NestedScrollView scrollViewContainer;
    private TextView textView;

    private BillingClient mBillingClient;
    private DeviceListAdapter deviceListAdapter;
    private KeyValueStorage keyValueStorage;

    private String userDashboardDevices;
    private List<String> supportedPids;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_device, container, false);

        Bundle args = getArguments();

        assert args != null;
        supportedPids = args.getStringArrayList(AdditionalActivity.SUPPORTED_PIDS_EXTRA);

        scrollViewContainer = rootView.findViewById(R.id.scroll_view_container);
        deviceListView = rootView.findViewById(R.id.devices_list);
        textView = rootView.findViewById(R.id.add_device_txt);


        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));
        userDashboardDevices = "";


        deviceListView.setFocusable(false);
        textView.requestFocus();

        deviceListView.setNestedScrollingEnabled(false);

        int spanCount = 4;
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT)
            spanCount = 2;

        deviceListView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        deviceListView.addItemDecoration(new GridSpacingItemDecoration(spanCount, 20, false));

        deviceListAdapter = new DeviceListAdapter();

        deviceListAdapter.setOnItemClickListener((pid, enabled) -> {
            Toast.makeText(getActivity(), R.string.pay_for_diagonostics, Toast.LENGTH_SHORT).show();
//            setupUserDashboardDevices();
        });

        mBillingClient = BillingClient.newBuilder(getContext()).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                            .setSku("com.carzis.product.diagnostics")
                            .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                            .build();
                    mBillingClient.launchBillingFlow(getActivity(), flowParams);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });

        deviceListView.setAdapter(deviceListAdapter);
        deviceListAdapter.setItems(getPidDevicesFrom(supportedPids));
        setupUserDashboardDevices();

//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.VOLTAGE));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.ENGINE_LOAD));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.ENGINE_COOLANT_TEMP));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.SH_TERM_FUEL_TRIM_1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.LN_TERM_FUEL_PERCENT_TRIM_1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.SH_TERM_FUEL_TRIM_2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.LN_TERM_FUEL_PERCENT_TRIM_2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.FUEL_PRESSURE));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.INTAKE_MAN_PRESSURE));
////        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.RPM));
////        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.SPEED));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.TIMING_ADVANCE));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.INTAKE_AIR_TEMP));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.MAF_AIR_FLOW));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.THROTTLE_POSITION));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_1_SENS_1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_1_SENS_2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_1_SENS_3));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_1_SENS_4));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_2_SENS_1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_2_SENS_2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_2_SENS_3));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.OXY_SENS_VOLT_B_2_SENS_4));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.FUEL_RAIL_PRESSURE));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.FUEL_RAIL_PRESSURE_DIESEL));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.COMMANDED_EGR));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.FUEL_AMOUNT));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.BAROMETRIC_PRESSURE));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.CATALYST_TEMP_B1S1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.CATALYST_TEMP_B2S1));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.CATALYST_TEMP_B1S2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.CATALYST_TEMP_B2S2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.THROTTLE_POS_2));
//        deviceListAdapter.addItem(new DashboardItem("0", DashboardDevice.ENGINE_OIL_TEMP));

        return rootView;
    }

    private void setupUserDashboardDevices() {
        String devices = keyValueStorage.getUserDashboardDevices();
        userDashboardDevices = devices;

        Log.d(TAG, "setupUserDashboardDevices: " + userDashboardDevices);

        deviceListAdapter.setUserDashboardDevices(userDashboardDevices);

    }


    private List<DashboardItem> getPidDevicesFrom(List<String> supportedPids) {
        List<DashboardItem> items = new ArrayList<>();
        if (supportedPids.size() == 0)
            return items;

        for (String item : supportedPids) {
            PID pid = PID.getEnumByString(item);
//            PidItem pidItem = new PidItem(pid);
            DashboardItem dashboardItem = new DashboardItem("-", pid);

            items.add(dashboardItem);
        }

        return items;
    }


    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            deviceListAdapter.setOnItemClickListener((pid, enabled) -> {
                if (enabled) {
                    keyValueStorage.addDeviceToDashboard(pid);
                    Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices());
                } else {
                    keyValueStorage.removeDeviceFromDashboard(pid);
                    Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices()
                    );
                }
//            setupUserDashboardDevices();
            });
        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            deviceListAdapter.setOnItemClickListener((pid, enabled) -> {
                if (enabled) {
                    keyValueStorage.addDeviceToDashboard(pid);
                    Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices());
                } else {
                    keyValueStorage.removeDeviceFromDashboard(pid);
                    Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices()
                    );
                }
//            setupUserDashboardDevices();
            });
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
        }
    }
}
