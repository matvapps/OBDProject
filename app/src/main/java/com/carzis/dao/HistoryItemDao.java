package com.carzis.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.carzis.model.HistoryItem;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
@Dao
public interface HistoryItemDao {

    @Insert
    void insertAll(HistoryItem... historyItems);

    @Insert
    void insert(HistoryItem historyItem);

    @Delete
    void delete(HistoryItem historyItem);

    @Query("SELECT * FROM history")
    Flowable<List<HistoryItem>> getAllHistoryItems();

    @Query("SELECT * FROM history WHERE car_name LIKE :carName")
    Flowable<List<HistoryItem>> getHistoryItemByCar(String carName);

}
