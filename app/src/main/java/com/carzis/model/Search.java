package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("region")
    @Expose
    private String region;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("model_year")
    @Expose
    private String modelYear;

    @SerializedName("search_date")
    @Expose
    private String searchDate;

    public Search() {
    }

    public Search(String region, String model, String modelYear, String searchDate) {
        this.region = region;
        this.model = model;
        this.modelYear = modelYear;
        this.searchDate = searchDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    @Override
    public String toString() {
        return "Розыск: " + '\n' +
                "\t" + "Регион розыска: " + region + '\n' +
                "\t" + "Модель: " + model + '\n' +
                "\t" + "Год выпуска: " + modelYear + '\n' +
                "\t" + "Дата начала розыска: " + searchDate + '\n';
    }
}
