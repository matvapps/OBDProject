package com.carzis.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.carzis.model.Trouble;
import com.carzis.obd.PidNew;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Alexandr
 */
@Dao
public interface PidDao {
    @Insert
    void insertAll(PidNew... pids);

    @Delete
    void delete(PidNew pid);

    @Query("SELECT * FROM pidnew")
    Flowable<List<PidNew>> getDefaultPids();

    @Query("SELECT * FROM pidnew WHERE pid LIKE :pid")
    Single<PidNew> getPidItem(String pid);
}
