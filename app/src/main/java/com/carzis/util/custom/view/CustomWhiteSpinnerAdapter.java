package com.carzis.util.custom.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carzis.R;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class CustomWhiteSpinnerAdapter extends ArrayAdapter<String>  {
    private final LayoutInflater mInflater;
    private List<String> items;
    private boolean enabled = true;

    public CustomWhiteSpinnerAdapter (Context context, List<String> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

//    @Override
//    public boolean isEnabled(int position) {
//        return super.isEnabled(position);
//    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, R.layout.item_spinner_white_dropdown, parent);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, R.layout.item_spinner_white, parent);
    }

    private View createItemView(int position, int viewId, ViewGroup parent) {
        View view = mInflater.inflate(viewId, parent, false);
//        view.setPadding(0, 0, 0, 0);

        TextView textView = view.findViewById(R.id.spinner_text);
        String text = items.get(position);

        textView.setEnabled(enabled);
        textView.setText(text);

        return view;
    }
}
