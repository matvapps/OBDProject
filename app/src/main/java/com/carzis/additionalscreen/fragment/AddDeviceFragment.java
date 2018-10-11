package com.carzis.additionalscreen.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.carzis.CarzisApplication;
import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.additionalscreen.adapter.DeviceListAdapter;
import com.carzis.additionalscreen.listener.OnDeviceClickListener;
import com.carzis.base.BaseFragment;
import com.carzis.history.HistoryActivity;
import com.carzis.model.DashboardItem;
import com.carzis.obd.PID;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.View.GONE;
import static com.carzis.main.MainActivity.BROADCAST_PID_EXTRA;
import static com.carzis.main.MainActivity.BROADCAST_VALUE_EXTRA;
import static com.carzis.main.MainActivity.RECEIVED_DATA_FROM_CAR;

/**
 * Created by Alexandr.
 */
public class AddDeviceFragment extends BaseFragment implements PurchasesUpdatedListener {

    private final String TAG = AddDeviceFragment.class.getSimpleName();

    private RecyclerView deviceListView;
//    private NestedScrollView scrollViewContainer;
    private TextView textView;
    private Button btnWatchGraphsOnline;

    private BillingClient mBillingClient;
    private DeviceListAdapter deviceListAdapter;
    private KeyValueStorage keyValueStorage;

    private String userDashboardDevices;
    private List<String> supportedPids;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_device, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter(RECEIVED_DATA_FROM_CAR));

        Bundle args = getArguments();

        assert args != null;
        supportedPids = args.getStringArrayList(AdditionalActivity.SUPPORTED_PIDS_EXTRA);

