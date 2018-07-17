package com.carzis.pidlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.PID;
import com.carzis.obd.PidItem;
import com.carzis.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class PidListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PidItem> items;
    private PidItemClickListener pidItemClickListener;

    public void setItems(List<PidItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(PidItem item) {
        this.items.add(item);
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
        PidItem pidItem = items.get(position);

        int imageID = Utility.getDeviceIconIdBy(pidItem.getPid());
        String name = Utility.getDeviceNameBy(pidItem.getPid());

        if (pidItemClickListener != null)
            pidViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pidItemClickListener.onClick(pidItem.getPid().getCommand());
                }
            });

        pidViewHolder.pidImage.setImageResource(imageID);
        pidViewHolder.pidName.setText(name);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class PidViewHolder extends RecyclerView.ViewHolder {
        public ImageView pidImage;
        public TextView pidName;

        public PidViewHolder(View itemView) {
            super(itemView);

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
