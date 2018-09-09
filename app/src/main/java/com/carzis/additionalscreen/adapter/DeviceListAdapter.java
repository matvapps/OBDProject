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
import com.carzis.obd.PID;
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
        // if item in list of selected devices then select it
        item.setChecked(isSelectedDashboardDevice(item.getPid().getCommand()));

        for (int i = 0; i < items.size(); i++) {
            DashboardItem currentItem = items.get(i);
            // if item already added then update value of it
            if (currentItem.getPid().getCommand().equals(item.getPid().getCommand())) {
                updateItem(i, item);
                return;
            }
        }
        if (!item.getPid().getCommand().equals(PID.PIDS_SUP_0_20.getCommand()) &&
                !item.getPid().getCommand().equals(PID.FREEZE_DTCS.getCommand()) &&
                !item.getPid().getCommand().equals(PID.ENGINE_RPM.getCommand()) &&
                !item.getPid().getCommand().equals(PID.VEHICLE_SPEED.getCommand()) &&
                !item.getPid().getCommand().equals(PID.OBD_STANDARDS_VEHICLE_CONFORMS_TO.getCommand()) &&
                !item.getPid().getCommand().equals(PID.PIDS_SUP_21_40.getCommand()) &&
                !item.getPid().getCommand().equals(PID.PIDS_SUP_41_60.getCommand()) &&
                !item.getPid().getCommand().equals(PID.EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED.getCommand()) &&
                !item.getPid().getCommand().equals(PID.DTCS_CLEARED_MIL_DTCS.getCommand()) &&
                !item.getPid().getCommand().equals(PID.PIDS_SUP_61_80.getCommand()) &&
                !item.getPid().getCommand().equals(PID.AUXILIARY_IN_OUT_SUPPORTED.getCommand()))
            this.items.add(item);
        notifyDataSetChanged();
    }

    private void updateItem(int position, DashboardItem item) {
        item.setChecked(items.get(position).isChecked());

        this.items.set(position, item);
        notifyItemChanged(position, item);
    }


    public DeviceListAdapter() {
        items = new ArrayList<>();
        userDashboardDevices = "";
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

        if (device.isChecked()) {
            deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_active);
            deviceViewHolder.chbx.setChecked(true);
        } else {
            deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_noactive);
            deviceViewHolder.chbx.setChecked(false);
        }

        deviceViewHolder.itemView.setOnClickListener(view -> {
            if (currSelectedItems < maxSelectedItems) {
                onItemClickListener.onClick(device.getPid(), !device.isChecked());
                device.setChecked(!device.isChecked());
                notifyItemChanged(position);
            }
        });

        deviceViewHolder.itemView.setOnLongClickListener(view -> {
            onItemClickListener.onLongClick(Utility.getDeviceNameBy(deviceViewHolder.itemView.getContext(), device.getPid()));
            return true;
        });

//        deviceViewHolder.itemView.setOnClickListener(view -> {
//            if (currSelectedItems < maxSelectedItems) {
//                if (!deviceViewHolder.chbx.isChecked()) {
//                    onItemClickListener.onClick(device.getPid(), true);
//                    deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_active);
//                    deviceViewHolder.chbx.setChecked(true);
//                    currSelectedItems++;
//                } else {
//                    onItemClickListener.onClick(device.getPid(), false);
//                    deviceViewHolder.itemView.setBackgroundResource(R.drawable.item_background_noactive);
//                    deviceViewHolder.chbx.setChecked(false);
//                    currSelectedItems--;
//                }
//            }
//        });

        deviceViewHolder.deviceValue.setText(
                String.format("%s%s", device.getValue(),
                        Utility.getDeviceDimenBy(deviceViewHolder.itemView.getContext(), device.getPid())));

        deviceViewHolder.deviceName.setText(
                Utility.getDeviceNameBy(deviceViewHolder.itemView.getContext(), device.getPid()));

        deviceViewHolder.deviceIcon.setImageResource(
                Utility.getDeviceIconIdBy(
                        device.getPid()));
    }

    public List<DashboardItem> getSelectedItems() {
        List<DashboardItem> result = new ArrayList<>();
        for (DashboardItem item : items) {
            if (item.isChecked())
                result.add(item);
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private boolean isSelectedDashboardDevice(String command) {
        if (userDashboardDevices.isEmpty())
            return false;

        return userDashboardDevices.contains(command);
    }

    private class DeviceViewHolder extends RecyclerView.ViewHolder {

        ImageView deviceIcon;
        TextView deviceName;
        TextView deviceValue;
        CheckBox chbx;

        public DeviceViewHolder(View itemView) {
            super(itemView);

            deviceIcon = itemView.findViewById(R.id.device_image);
            deviceName = itemView.findViewById(R.id.device_name);
            chbx = itemView.findViewById(R.id.device_chbx_image);
            deviceValue = itemView.findViewById(R.id.device_value);

            chbx.setClickable(false);

        }
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
    }

    public OnDeviceClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnDeviceClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
