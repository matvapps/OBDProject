package com.carzis.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.Car;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class MyCarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = MyCarsAdapter.class.getSimpleName();

    private Context context;
    private KeyValueStorage keyValueStorage;

    private List<Car> items;
    private List<Car> curItems;

    private String selectedItemName = "";
//    private OnDeviceClickListener onItemClickListener;

    public void setItems(List<Car> items) {
        this.items.clear();
        this.curItems.clear();
        this.items.addAll(items);
        this.curItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Car item) {
        this.items.add(item);
        this.curItems.add(item);
//        search("");
        notifyDataSetChanged();
    }


    public MyCarsAdapter(Context context) {
        items = new ArrayList<>();
        curItems = new ArrayList<>();

        keyValueStorage = new KeyValueStorage(context);
        setSelectedItemName(keyValueStorage.getCurrentCarName());
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CarViewHolder carViewHolder = (CarViewHolder) holder;

        if (selectedItemName.equals(items.get(position).getName()))
            carViewHolder.itemView.setSelected(true);
        else
            carViewHolder.itemView.setSelected(false);

        carViewHolder.itemView.setOnClickListener(view -> {
            if (!carViewHolder.itemView.isSelected()) {
                selectedItemName = items.get(position).getName();
                carViewHolder.itemView.setSelected(true);
                keyValueStorage.setCurrentCarName(selectedItemName);
                notifyDataSetChanged();
            } else {

                carViewHolder.itemView.setSelected(false);
            }
        });

        carViewHolder.carName.setText(curItems.get(position).getName());

    }

    public String getSelectedItemName() {
        return selectedItemName;
    }

    public void setSelectedItemName(String selectedItemName) {
        this.selectedItemName = selectedItemName;
    }

    public void search(String name) {
        curItems.clear();

        for (Car item : items) {
            if (item.getName().toLowerCase()
                    .contains(name.toLowerCase()))
                curItems.add(item);
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return curItems.size();
    }


    private class CarViewHolder extends RecyclerView.ViewHolder {

        ImageView carIcon;
        TextView carName;

        public CarViewHolder(View itemView) {
            super(itemView);

            carIcon = itemView.findViewById(R.id.car_image);
            carName = itemView.findViewById(R.id.car_name);

        }
    }

}