//        scrollViewContainer = rootView.findViewById(R.id.scroll_view_container);
        deviceListView = rootView.findViewById(R.id.devices_list);
        textView = rootView.findViewById(R.id.add_device_txt);
        btnWatchGraphsOnline = rootView.findViewById(R.id.btn_watch_graphs);

        btnWatchGraphsOnline.setOnClickListener(view -> {
            if (deviceListAdapter.getSelectedItems().isEmpty()) {
                Toast.makeText(getContext(), getContext().getString(R.string.select_sensors), Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<String> pidCodes = new ArrayList<>();
            for (DashboardItem item : deviceListAdapter.getSelectedItems()) {
                pidCodes.add(item.getPid());
            }
            HistoryActivity.start(getContext(), true, pidCodes);
        });

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
        deviceListView.setAdapter(deviceListAdapter);

        mBillingClient = BillingClient.newBuilder(getContext()).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {

                    Purchase.PurchasesResult subsPurchaseResult = mBillingClient.queryPurchases(BillingClient.SkuType.SUBS);
                    boolean isSubscript = false;
                    for (Purchase purchase : subsPurchaseResult.getPurchasesList()) {

                        if (purchase.getSku().equals(CarzisApplication.SUBSCRIPTION_BILLING_ID)) {
                            isSubscript = true;
                        }
                    }
                    Purchase.PurchasesResult prodPurchaseResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
                    boolean isBuyFull = false;
                    for (Purchase purchase : prodPurchaseResult.getPurchasesList()) {
                        if (purchase.getSku().equals(CarzisApplication.DIAGNOSTICS_BILLING_ID)) {
                            isBuyFull = true;
                        }
                    }

                    if (!isSubscript && !isBuyFull) {
                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                .setSku(CarzisApplication.SUBSCRIPTION_BILLING_ID)
                                .setType(BillingClient.SkuType.SUBS) // SkuType.SUB for subscription
                                .build();
                        mBillingClient.launchBillingFlow(getActivity(), flowParams);
                    } else {
                        deviceListAdapter.setOnItemClickListener(new OnDeviceClickListener() {
                            @Override
                            public void onClick(String pid, boolean enabled) {

                                if (enabled) {
                                    keyValueStorage.addDeviceToDashboard(pid);
                                } else {
                                    keyValueStorage.removeDeviceFromDashboard(pid);
                                }

                                if (deviceListAdapter.getSelectedItems().size() > 0)
                                    btnWatchGraphsOnline.setVisibility(View.VISIBLE);
                                else
                                    btnWatchGraphsOnline.setVisibility(GONE);
                            }

                            @Override
                            public void onLongClick(String name) {
                                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });

        setupUserDashboardDevices();
        return rootView;
    }

    private void setupUserDashboardDevices() {
        String devices = keyValueStorage.getUserDashboardDevices();
        userDashboardDevices = devices;

        Log.d(TAG, "setupUserDashboardDevices: " + userDashboardDevices);

        deviceListAdapter.setUserDashboardDevices(userDashboardDevices);

    }

//    private List<DashboardItem> getPidDevicesFrom(List<String> supportedPids) {
//        List<DashboardItem> items = new ArrayList<>();
//        if (supportedPids.size() == 0)
//            return items;
//
//        for (String item : supportedPids) {
//            PID pid = PID.getEnumByString(item);
//            String pidCommand = pid.getCommand();
//
//            if (!pidCommand.equals(PID.PIDS_SUP_0_20.getCommand()) &&
//                    !pidCommand.equals(PID.FREEZE_DTCS.getCommand()) &&
//                    !pidCommand.equals(PID.ENGINE_RPM.getCommand()) &&
//                    !pidCommand.equals(PID.VEHICLE_SPEED.getCommand()) &&
//                    !pidCommand.equals(PID.OBD_STANDARDS_VEHICLE_CONFORMS_TO.getCommand()) &&
//                    !pidCommand.equals(PID.PIDS_SUP_21_40.getCommand()) &&
//                    !pidCommand.equals(PID.PIDS_SUP_41_60.getCommand()) &&
//                    !pidCommand.equals(PID.EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED.getCommand()) &&
//                    !pidCommand.equals(PID.PIDS_SUP_61_80.getCommand()) &&
//                    !pidCommand.equals(PID.AUXILIARY_IN_OUT_SUPPORTED.getCommand())) {
//
//                DashboardItem dashboardItem = new DashboardItem("-", pid);
//                items.add(dashboardItem);
//            }
//        }
//
//        return items;
//    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            deviceListAdapter.setOnItemClickListener(new OnDeviceClickListener() {
                @Override
                public void onLongClick(String name) {
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(String pid, boolean enabled) {

                    if (enabled) {
                        keyValueStorage.addDeviceToDashboard(pid);
                        Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices());
                    } else {
                        keyValueStorage.removeDeviceFromDashboard(pid);
                        Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices()
                        );
                    }

                    if (deviceListAdapter.getSelectedItems().size() > 0)
                        btnWatchGraphsOnline.setVisibility(View.VISIBLE);
                    else
                        btnWatchGraphsOnline.setVisibility(GONE);
                }
            });

        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            deviceListAdapter.setOnItemClickListener(new OnDeviceClickListener() {
                @Override
                public void onLongClick(String name) {
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(String pid, boolean enabled) {

                    if (enabled) {
                        keyValueStorage.addDeviceToDashboard(pid);
                        Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices());
                    } else {
                        keyValueStorage.removeDeviceFromDashboard(pid);
                        Log.d(TAG, "onCreateView: " + keyValueStorage.getUserDashboardDevices()
                        );
                    }

                    if (deviceListAdapter.getSelectedItems().size() > 0)
                        btnWatchGraphsOnline.setVisibility(View.VISIBLE);
                    else
                        btnWatchGraphsOnline.setVisibility(GONE);
                }
            });
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: PID = " + intent.getStringExtra(BROADCAST_PID_EXTRA) + ", value = " + intent.getStringExtra(BROADCAST_VALUE_EXTRA));

            deviceListAdapter.addItem(new DashboardItem(intent.getStringExtra(BROADCAST_VALUE_EXTRA),
                    intent.getStringExtra(BROADCAST_PID_EXTRA)));
            if (deviceListAdapter.getSelectedItems().size() > 0)
                btnWatchGraphsOnline.setVisibility(View.VISIBLE);
            else
                btnWatchGraphsOnline.setVisibility(GONE);
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
