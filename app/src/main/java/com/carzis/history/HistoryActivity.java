package com.carzis.history;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.model.HistoryItem;
import com.carzis.obd.PID;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.carzis.main.MainActivity.BROADCAST_PID_EXTRA;
import static com.carzis.main.MainActivity.BROADCAST_VALUE_EXTRA;
import static com.carzis.main.MainActivity.RECEIVED_DATA_FROM_CAR;

public class HistoryActivity extends BaseActivity {

    public final String TAG = HistoryActivity.class.getSimpleName();

    private static final String CAR_NAME = "car_name";
    private static final String PID_CODES = "pid_codes";
    private static final String CAR_ID = "car_id";
    private static final String ONLINE = "online";


    private View backBtn;
    private TextView timeTextView;
    private RecyclerView plotsRecycler;
    private ProgressBar progressBar;

    private KeyValueStorage keyValueStorage;
    private PlotsAdapter plotsAdapter;
    private OnlinePlotsAdapter onlinePlotsAdapter;

    private String carName;
    private ArrayList<String> pidCodes;
    private String carId;
    private boolean isOnline;


    public static void start(Context context, String carName, String carId, ArrayList<String> pidCodes) {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(CAR_NAME, carName);
        intent.putExtra(CAR_ID, carId);
        intent.putStringArrayListExtra(PID_CODES, pidCodes);
        context.startActivity(intent);
    }

