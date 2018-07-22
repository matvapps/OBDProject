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
    @NonNull
    @ColumnInfo(name = "car_name")
    private String carName;
    @NonNull
    @ColumnInfo(name = "pid_id")
    private String pidId;
    @NonNull
    private String value;
    @PrimaryKey
    @NonNull
    private String time;


    public HistoryItem(@NonNull String carName, @NonNull String pidId, @NonNull String value, @NonNull String time) {
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
    public String getPidId() {
        return pidId;
    }

    public void setPidId(@NonNull String pidId) {
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

    @Override
    public String toString() {
        return "HistoryItem{" +
                "carName='" + carName + '\'' +
                ", pidId='" + pidId + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
