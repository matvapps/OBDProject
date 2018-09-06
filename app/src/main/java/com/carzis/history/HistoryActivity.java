package com.carzis.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.MyDatePickerFragment;
import com.carzis.util.custom.view.MyTimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HistoryActivity extends BaseActivity implements HistoryView, View.OnClickListener {

    public final String TAG = HistoryActivity.class.getSimpleName();

    private KeyValueStorage keyValueStorage;
    private LocalRepository localRepository;
    private HistoryPresenter historyPresenter;
    private LineChartData lineData;

    private LineChartView chart;
    private TextView timeTextView;
    private View backBtn;
    private EditText firstDate;
    private EditText secondDate;

    private static final String CAR_NAME = "car_name";
    private static final String PID_CODES = "pid_codes";
    private static final String CAR_ID = "car_id";

    private String carName;
    private ArrayList<String> pidCode;
    private String carId;

    private Calendar firstTime = null;
    private Calendar secondTime = null;

    public static void start(Context context, String carName, String carId, ArrayList<String> pidCodes) {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(CAR_NAME, carName);
        intent.putExtra(CAR_ID, carId);
//        intent.putExtra(PID_CODES, pidCode);
        intent.putStringArrayListExtra(PID_CODES, pidCodes);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        this.carName = getIntent().getStringExtra(CAR_NAME);
        this.pidCode = getIntent().getStringArrayListExtra(PID_CODES);
        this.carId = getIntent().getStringExtra(CAR_ID);

        chart = findViewById(R.id.chart);
        timeTextView = findViewById(R.id.time_text_view);
        backBtn = findViewById(R.id.back_btn);
        firstDate = findViewById(R.id.first_date);
        secondDate = findViewById(R.id.second_date);

        firstDate.setFocusable(false);
        secondDate.setFocusable(false);

        firstDate.setOnClickListener(this);
        secondDate.setOnClickListener(this);

        backBtn.setOnClickListener(view -> finish());

        keyValueStorage = new KeyValueStorage(this);
        localRepository = new LocalRepository(this);
        historyPresenter = new HistoryPresenter(keyValueStorage.getUserToken());
        historyPresenter.attachView(this);
        localRepository.attachView(this);

        chart.post(this::onTimeUpdate);

        startTimeThread();
    }

    public void generateData(List<HistoryItem> items) {
        Line line;
        List<PointValue> values;
        List<Line> lines = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();
        boolean isOneDay = true;

        ArrayList<Calendar> itemLabels = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            long millis = Long.parseLong(items.get(i).getTime());
            Log.d("HelloWorld", "TimeInMillis HistoryActivity: " + millis);
            Calendar time = Calendar.getInstance();
//            Toast.makeText(this, time.get(Calendar.SECOND) + " second before", Toast.LENGTH_SHORT).show();
            time.setTimeInMillis(millis);
//            Toast.makeText(this, time.get(Calendar.SECOND) + " second after", Toast.LENGTH_SHORT).show();

            if (i > 0 && i < items.size() - 1) {
                if (!(itemLabels.get(i - 1).get(Calendar.DAY_OF_MONTH) == time.get(Calendar.DAY_OF_MONTH)
                        && itemLabels.get(i - 1).get(Calendar.MONTH) == time.get(Calendar.MONTH))) {
                    isOneDay = false;
                }
            }

            itemLabels.add(time);
        }

        ArrayList<String> itemValues = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            itemValues.add(items.get(i).getValue());
        }


        values = new ArrayList<PointValue>();
        for (int i = 0; i < items.size(); i++) {
            values.add(new PointValue(i, Float.parseFloat(items.get(i).getValue())));
            if (isOneDay) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Calendar time = itemLabels.get(i);

                Log.d(TAG, "generateData: " + TimeZone.getDefault().getID());

                axisValues.add(new AxisValue(i).setLabel(sdf.format(time.getTime())));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
                Calendar time = itemLabels.get(i);

                axisValues.add(new AxisValue(i).setLabel(sdf.format(time.getTime())));
            }
        }

        line = new Line(values);
        line.setColor(Color.parseColor("#5699C1"));
        line.setHasPoints(false);
        line.setFilled(true);
        line.setCubic(true);
        line.setStrokeWidth(1);
        lines.add(line);

        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);


        Axis distanceAxis = new Axis(axisValues);
        distanceAxis.setHasLines(true);
        distanceAxis.setLineColor(Color.parseColor("#7965A5CA"));

        lineChartData.setAxisXBottom(distanceAxis);

        // Tempo uses minutes so I can't use auto-generated axis because auto-generation works only for decimal
        // system. So generate custom axis values for example every 15 seconds and set custom labels in format
        // minutes:seconds(00:00), you could do it in formatter but here will be faster.
//        List<AxisValue> axisValues = new ArrayList<AxisValue>();
//        for (float i = 0; i < tempoRange; i += 0.25f) {
//            // I'am translating float to minutes because I don't have data in minutes, if You store some time data
//            // you may skip translation.
//            axisValues.add(new AxisValue(i).setLabel(""));
//        }

