package com.carzis.pidlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.carzis.CarzisApplication;
import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.history.HistoryActivity;
import com.carzis.history.HistoryPresenter;
import com.carzis.history.HistoryView;
import com.carzis.model.AppError;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;
import com.carzis.obd.PID;
import com.carzis.obd.PidItem;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PidListActvity extends BaseActivity implements HistoryView, PidItemClickListener,
        PurchasesUpdatedListener {

    private final String TAG = PidListActvity.class.getSimpleName();
    private static final String CAR_NAME = "car_name";
    private static final String CAR_ID = "car_id";


    private RecyclerView pidListView;
    private TextView timeTextView;
    private View backBtn;
    private View watchGraphsBtn;

    private LocalRepository localRepository;
    private PidListAdapter pidListAdapter;
    private HistoryPresenter historyPresenter;
    private KeyValueStorage keyValueStorage;
    private BillingClient mBillingClient;

    private String carName;
    private String carId;

    public static void start(Context context, String carName, String carId) {
        Intent intent = new Intent(context, PidListActvity.class);
        intent.putExtra(CAR_NAME, carName);
        intent.putExtra(CAR_ID, carId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carName = getIntent().getStringExtra(CAR_NAME);
        carId = getIntent().getStringExtra(CAR_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pid_list_actvity);

        timeTextView = findViewById(R.id.time_text_view);
        backBtn = findViewById(R.id.back_btn);
        watchGraphsBtn = findViewById(R.id.btn_look_plots);
        pidListView = findViewById(R.id.pidlist);

        pidListView.setLayoutManager(new LinearLayoutManager(this));

        keyValueStorage = new KeyValueStorage(this);
        localRepository = new LocalRepository(this);
        localRepository.attachView(this);

        historyPresenter = new HistoryPresenter(keyValueStorage.getUserToken());
        historyPresenter.attachView(this);

        pidListAdapter = new PidListAdapter();
        pidListAdapter.setPidItemClickListener(this);

        pidListView.setAdapter(pidListAdapter);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(200);
                runOnUiThread(() -> historyPresenter.getAllCarMetric(carId, carName));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();


        backBtn.setOnClickListener(view -> finish());
        watchGraphsBtn.setOnClickListener(view -> {
            ArrayList<String> pids = new ArrayList<>();
            for (PidItem item : pidListAdapter.getSelected()) {
                pids.add(item.getPid().getCommand());
            }

            HistoryActivity.start(PidListActvity.this, carName, carId, pids);
        });


        startTimeThread();

    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {

        List<PidItem> pidItems = new ArrayList<>();
        if (items != null) {
            for (HistoryItem item : items) {
                if (!existInList(pidItems, item.getPidId())) {
                    String pidCommand = item.getPidId();

                    if (!pidCommand.equals(PID.PIDS_SUP_0_20.getCommand()) &&
                            !pidCommand.equals(PID.FREEZE_DTCS.getCommand()) &&
                            !pidCommand.equals(PID.OBD_STANDARDS_VEHICLE_CONFORMS_TO.getCommand()) &&
                            !pidCommand.equals(PID.PIDS_SUP_21_40.getCommand()) &&
                            !pidCommand.equals(PID.PIDS_SUP_41_60.getCommand()) &&
                            !pidCommand.equals(PID.EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED.getCommand()) &&
                            !pidCommand.equals(PID.PIDS_SUP_61_80.getCommand()) &&
                            !pidCommand.equals(PID.DTCS_CLEARED_MIL_DTCS.getCommand()) &&
                            !pidCommand.equals(PID.AUXILIARY_IN_OUT_SUPPORTED.getCommand())) {

                        pidItems.add(new PidItem(PID.getEnumByString(pidCommand)));

                        Log.d(TAG, "onGetHistoryItems: " + item.getPidId());
                    }
                }
            }
        }
        pidListAdapter.setItems(pidItems);
    }

    @Override
    public void onCarMetricAdded(CarMetric carMetric) {

    }

    @Override
    public void onRemoteRepoError() {
        localRepository.getAllHistoryItemsByCar(carName);
    }

    private boolean existInList(List<PidItem> items, String pidCode) {
        for (PidItem item : items) {
            if (item.getPid().getCommand().equals(pidCode))
                return true;
        }
        return false;
    }

    private void startTimeThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String timeString = timeFormat.format(calendar.getTime());
                            timeTextView.setText(timeString);
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }


    @Override
    public void onClick(String pidId) {
        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
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
                        mBillingClient.launchBillingFlow(PidListActvity.this, flowParams);
                    } else {



//                        HistoryActivity.start(PidListActvity.this, carName, carId, pidId);
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {

        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {

        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
        }
    }
}
