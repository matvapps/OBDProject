package com.carzis.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.DashboardItem;
import com.carzis.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class DashboardItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DashboardItem> items;


    public DashboardItemsAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<DashboardItem> devices) {
        items.clear();
        items.addAll(devices);
        notifyDataSetChanged();
    }

    public void addItem(DashboardItem device) {
        items.add(device);
        notifyItemInserted(items.size());
    }

    public void removeItem(DashboardItem device) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDeviceType() == device.getDeviceType())
                removeItem(i);
        }
    }

    public void updateItem(DashboardItem device) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDeviceType() == device.getDeviceType()) {
                items.get(i).setValue(device.getValue());
                notifyDataSetChanged();
            }
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new DashboardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DashboardItemViewHolder dashboardItemViewHolder = (DashboardItemViewHolder) holder;
        DashboardItem device = items.get(position);

        dashboardItemViewHolder.value.setText(String.valueOf(device.getValue()));
        dashboardItemViewHolder.dimen.setText(
                Utility.getDeviceDimenBy(
                        holder.itemView.getContext(), device.getDeviceType()));

        dashboardItemViewHolder.icon.setImageResource(
                Utility.getDeviceIconIdBy(
                        device.getDeviceType()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private class DashboardItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView value;
        private TextView dimen;

        public DashboardItemViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            value = itemView.findViewById(R.id.value);
            dimen = itemView.findViewById(R.id.dimen);

        }
    }


}
