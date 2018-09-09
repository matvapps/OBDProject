package com.carzis.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.AppError;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;
import com.carzis.model.PlotItem;
import com.carzis.obd.PID;
import com.carzis.util.Utility;
import com.carzis.util.custom.view.MyDatePickerFragment;
import com.carzis.util.custom.view.MyTimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Alexandr
 */

public class PlotsAdapter extends RecyclerView.Adapter<PlotsAdapter.PlotViewHolder> implements View.OnClickListener, HistoryView {

    private final String TAG = PlotsAdapter.class.getSimpleName();

    private Context context;

    private List<String> pids;
    private List<PlotItem> plotItems;
    private String carName;
    private String carId;

    private Calendar firstTime = null;
    private Calendar secondTime = null;

    private HistoryPresenter historyPresenter;

    public PlotsAdapter(String token, String carName, String carId) {
        historyPresenter = new HistoryPresenter(token);
        historyPresenter.attachView(this);
        pids = new ArrayList<>();
        plotItems = new ArrayList<>();
        this.carName = carName;
        this.carId = carId;

        for (int i = 0; i < pids.size(); i++) {
            onTimeUpdate(i);
        }
    }

    public void setPids(List<String> pids) {
        this.pids.addAll(pids);
        for (int i = 0; i < pids.size(); i++) {
            plotItems.add(new PlotItem());
            onTimeUpdate(i);
        }
        notifyDataSetChanged();
    }

    public List<String> getPids() {
        return pids;
    }

    public String getItem(int position) {
        return pids.get(position);
    }

    @NonNull
    @Override
    public PlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_plot, parent, false);
        this.context = parent.getContext();
        return new PlotViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlotViewHolder holder, int position) {
        FragmentActivity activity = (FragmentActivity) context;

        holder.progressBar.setVisibility(View.GONE);

        holder.firstDateEdtxt.setFocusable(false);
        holder.secondDateEdtxt.setFocusable(false);


        holder.firstDateEdtxt.setOnClickListener(view -> {
            MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
            myTimePickerFragment.setTimeChangeListener(timePicker -> {
                ((EditText) view)
                        .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                firstTime = Calendar.getInstance();
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

                    holder.progressBar.setVisibility(View.VISIBLE);
                    onTimeUpdate(position);

                });
                myDatePickerFragment.show(activity.getSupportFragmentManager(), "date picker");
            });

            myTimePickerFragment.show(activity.getSupportFragmentManager(), "time picker");
        });
        holder.secondDateEdtxt.setOnClickListener(view -> {
            MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
            myTimePickerFragment.setTimeChangeListener(timePicker -> {
                ((EditText) view)
                        .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                secondTime = Calendar.getInstance();
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

                    holder.progressBar.setVisibility(View.VISIBLE);
                    onTimeUpdate(position);

                });
                myDatePickerFragment.show(activity.getSupportFragmentManager(), "date picker");
            });

            myTimePickerFragment.show(activity.getSupportFragmentManager(), "time picker");
        });

        if (plotItems.get(position).getTitle() != null) {
            String pid = plotItems.get(position).getHistoryItems().get(0).getPidId();
            PID pidItem = PID.getEnumByString(pid);

            String dimen = Utility.getDeviceDimenBy(
                    holder.itemView.getContext(), pidItem);

            String name = Utility.getDeviceNameBy(
                    holder.itemView.getContext(), pidItem);

            holder.plotTitleTextView
                    .setText(String.format("%s (%s)", name, dimen));

//            holder.plotTitleTextView.setText(plotItems.get(position).getTitle());
            generateData(plotItems.get(position).getHistoryItems(), holder.lineChartView);
        }

    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        FragmentActivity activity = (FragmentActivity) context;

        switch (view.getId()) {
            case R.id.first_date: {
                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
                myTimePickerFragment.setTimeChangeListener(timePicker -> {
                    ((EditText) view)
                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                    firstTime = Calendar.getInstance();
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

//                        onTimeUpdate();

                    });
                    myDatePickerFragment.show(activity.getSupportFragmentManager(), "date picker");
                });

                myTimePickerFragment.show(activity.getSupportFragmentManager(), "time picker");
                break;
            }
            case R.id.second_date: {
                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
                myTimePickerFragment.setTimeChangeListener(timePicker -> {
                    ((EditText) view)
                            .setText(String.format("%d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                    secondTime = Calendar.getInstance();
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

//                        onTimeUpdate();

                    });
                    myDatePickerFragment.show(activity.getSupportFragmentManager(), "date picker");
                });

                myTimePickerFragment.show(activity.getSupportFragmentManager(), "time picker");
                break;
            }
        }
    }


    private void onTimeUpdate(int position) {
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

        historyPresenter.getCarMetric(carName, carId, getItem(position), String.valueOf(firstMillis), String.valueOf(secondMillis));
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


    @Override
    public int getItemCount() {
        return pids.size();
    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {
        int pidIndex = 0;
        for (int i = 0; i < pids.size(); i++) {
            if (items.size() != 0)
                if (pids.get(i).equals(items.get(0).getPidId())) {
                    pidIndex = i;
                }
        }
        String plotTitle = Utility.getDeviceNameBy(context, PID.getEnumByString(pids.get(pidIndex)));
        plotItems.set(pidIndex, new PlotItem(plotTitle, items));

        notifyDataSetChanged();
    }

    @Override
    public void onCarMetricAdded(CarMetric carMetric) {

    }

    @Override
    public void onRemoteRepoError() {

    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }

    public class PlotViewHolder extends RecyclerView.ViewHolder {
        public TextView plotTitleTextView;
        public LineChartView lineChartView;
        public EditText firstDateEdtxt;
        public EditText secondDateEdtxt;
        public ProgressBar progressBar;

        public PlotViewHolder(View itemView) {
            super(itemView);
            plotTitleTextView = itemView.findViewById(R.id.plot_title);
            lineChartView = itemView.findViewById(R.id.chart);
            firstDateEdtxt = itemView.findViewById(R.id.first_date);
            secondDateEdtxt = itemView.findViewById(R.id.second_date);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

}
