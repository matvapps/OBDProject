package com.carzis.util.custom.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Alexandr
 */

public class MyTimePickerFragment extends DialogFragment {


    onTimeChangeListener timeChangeListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute,
                true);
    }

    public onTimeChangeListener getTimeChangeListener() {
        return timeChangeListener;
    }

    public void setTimeChangeListener(onTimeChangeListener timeChangeListener) {
        this.timeChangeListener = timeChangeListener;
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeChangeListener.onTimeChange(view);
                }
            };

    public interface onTimeChangeListener {
        void onTimeChange(TimePicker timePicker);
    }
}
