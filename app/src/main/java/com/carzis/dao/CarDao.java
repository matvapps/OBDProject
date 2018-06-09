package com.carzis.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.carzis.model.Car;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Alexandr.
 */
@Dao
public interface CarDao {

    @Insert
    void insertAll(Car... cars);

    @Insert
    void insert(Car car);

    @Delete
    void delete(Car car);

    @Query("SELECT * FROM car")
    Flowable<List<Car>> getAllCars();

    @Query("SELECT * FROM car WHERE name LIKE :name")
    Single<Car> getCar(String name);

}
