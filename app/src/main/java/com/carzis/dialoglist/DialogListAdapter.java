package com.carzis.dialoglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carzis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class DialogListAdapter extends Adapter<RecyclerView.ViewHolder> {

    private List<String> items;
    private DialogListItemClickListener itemClickListener;


    public void setItems(List<String> items) {
        this.items.clear();
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public DialogListAdapter() {
        items = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextViewHolder textViewHolder = (TextViewHolder) holder;

        textViewHolder.text.setText(items.get(position));
        if (itemClickListener != null)
            textViewHolder.itemView.setOnClickListener(view -> itemClickListener.onClick(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getItem(int position) {
        return items.get(position);
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public TextViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

    public DialogListItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(DialogListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
