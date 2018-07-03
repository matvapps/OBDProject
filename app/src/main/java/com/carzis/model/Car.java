package com.carzis.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Alexandr.
 */
@Entity
public class Car {

    private String brand;
    private String model;
    private String year;
    private String engine_num;
    private String bodywork_num;

    @PrimaryKey
    @NonNull
    private String name;

    public Car(String brand, String model, String year, String engine_num, String bodywork_num, String name) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engine_num = engine_num;
        this.bodywork_num = bodywork_num;
        this.name = name;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngine_num() {
        return engine_num;
    }

    public void setEngine_num(String engine_num) {
        this.engine_num = engine_num;
    }

    public String getBodywork_num() {
        return bodywork_num;
    }

    public void setBodywork_num(String bodywork_num) {
        this.bodywork_num = bodywork_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
