package com.carzis.pidlist;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.PidListItem;
import com.carzis.obd.PidItem;
import com.carzis.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class PidListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PidListItem> items;
    private PidItemClickListener pidItemClickListener;

    public void setItems(List<PidItem> items) {
        this.items.clear();
        for (PidItem pidItem :items) {
            this.items.add(new PidListItem(pidItem, false));
        }
        notifyDataSetChanged();
    }

    public void addItem(PidItem item) {
        this.items.add(new PidListItem(item, false));
        notifyDataSetChanged();
    }

    public PidListAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pid_list, parent, false);
        return new PidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PidViewHolder pidViewHolder = (PidViewHolder) holder;
        PidListItem pidItem = items.get(position);

        int imageID = Utility.getDeviceIconIdBy(pidItem.getPidItem().getPid());
        String name = Utility.getDeviceNameBy(holder.itemView.getContext(), pidItem.getPidItem().getPid());

        pidViewHolder.container.setBackgroundResource(
                pidItem.isChecked()
                        ? R.color.appBlue
                        : R.color.transparent);

        if (pidItemClickListener != null) {
            pidViewHolder.itemView.setOnClickListener(view -> {
                pidItemClickListener.onClick(pidItem.getPidItem().getPid().getCommand());
                pidItem.setChecked(!pidItem.isChecked());
                pidViewHolder.container.setBackgroundResource(
                        pidItem.isChecked()
                                ? R.color.appBlue
                                : R.color.transparent);
            });
        }

        pidViewHolder.pidImage.setImageResource(imageID);
        pidViewHolder.pidName.setText(name);

    }

    public List<PidItem> getSelected() {
        List<PidItem> selectedPidItems = new ArrayList<>();
        for (PidListItem pidItem :items) {
            if (pidItem.isChecked())
                selectedPidItems.add(pidItem.getPidItem());
        }
        return selectedPidItems;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class PidViewHolder extends RecyclerView.ViewHolder {
        public ImageView pidImage;
        public TextView pidName;
        public View container;

        public PidViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            pidImage = itemView.findViewById(R.id.pid_image);
            pidName = itemView.findViewById(R.id.pid_name);

        }
    }

    public PidItemClickListener getPidItemClickListener() {
        return pidItemClickListener;
    }

    public void setPidItemClickListener(PidItemClickListener pidItemClickListener) {
        this.pidItemClickListener = pidItemClickListener;
    }
}
