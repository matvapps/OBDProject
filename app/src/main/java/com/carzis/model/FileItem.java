package com.carzis.model;

import java.io.File;

/**
 * Created by Alexandr
 */
public class FileItem {

    private File file;
    private boolean isChecked;

    public FileItem(File file, boolean isChecked) {
        this.file = file;
        this.isChecked = isChecked;
    }

    public FileItem(File file) {
        this.file = file;
        isChecked = false;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