//        Axis tempoAxis = new Axis(axisValues).setName("Значение").setHasLines(true).setMaxLabelChars(6)
//                .setTextColor(Color.WHITE);
//        tempoAxis.setLineColor(Color.parseColor("#7965A5CA"));

//        lineChartData.setAxisYLeft(tempoAxis);


        // Height axis, this axis need custom formatter that will translate values back to real height values.
        lineChartData.setAxisYLeft(new Axis()
                .setHasLines(true)
                .setLineColor(Color.parseColor("#7965A5CA")));

        chart.setViewportCalculationEnabled(false);


        int length = 8;
        if (itemLabels.size() < 8) {
            length = itemLabels.size();
        }

        Viewport v = new Viewport(0, getMax(itemValues) + 10, length, 0);
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

        chart.setLineChartData(lineChartData);

//        layout.addView(chart);


    }

    private void generateDataFrom(List<HistoryItem> items) {
        List<Line> lines = new ArrayList<Line>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();

        ArrayList<String> itemLabels = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            itemLabels.add(String.valueOf(items.get(i).getTime()));
        }

        for (int i = 0; i < items.size(); ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel(itemLabels.get(i)));
        }

        Line line = new Line(values);
        line.setColor(Color.parseColor("#5699C1"));
        line.setHasPoints(false);
        line.setFilled(true);
        line.setCubic(true);
        line.setStrokeWidth(1);

        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisYLeft(new Axis()
                .setHasLines(true)
                .setLineColor(Color.parseColor("#7965A5CA")));

//        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        lineData.setAxisXBottom(new Axis(axisValues)
                .setHasLines(true)
                .setMaxLabelChars(6)
                .setLineColor(Color.parseColor("#7965A5CA")));

        chart.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chart.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 110, 6, 0);
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

        chart.setZoomType(ZoomType.HORIZONTAL);
    }

    public float getMax(ArrayList<String> list) {
        float max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (Float.parseFloat(list.get(i)) > max) {
                max = Float.parseFloat(list.get(i));
            }
        }
        return max;
    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        Log.d("TAG", "onGetHistoryItems: " + items);
        for (int i = 0; i < items.size(); i++) {
            HistoryItem item = items.get(i);
            if (!item.getPidId().equals(pidCode)) {
                items.remove(i);
//                Log.d("TAG", "onGetHistoryItems: " + item.getPidId() + " " + item.getValue());
            }
        }
        generateData(items);
    }

    @Override
    public void onCarMetricAdded(CarMetric carMetric) {

    }

    @Override
    public void onRemoteRepoError() {

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

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_date: {
                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
                myTimePickerFragment.setTimeChangeListener(timePicker -> {
                    ((EditText) view)
                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                    firstTime =  Calendar.getInstance();
                    firstTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    firstTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                    MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
                    myDatePickerFragment.setDateChangeListener(datePicker -> {
                        String str = ((EditText) view).getText().toString();
                        ((EditText) view)
                                .setText(String.format("%s  %d/%d/%d", str, datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()));

                        firstTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        firstTime.set(Calendar.MONTH, datePicker.getMonth());
                        firstTime.set(Calendar.YEAR, datePicker.getYear());

                        onTimeUpdate();

                    });
                    myDatePickerFragment.show(getSupportFragmentManager(), "date picker");
                });

                myTimePickerFragment.show(getSupportFragmentManager(), "time picker");
                break;
            }
            case R.id.second_date: {
                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
                myTimePickerFragment.setTimeChangeListener(timePicker -> {
                    ((EditText) view)
                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                    secondTime =  Calendar.getInstance();
                    secondTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    secondTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                    MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
                    myDatePickerFragment.setDateChangeListener(datePicker -> {
                        String str = ((EditText) view).getText().toString();
                        ((EditText) view)
                                .setText(String.format("%s  %d/%d/%d", str, datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()));

                        secondTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        secondTime.set(Calendar.MONTH, datePicker.getMonth());
                        secondTime.set(Calendar.YEAR, datePicker.getYear());

                        onTimeUpdate();

                    });
                    myDatePickerFragment.show(getSupportFragmentManager(), "date picker");
                });

                myTimePickerFragment.show(getSupportFragmentManager(), "time picker");
                break;
            }
        }
    }

    private void onTimeUpdate() {
        long firstMillis;
        long secondMillis;

        if (firstTime == null)
            firstMillis = 0;
        else
            firstMillis = firstTime.getTimeInMillis() / 1000L;


        if (secondTime == null)
            secondMillis = 999999999999999999L;
        else
            secondMillis = secondTime.getTimeInMillis() / 1000L;


//        historyPresenter.getCarMetric(carName, carId, pidCode, String.valueOf(firstMillis), String.valueOf(secondMillis));

    }

}
