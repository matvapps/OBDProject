package com.carzis.pidlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.carzis.R;
import com.carzis.history.HistoryActivity;
import com.carzis.history.HistoryView;
import com.carzis.model.AppError;
import com.carzis.model.HistoryItem;
import com.carzis.model.PID;
import com.carzis.obd.PidItem;
import com.carzis.repository.local.database.LocalRepository;

import java.util.ArrayList;
import java.util.List;

public class PidListActvity extends AppCompatActivity implements HistoryView, PidItemClickListener{

    private final String TAG = PidListActvity.class.getSimpleName();
    private static final String CAR_NAME = "car_name";

    private RecyclerView pidListView;

    private LocalRepository localRepository;
    private PidListAdapter pidListAdapter;

    private String carName;

    public static void start(Context context, String carName) {
        Intent intent = new Intent(context, PidListActvity.class);
        intent.putExtra(CAR_NAME, carName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carName = getIntent().getStringExtra(CAR_NAME);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pid_list_actvity);

        pidListView = findViewById(R.id.pidlist);
        pidListView.setLayoutManager(new LinearLayoutManager(this));

        localRepository = new LocalRepository(this);
        localRepository.attachView(this);

        localRepository.getAllHistoryItemsByCar(carName);

        pidListAdapter = new PidListAdapter();
        pidListAdapter.setPidItemClickListener(this);

        pidListView.setAdapter(pidListAdapter);

    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        List<PidItem> pidItems = new ArrayList<>();
        for (HistoryItem item :items) {
            if (!existInList(pidItems, item.getPidId())) {
                pidItems.add(new PidItem(PID.getEnumByString(item.getPidId())));
            }
        }

        pidListAdapter.setItems(pidItems);
    }

    private boolean existInList(List<PidItem> items, String pidCode) {
        for (PidItem item: items) {
            if (item.getPid().getCommand().equals(pidCode))
                return true;
        }
        return false;
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }

    @Override
    public void onClick(String pidId) {
        HistoryActivity.start(this, carName, pidId);
    }
}
