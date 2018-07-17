package com.carzis.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.carzis.R;
import com.carzis.model.AppError;
import com.carzis.model.HistoryItem;
import com.carzis.repository.local.database.LocalRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HistoryActivity extends AppCompatActivity implements HistoryView {

    public final String TAG = HistoryActivity.class.getSimpleName();

    private LineChartView chart;
    private LocalRepository localRepository;
    private LineChartData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        chart = findViewById(R.id.chart);

        List<Line> values = new ArrayList<Line>();

        localRepository = new LocalRepository(this);
        localRepository.attachView(this);
        Calendar now = Calendar.getInstance();

        localRepository.getAllHistoryItemsByCar("BMW");
//        generateData(new ArrayList<HistoryItem>());

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
//            Toast.makeText(this, "millis: " + millis , Toast.LENGTH_SHORT).show();
            Calendar time = Calendar.getInstance();
//            Toast.makeText(this, time.get(Calendar.SECOND) + " second before", Toast.LENGTH_SHORT).show();
            time.setTimeInMillis(millis);
//            Toast.makeText(this, time.get(Calendar.SECOND) + " second after", Toast.LENGTH_SHORT).show();

            if (i > 0 && i < items.size() - 1) {
                if(!(itemLabels.get(i - 1).get(Calendar.DAY_OF_MONTH) == time.get(Calendar.DAY_OF_MONTH)
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
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//                Toast.makeText(this, itemLabels.get(i).get(Calendar.SECOND), Toast.LENGTH_SHORT).show();
                axisValues.add(new AxisValue(i).setLabel(sdf.format(itemLabels.get(i).getTime())));
            } else {

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

        Viewport v = new Viewport(0, getMax(itemValues) + 5, length, 0);
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

    public float getMax(ArrayList<String> list){
        float max = 0;
        for(int i=0; i<list.size(); i++){
            if(Float.parseFloat(list.get(i)) > max){
                max = Float.parseFloat(list.get(i));
            }
        }
        return max;
    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        Log.d("TAG", "onGetHistoryItems: " + carName);
        for (HistoryItem item : items) {
            Log.d("TAG", "onGetHistoryItems: " + item.getPidId() + " " + item.getValue());

        }
        generateData(items);
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }
}
