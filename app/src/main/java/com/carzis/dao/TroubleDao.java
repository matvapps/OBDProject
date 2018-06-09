package com.carzis.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.carzis.model.Trouble;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Alexandr.
 */

@Dao
public interface TroubleDao {

    @Insert
    void insertAll(Trouble... troubles);

    @Delete
    void delete(Trouble trouble);

    @Query("SELECT * FROM trouble")
    Flowable<List<Trouble>> getAllTroubles();

    @Query("SELECT * FROM trouble WHERE code LIKE :troubleCode")
    Single<Trouble> getTrouble(String troubleCode);

}
