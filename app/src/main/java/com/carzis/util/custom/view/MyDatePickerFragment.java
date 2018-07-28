package com.carzis.util.custom.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Alexandr
 */

public class MyDatePickerFragment extends DialogFragment {


    onDateChangeListener dateChangeListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), timeSetListener, year, month, day);
    }

    public onDateChangeListener getDateChangeListener() {
        return dateChangeListener;
    }

    public void setDateChangeListener(onDateChangeListener dateChangeListener) {
        this.dateChangeListener = dateChangeListener;
    }


    private DatePickerDialog.OnDateSetListener timeSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dateChangeListener.onDateChange(datePicker);
                }
            };

    public interface onDateChangeListener {
        void onDateChange(DatePicker datePicker);
    }
}
