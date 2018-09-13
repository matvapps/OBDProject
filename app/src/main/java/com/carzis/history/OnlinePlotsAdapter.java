package com.carzis.history;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.HistoryItem;
import com.carzis.model.OnlinePlot;
import com.carzis.obd.PID;
import com.carzis.util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class OnlinePlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = OnlinePlotsAdapter.class.getSimpleName();

    private List<OnlinePlot> items;

    public OnlinePlotsAdapter() {
        items = new ArrayList<>();
    }

    public void addItem(PID pid, HistoryItem historyItem) {
        for (OnlinePlot plot : items) {
            if (plot.getPid().getCommand().equals(pid.getCommand())) {
                plot.addHistoryItem(historyItem);
                notifyDataSetChanged();
                return;
            }
        }

        List<HistoryItem> bufList = new ArrayList<>();
        bufList.add(historyItem);
        items.add(new OnlinePlot(pid, bufList));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_plot, parent, false);
        return new PlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlotViewHolder plotViewHolder = (PlotViewHolder) holder;

        plotViewHolder.textView.setVisibility(View.GONE);
        plotViewHolder.firstDateEdtxt.setVisibility(View.GONE);
        plotViewHolder.secondDateEdtxt.setVisibility(View.GONE);

        String name = Utility.getDeviceNameBy(
                holder.itemView.getContext(), items.get(position).getPid());

        String dimen = Utility.getDeviceDimenBy(
                holder.itemView.getContext(), items.get(position).getPid());

        plotViewHolder.plotTitleTextView
                .setText(String.format("%s (%s)", name, dimen));


        if (items.get(position).getHistoryItems() != null)
            generateData(items.get(position).getHistoryItems(), plotViewHolder.lineChartView);
        else
            generateData(new ArrayList<>(), plotViewHolder.lineChartView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PlotViewHolder extends RecyclerView.ViewHolder {
        public TextView plotTitleTextView;
        public LineChartView lineChartView;
        public EditText firstDateEdtxt;
        public EditText secondDateEdtxt;
        public ProgressBar progressBar;
        public TextView textView;

        public PlotViewHolder(View itemView) {
            super(itemView);
            plotTitleTextView = itemView.findViewById(R.id.plot_title);
            lineChartView = itemView.findViewById(R.id.chart);
            firstDateEdtxt = itemView.findViewById(R.id.first_date);
            secondDateEdtxt = itemView.findViewById(R.id.second_date);
            progressBar = itemView.findViewById(R.id.progress_bar);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void generateData(List<HistoryItem> items, LineChartView chart) {
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
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Calendar time = itemLabels.get(i);

                Log.d(TAG, "generateData: " + TimeZone.getDefault().getID());

                axisValues.add(new AxisValue(i).setLabel(sdf.format(time.getTime())));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss", Locale.getDefault());
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
        if (itemLabels.size() > 8) {
            length = itemLabels.size();
        }

        Viewport v = new Viewport(0, getMax(itemValues) + 10, length, 0);
        Viewport maxV = new Viewport(0, getMax(itemValues) + 10, items.size(), 0);
        chart.setMaximumViewport(maxV);
        chart.setCurrentViewport(v);

        chart.setLineChartData(lineChartData);

//        layout.addView(chart);


    }

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

    public float getMax(ArrayList<String> list) {
        float max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (Float.parseFloat(list.get(i)) > max) {
                max = Float.parseFloat(list.get(i));
            }
        }
        return max;
    }

}
