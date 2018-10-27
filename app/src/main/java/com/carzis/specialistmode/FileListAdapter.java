package com.carzis.specialistmode;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.model.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr
 */
public class FileListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileItem> items;

    public void setItems(List<File> files) {
        items.clear();
        for (File file:files) {
            items.add(new FileItem(file));
        }
        notifyDataSetChanged();
    }


    public FileListAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_list, parent, false);
        return new FileViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FileViewHolder fileViewHolder = (FileViewHolder) holder;
        FileItem fileItem = items.get(position);

        fileViewHolder.checkBox.setChecked(fileItem.isChecked());
        fileViewHolder.fileNameTxt.setText(getItem(position).getName());
        fileViewHolder.itemView.setOnClickListener(v -> {
            fileViewHolder.checkBox.setChecked(!fileViewHolder.checkBox.isChecked());
            items.get(position).setChecked(fileViewHolder.checkBox.isChecked());
            notifyDataSetChanged();
        });
//              view -> fileViewHolder.checkBox.setChecked(!fileViewHolder.checkBox.isChecked()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<File> getCheckedItems() {
        List<File> checkedFiles = new ArrayList<>();
        for (FileItem item :items) {
            if (item.isChecked())
                checkedFiles.add(item.getFile());
        }
        return checkedFiles;
    }

    public File getItem(int position) {
        return items.get(position).getFile();
    }

    public void selectAll(boolean select) {
        for (FileItem item :items) {
            item.setChecked(select);
        }
        notifyDataSetChanged();
    }

    private class FileViewHolder extends RecyclerView.ViewHolder {

        private TextView fileNameTxt;
        private CheckBox checkBox;

        public FileViewHolder(View itemView) {
            super(itemView);

            fileNameTxt = itemView.findViewById(R.id.file_name);
            checkBox = itemView.findViewById(R.id.file_checkbox);
            checkBox.setClickable(false);

        }
    }

}
