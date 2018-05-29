package com.carzis.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.TroubleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class TroubleCodesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TroubleItem> troubleItems;
    private List<TroubleItem> defaultItems;

    private String currentType;

    private View.OnClickListener onClickListener;


    public TroubleCodesAdapter() {
        troubleItems = new ArrayList<>();
        defaultItems = new ArrayList<>();
    }

    public void setItems(List<TroubleItem> troubleItems) {
        this.defaultItems.clear();
        this.defaultItems.addAll(troubleItems);
        notifyDataSetChanged();
        setTroubleType(currentType);
    }

    public void addItem(TroubleItem troubleItem) {
        this.defaultItems.add(troubleItem);
//        notifyItemInserted(defaultItems.size());
        notifyDataSetChanged();
        setTroubleType(currentType);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trouble, parent, false);
        return new TroubleCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TroubleCodeViewHolder troubleCodeViewHolder = (TroubleCodeViewHolder) holder;

        TroubleItem item = getItem(position);

        troubleCodeViewHolder.text.setText(item.getCode() + " " + item.getDesc());

        if (onClickListener != null)
            troubleCodeViewHolder.itemView.setOnClickListener(onClickListener);

    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TroubleItem getItem(int position) {
        return troubleItems.get(position);
    }

    public boolean contains(String troubleCode) {
        for(TroubleItem item: troubleItems) {
            if (item.getCode().equals(troubleCode))
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return troubleItems.size();
    }

    public void setTroubleType(String troubleType) {
        currentType = troubleType;
        troubleItems.clear();
        for(TroubleItem item: defaultItems)
            if (item.getCode().indexOf(troubleType) == 0)
                if (!contains(item.getCode()))
                    troubleItems.add(item);
        notifyDataSetChanged();
    }

    private class TroubleCodeViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public TroubleCodeViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);

        }
    }


}
