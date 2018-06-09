package com.carzis.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.carzis.R;
import com.carzis.model.HistoryItem;
import com.carzis.model.LoadingError;
import com.carzis.repository.local.database.LocalRepository;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class HistoryActivity extends AppCompatActivity implements HistoryView {

    public final String TAG = HistoryActivity.class.getSimpleName();

    private LineChartView chart;
    private LocalRepository localRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        chart = findViewById(R.id.chart);

        List<Line> values = new ArrayList<Line>();

        localRepository = new LocalRepository(this);
        localRepository.attachView(this);

        localRepository.getAllHistoryItemsByCar("мое");
        generateData();

    }

    public void generateData() {
        float tempoRange = 12; // from 0min/km to 15min/km

        float scale = tempoRange;
        float sub = (scale) / 2;

        int numValues = 12;

        Line line;
        List<PointValue> values;
        List<Line> lines = new ArrayList<Line>();

        // Height line, add it as first line to be drawn in the background.
        values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            // Some random height values, add +200 to make line a little more natural
            float rawHeight = (float) (Math.random() * 10 + 200);
            float normalizedHeight = rawHeight * scale - sub;
            values.add(new PointValue(i, normalizedHeight));
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


        Axis distanceAxis = new Axis();
        distanceAxis.setName("Время");
        distanceAxis.setFormatter(new SimpleAxisValueFormatter().setAppendedText("km".toCharArray()));
        distanceAxis.setHasLines(true);
        distanceAxis.setLineColor(Color.parseColor("#7965A5CA"));

        lineChartData.setAxisXBottom(distanceAxis);


        // Tempo uses minutes so I can't use auto-generated axis because auto-generation works only for decimal
        // system. So generate custom axis values for example every 15 seconds and set custom labels in format
        // minutes:seconds(00:00), you could do it in formatter but here will be faster.
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (float i = 0; i < tempoRange; i += 0.25f) {
            // I'am translating float to minutes because I don't have data in minutes, if You store some time data
            // you may skip translation.
            axisValues.add(new AxisValue(i).setLabel(""));
        }

//        Axis tempoAxis = new Axis(axisValues).setName("Значение").setHasLines(true).setMaxLabelChars(6)
//                .setTextColor(Color.WHITE);
//        tempoAxis.setLineColor(Color.parseColor("#7965A5CA"));

//        lineChartData.setAxisYLeft(tempoAxis);


        // *** Same as in Speed/Height chart.
        // Height axis, this axis need custom formatter that will translate values back to real height values.
        lineChartData.setAxisYLeft(new Axis()
                .setHasLines(true)
                .setLineColor(Color.parseColor("#7965A5CA")));


        chart.setLineChartData(lineChartData);

//        layout.addView(chart);


    }


    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        Log.d("TAG", "onGetHistoryItems: " + carName);
        for (HistoryItem item : items) {
            Log.d("TAG", "onGetHistoryItems: " + item.getPidId() + " " + item.getValue());

        }
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(LoadingError loadingError) {

    }
}
