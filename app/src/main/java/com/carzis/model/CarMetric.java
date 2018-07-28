package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class CarMetric {

    @SerializedName("car_id")
    @Expose
    private String carId;

    @SerializedName("metric_code")
    @Expose
    private String metricCode;


    @SerializedName("metric_value")
    @Expose
    private String metricValue;

    public CarMetric(String carId, String metricCode, String metricValue) {
        this.carId = carId;
        this.metricCode = metricCode;
        this.metricValue = metricValue;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMetricCode() {
        return metricCode;
    }

    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }
}
