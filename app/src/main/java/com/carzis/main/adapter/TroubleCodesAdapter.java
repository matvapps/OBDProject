package com.carzis.main.adapter;

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
import com.carzis.main.listener.OnTroubleItemClickListener;
import com.carzis.model.Trouble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class TroubleCodesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Trouble> troubleItems;
    private List<Trouble> defaultItems;

    public int selectedItemPos = 0;

    private String currentType;

    private OnTroubleItemClickListener onItemClickListener;


    public TroubleCodesAdapter() {
        troubleItems = new ArrayList<>();
        defaultItems = new ArrayList<>();
    }

    public void setItems(List<Trouble> troubleItems) {
        this.defaultItems.clear();
        this.defaultItems.addAll(troubleItems);
        notifyDataSetChanged();
        setTroubleType(currentType);
    }

    public void addItem(Trouble troubleItem) {
        this.defaultItems.add(troubleItem);
//        notifyItemInserted(defaultItems.size());
        notifyDataSetChanged();
        setTroubleType(currentType);
    }

    public void updateItem(Trouble troubleItem) {
        if (!contains(troubleItem.getCode()))
            return;

        if (!(getPositionOf(troubleItem) == -1))
            this.defaultItems.set(getPositionOf(troubleItem), troubleItem);
        else
            addItem(troubleItem);
    }

    public int getPositionOf(Trouble troubleItem) {
        for (int i = 0; i < getItemCount(); i++) {
            if (defaultItems.get(i).getCode().equals(troubleItem.getCode()))
                return i;
        }
        return -1;
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
        Trouble item = getItem(position);

//        if (selectedItemPos != -1)
//            if (selectedItemPos != position) {
//                troubleCodeViewHolder.text.setTextColor(
//                        ContextCompat.getColor(troubleCodeViewHolder.itemView.getContext(), R.color.darkerGray));
//                troubleCodeViewHolder.image.setAlpha(0.4f);
//            }

        if (selectedItemPos == position) {
            troubleCodeViewHolder.text.setTextColor(Color.WHITE);
            troubleCodeViewHolder.image.setAlpha(1.0f);
        } else {
            troubleCodeViewHolder.text.setTextColor(
                    ContextCompat.getColor(troubleCodeViewHolder.itemView.getContext(), R.color.darkerGray));
            troubleCodeViewHolder.image.setAlpha(0.4f);
        }

        String shortDescription = item.getRu_desc();

        if (shortDescription != null) {
            if (shortDescription.isEmpty())
                shortDescription = item.getEn_desc();

            if (shortDescription.indexOf("P00") == 0)
                shortDescription = shortDescription.substring(5);
        } else {
            shortDescription = "";
        }

        troubleCodeViewHolder.text.setText(String.format("%s %s", item.getCode(), shortDescription));

        if (onItemClickListener != null)
            troubleCodeViewHolder.itemView.setOnClickListener(view -> {
                selectedItemPos = position;
                onItemClickListener.onClick(position);
                troubleCodeViewHolder.text.setTextColor(Color.WHITE);
                troubleCodeViewHolder.image.setAlpha(1.0f);
                notifyDataSetChanged();
            });

    }

    public OnTroubleItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnTroubleItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Trouble getItem(int position) {
        return troubleItems.get(position);
    }

    public boolean contains(String troubleCode) {
        for (Trouble item : troubleItems) {
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
        if (!troubleType.equals(currentType))
            selectedItemPos = 0;
        currentType = troubleType;
        troubleItems.clear();
        for (Trouble item : defaultItems)
            if (item.getCode().indexOf(troubleType) == 0)
                if (!contains(item.getCode()))
                    troubleItems.add(item);
        notifyDataSetChanged();
    }

    private class TroubleCodeViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView image;

        public TroubleCodeViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.imageView);

        }
    }


}
