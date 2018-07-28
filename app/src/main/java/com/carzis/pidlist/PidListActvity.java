package com.carzis.pidlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

public class PidListActvity extends BaseActivity implements HistoryView, PidItemClickListener{

    private final String TAG = PidListActvity.class.getSimpleName();
    private static final String CAR_NAME = "car_name";
    private static final String CAR_ID = "car_id";


    private RecyclerView pidListView;
    private TextView timeTextView;
    private View backBtn;

    private LocalRepository localRepository;
    private PidListAdapter pidListAdapter;
    private HistoryPresenter historyPresenter;
    private KeyValueStorage keyValueStorage;

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

        backBtn.setOnClickListener(view -> finish());

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

        historyPresenter.getAllCarMetric(carId, carName);
        startTimeThread();

    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        List<PidItem> pidItems = new ArrayList<>();
        if(items != null) {
            for (HistoryItem item : items) {
                if (!existInList(pidItems, item.getPidId())) {
                    pidItems.add(new PidItem(PID.getEnumByString(item.getPidId())));
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
        for (PidItem item: items) {
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
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }

    @Override
    public void onClick(String pidId) {
        HistoryActivity.start(this, carName, carId, pidId);
    }
}
