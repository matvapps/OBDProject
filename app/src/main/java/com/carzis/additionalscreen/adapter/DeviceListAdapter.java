package com.carzis.additionalscreen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.additionalscreen.listener.OnDeviceClickListener;
import com.carzis.model.DashboardItem;
import com.carzis.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = DeviceListAdapter.class.getSimpleName();

    private List<DashboardItem> items;
    private OnDeviceClickListener onItemClickListener;
    private String userDashboardDevices;
    private int maxSelectedItems = 6;
    private int currSelectedItems = 0;

    public void setItems(List<DashboardItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(DashboardItem item) {
        this.items.add(item);
        notifyDataSetChanged();
    }


    public DeviceListAdapter() {
        items = new ArrayList<>();
        userDashboardDevices = "";
    }

    public int getMaxSelectedItems() {
        return maxSelectedItems;
    }

    public void setMaxSelectedItems(int maxSelectedItems) {
        this.maxSelectedItems = maxSelectedItems;
    }

    public String getUserDashboardDevices() {
        return userDashboardDevices;
    }

    public void setUserDashboardDevices(String userDashboardDevices) {
        this.userDashboardDevices = userDashboardDevices;
        notifyDataSetChanged();
    }

    public OnDeviceClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnDeviceClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceViewHolder deviceViewHolder = (DeviceViewHolder) holder;
        DashboardItem device = items.get(position);

        deviceViewHolder.itemView.setOnClickListener(view -> {
            if (currSelectedItems < maxSelectedItems) {
                if (!deviceViewHolder.chbx.isChecked()) {
                    onItemClickListener.onClick(device.getDeviceType(), true);
                    deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_active);
                    deviceViewHolder.chbx.setChecked(true);
                    currSelectedItems++;
                } else {
                    onItemClickListener.onClick(device.getDeviceType(), false);
                    deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_noactive);
                    deviceViewHolder.chbx.setChecked(false);
                    currSelectedItems--;
                }
            }
        });

        if (isSelectedDashboardDevice(device.getDeviceType().value)) {
            deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_active);
            deviceViewHolder.chbx.setChecked(true);
        }

        deviceViewHolder.deviceName.setText(
                Utility.getDeviceNameBy(device.getDeviceType()));

        deviceViewHolder.deviceIcon.setImageResource(
                Utility.getDeviceIconIdBy(
                        device.getDeviceType()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private boolean isSelectedDashboardDevice(int type) {
        if (userDashboardDevices.isEmpty())
            return false;

        return userDashboardDevices.contains(String.valueOf(type));

    }


    private class DeviceViewHolder extends RecyclerView.ViewHolder {

        ImageView deviceIcon;
        TextView deviceName;
        CheckBox chbx;

        public DeviceViewHolder(View itemView) {
            super(itemView);

            deviceIcon = itemView.findViewById(R.id.device_image);
            deviceName = itemView.findViewById(R.id.device_name);
            chbx = itemView.findViewById(R.id.device_chbx_image);

            chbx.setClickable(false);

        }
    }

}