    public static void start(Context context, boolean online, ArrayList<String> pidCodes) {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(ONLINE, online);
        intent.putStringArrayListExtra(PID_CODES, pidCodes);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        timeTextView = findViewById(R.id.time_text_view);
        plotsRecycler = findViewById(R.id.plots);
        progressBar = findViewById(R.id.progress_bar);
        //        backBtn = findViewById(R.id.back_btn);

        carName = getIntent().getStringExtra(CAR_NAME);
        pidCodes = getIntent().getStringArrayListExtra(PID_CODES);
        carId = getIntent().getStringExtra(CAR_ID);
        isOnline = getIntent().getBooleanExtra(ONLINE, false);

        plotsRecycler.setLayoutManager(new LinearLayoutManager(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(RECEIVED_DATA_FROM_CAR));

        if (isOnline) {
            progressBar.setVisibility(View.VISIBLE);
            onlinePlotsAdapter = new OnlinePlotsAdapter();
            plotsRecycler.setAdapter(onlinePlotsAdapter);
        } else {
            progressBar.setVisibility(View.GONE);
            keyValueStorage = new KeyValueStorage(this);
            plotsAdapter = new PlotsAdapter(keyValueStorage.getUserToken(), carName, carId);
            plotsAdapter.setPids(pidCodes);
            plotsRecycler.setAdapter(plotsAdapter);
        }

        //        chart = findViewById(R.id.chart);

        //        firstDate = findViewById(R.id.first_date);
        //        secondDate = findViewById(R.id.second_date);

        //        firstDate.setFocusable(false);
        //        secondDate.setFocusable(false);

        //        firstDate.setOnClickListener(this);
        //        secondDate.setOnClickListener(this);


        //        localRepository = new LocalRepository(this);
        //        historyPresenter = new HistoryPresenter(keyValueStorage.getUserToken());
        //        historyPresenter.attachView(this);
        //        localRepository.attachView(this);

        //        chart.post(this::onTimeUpdate);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pid = intent.getStringExtra(BROADCAST_PID_EXTRA);
            String value = intent.getStringExtra(BROADCAST_VALUE_EXTRA);

            Log.d(TAG, "onReceive: pid = " + pid + " value = " + value);

            for (String item :
                    pidCodes) {
                Log.d(TAG, "onReceive: " + item);
            }

            if (hasSamePid(pid)) {
                PID pidItem = PID.getEnumByString(pid);

                double val;
                if (value.equals("-")) {
                    val = 0;
                    value = "0";
                } else
                    val = Double.parseDouble(value);

                Calendar now = Calendar.getInstance();
                long millis = now.getTimeInMillis();

                Log.d(TAG, "onReceive and add to plot: pid = " + pid + " value = " + value);
                onlinePlotsAdapter.addItem(pidItem, new HistoryItem("", pid, value, String.valueOf(millis)));
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    private boolean hasSamePid(String pid) {
        for (String item : pidCodes) {
            if (item.equals(pid))
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    //    public void generateData(List<HistoryItem> items) {
    //        Line line;
    //        List<PointValue> values;
    //        List<Line> lines = new ArrayList<>();
    //        List<AxisValue> axisValues = new ArrayList<>();
    //        boolean isOneDay = true;
    //
    //        ArrayList<Calendar> itemLabels = new ArrayList<>();
    //        for (int i = 0; i < items.size(); i++) {
    //            long millis = Long.parseLong(items.get(i).getTime());
    //            Log.d("HelloWorld", "TimeInMillis HistoryActivity: " + millis);
    //            Calendar time = Calendar.getInstance();
    ////            Toast.makeText(this, time.get(Calendar.SECOND) + " second before", Toast.LENGTH_SHORT).show();
    //            time.setTimeInMillis(millis);
    ////            Toast.makeText(this, time.get(Calendar.SECOND) + " second after", Toast.LENGTH_SHORT).show();
    //
    //            if (i > 0 && i < items.size() - 1) {
    //                if (!(itemLabels.get(i - 1).get(Calendar.DAY_OF_MONTH) == time.get(Calendar.DAY_OF_MONTH)
    //                        && itemLabels.get(i - 1).get(Calendar.MONTH) == time.get(Calendar.MONTH))) {
    //                    isOneDay = false;
    //                }
    //            }
    //
    //            itemLabels.add(time);
    //        }
    //
    //        ArrayList<String> itemValues = new ArrayList<>();
    //        for (int i = 0; i < items.size(); i++) {
    //            itemValues.add(items.get(i).getValue());
    //        }
    //
    //
    //        values = new ArrayList<PointValue>();
    //        for (int i = 0; i < items.size(); i++) {
    //            values.add(new PointValue(i, Float.parseFloat(items.get(i).getValue())));
    //            if (isOneDay) {
    //                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    //                Calendar time = itemLabels.get(i);
    //
    //                Log.d(TAG, "generateData: " + TimeZone.getDefault().getID());
    //
    //                axisValues.add(new AxisValue(i).setLabel(sdf.format(time.getTime())));
    //            } else {
    //                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
    //                Calendar time = itemLabels.get(i);
    //
    //                axisValues.add(new AxisValue(i).setLabel(sdf.format(time.getTime())));
    //            }
    //        }
    //
    //        line = new Line(values);
    //        line.setColor(Color.parseColor("#5699C1"));
    //        line.setHasPoints(false);
    //        line.setFilled(true);
    //        line.setCubic(true);
    //        line.setStrokeWidth(1);
    //        lines.add(line);
    //
    //        LineChartData lineChartData = new LineChartData();
    //        lineChartData.setLines(lines);
    //
    //
    //        Axis distanceAxis = new Axis(axisValues);
    //        distanceAxis.setHasLines(true);
    //        distanceAxis.setLineColor(Color.parseColor("#7965A5CA"));
    //
    //        lineChartData.setAxisXBottom(distanceAxis);
    //
    //        // Tempo uses minutes so I can't use auto-generated axis because auto-generation works only for decimal
    //        // system. So generate custom axis values for example every 15 seconds and set custom labels in format
    //        // minutes:seconds(00:00), you could do it in formatter but here will be faster.
    ////        List<AxisValue> axisValues = new ArrayList<AxisValue>();
    ////        for (float i = 0; i < tempoRange; i += 0.25f) {
    ////            // I'am translating float to minutes because I don't have data in minutes, if You store some time data
    ////            // you may skip translation.
    ////            axisValues.add(new AxisValue(i).setLabel(""));
    ////        }
    //
    ////        Axis tempoAxis = new Axis(axisValues).setName("Значение").setHasLines(true).setMaxLabelChars(6)
    ////                .setTextColor(Color.WHITE);
    ////        tempoAxis.setLineColor(Color.parseColor("#7965A5CA"));
    //
    ////        lineChartData.setAxisYLeft(tempoAxis);
    //
    //
    //        // Height axis, this axis need custom formatter that will translate values back to real height values.
    //        lineChartData.setAxisYLeft(new Axis()
    //                .setHasLines(true)
    //                .setLineColor(Color.parseColor("#7965A5CA")));
    //
    //        chart.setViewportCalculationEnabled(false);
    //
    //
    //        int length = 8;
    //        if (itemLabels.size() < 8) {
    //            length = itemLabels.size();
    //        }
    //
    //        Viewport v = new Viewport(0, getMax(itemValues) + 10, length, 0);
    //        chart.setMaximumViewport(v);
    //        chart.setCurrentViewport(v);
    //
    //        chart.setLineChartData(lineChartData);
    //
    ////        layout.addView(chart);
    //
    //
    //    }
    //
    //    private void generateDataFrom(List<HistoryItem> items) {
    //        List<Line> lines = new ArrayList<Line>();
    //        List<AxisValue> axisValues = new ArrayList<AxisValue>();
    //        List<PointValue> values = new ArrayList<PointValue>();
    //
    //        ArrayList<String> itemLabels = new ArrayList<>();
    //        for (int i = 0; i < items.size(); i++) {
    //            itemLabels.add(String.valueOf(items.get(i).getTime()));
    //        }
    //
    //        for (int i = 0; i < items.size(); ++i) {
    //            values.add(new PointValue(i, 0));
    //            axisValues.add(new AxisValue(i).setLabel(itemLabels.get(i)));
    //        }
    //
    //        Line line = new Line(values);
    //        line.setColor(Color.parseColor("#5699C1"));
    //        line.setHasPoints(false);
    //        line.setFilled(true);
    //        line.setCubic(true);
    //        line.setStrokeWidth(1);
    //
    //        lines.add(line);
    //
    //        lineData = new LineChartData(lines);
    //        lineData.setAxisYLeft(new Axis()
    //                .setHasLines(true)
    //                .setLineColor(Color.parseColor("#7965A5CA")));
    //
    ////        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
    //
    //        lineData.setAxisXBottom(new Axis(axisValues)
    //                .setHasLines(true)
    //                .setMaxLabelChars(6)
    //                .setLineColor(Color.parseColor("#7965A5CA")));
    //
    //        chart.setLineChartData(lineData);
    //
    //        // For build-up animation you have to disable viewport recalculation.
    //        chart.setViewportCalculationEnabled(false);
    //
    //        // And set initial max viewport and current viewport- remember to set viewports after data.
    //        Viewport v = new Viewport(0, 110, 6, 0);
    //        chart.setMaximumViewport(v);
    //        chart.setCurrentViewport(v);
    //
    //        chart.setZoomType(ZoomType.HORIZONTAL);
    //    }
    //
    //    public float getMax(ArrayList<String> list) {
    //        float max = 0;
    //        for (int i = 0; i < list.size(); i++) {
    //            if (Float.parseFloat(list.get(i)) > max) {
    //                max = Float.parseFloat(list.get(i));
    //            }
    //        }
    //        return max;
    //    }

    //    @Override
    //    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
    //        Log.d("TAG", "onGetHistoryItems: " + items);
    //        for (int i = 0; i < items.size(); i++) {
    //            HistoryItem item = items.get(i);
    //            if (!item.getPidId().equals(pidCodes)) {
    //                items.remove(i);
    ////                Log.d("TAG", "onGetHistoryItems: " + item.getPidId() + " " + item.getValue());
    //            }
    //        }
    //        generateData(items);
    //    }

    //    @Override
    //    public void onCarMetricAdded(CarMetric carMetric) {
    //
    //    }
    //
    //    @Override
    //    public void onRemoteRepoError() {
    //
    //    }

    //    private void startTimeThread() {
    //        Thread thread = new Thread() {
    //            @Override
    //            public void run() {
    //                try {
    //                    while (!isInterrupted()) {
    //                        Thread.sleep(1000);
    //                        runOnUiThread(() -> {
    //                            Calendar calendar = Calendar.getInstance();
    //                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    //                            String timeString = timeFormat.format(calendar.getTime());
    //                            timeTextView.setText(timeString);
    //                        });
    //                    }
    //                } catch (InterruptedException ex) {
    //                    ex.printStackTrace();
    //                }
    //            }
    //        };
    //        thread.start();
    //    }

    //    @SuppressLint("DefaultLocale")
    //    @Override
    //    public void onClick(View view) {
    //        switch (view.getId()) {
    //            case R.id.first_date: {
    //                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
    //                myTimePickerFragment.setTimeChangeListener(timePicker -> {
    //                    ((EditText) view)
    //                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
    //
    //                    firstTime =  Calendar.getInstance();
    //                    firstTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    //                    firstTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
    //
    //                    MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
    //                    myDatePickerFragment.setDateChangeListener(datePicker -> {
    //                        String str = ((EditText) view).getText().toString();
    //                        ((EditText) view)
    //                                .setText(String.format("%s  %d/%d/%d", str, datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()));
    //
    //                        firstTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    //                        firstTime.set(Calendar.MONTH, datePicker.getMonth());
    //                        firstTime.set(Calendar.YEAR, datePicker.getYear());
    //
    //                        onTimeUpdate();
    //
    //                    });
    //                    myDatePickerFragment.show(getSupportFragmentManager(), "date picker");
    //                });
    //
    //                myTimePickerFragment.show(getSupportFragmentManager(), "time picker");
    //                break;
    //            }
    //            case R.id.second_date: {
    //                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
    //                myTimePickerFragment.setTimeChangeListener(timePicker -> {
    //                    ((EditText) view)
    //                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
    //
    //                    secondTime =  Calendar.getInstance();
    //                    secondTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    //                    secondTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
    //
    //                    MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
    //                    myDatePickerFragment.setDateChangeListener(datePicker -> {
    //                        String str = ((EditText) view).getText().toString();
    //                        ((EditText) view)
    //                                .setText(String.format("%s  %d/%d/%d", str, datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()));
    //
    //                        secondTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    //                        secondTime.set(Calendar.MONTH, datePicker.getMonth());
    //                        secondTime.set(Calendar.YEAR, datePicker.getYear());
    //
    //                        onTimeUpdate();
    //
    //                    });
    //                    myDatePickerFragment.show(getSupportFragmentManager(), "date picker");
    //                });
    //
    //                myTimePickerFragment.show(getSupportFragmentManager(), "time picker");
    //                break;
    //            }
    //        }
    //    }

    //    private void onTimeUpdate() {
    //        long firstMillis;
    //        long secondMillis;
    //
    //        if (firstTime == null)
    //            firstMillis = 0;
    //        else
    //            firstMillis = firstTime.getTimeInMillis() / 1000L;
    //
    //
    //        if (secondTime == null)
    //            secondMillis = 999999999999999999L;
    //        else
    //            secondMillis = secondTime.getTimeInMillis() / 1000L;
    //
    //
    ////        historyPresenter.getCarMetric(carName, carId, pidCodes, String.valueOf(firstMillis), String.valueOf(secondMillis));
    //
    //    }

}
