package com.carzis.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Alexandr.
 */
@Entity(tableName = "history")
public class HistoryItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "car_name")
    private String carName;
    @NonNull
    @ColumnInfo(name = "pid_id")
    private Integer pidId;
    @NonNull
    private String value;
    @NonNull
    private String time;


    public HistoryItem(@NonNull String carName, @NonNull Integer pidId, @NonNull String value, @NonNull String time) {
        this.carName = carName;
        this.pidId = pidId;
        this.value = value;
        this.time = time;
    }

    @NonNull
    public String getCarName() {
        return carName;
    }

    public void setCarName(@NonNull String carName) {
        this.carName = carName;
    }

    @NonNull
    public Integer getPidId() {
        return pidId;
    }

    public void setPidId(@NonNull Integer pidId) {
        this.pidId = pidId;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }
}
