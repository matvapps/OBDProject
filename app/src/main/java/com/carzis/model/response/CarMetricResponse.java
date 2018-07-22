package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class CarMetricResponse extends BaseResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("car_id")
    @Expose
    private String carId;

    @SerializedName("metric_code")
    @Expose
    private String metricCode;

    @SerializedName("metric_value")
    @Expose
    private String metricValue;

    @SerializedName("created_at")
    @Expose
    private String timeCreated;

    public CarMetricResponse() {
    }

    public CarMetricResponse(String id, String carId, String metricCode, String metricValue, String timeCreated) {
        this.id = id;
        this.carId = carId;
        this.metricCode = metricCode;
        this.metricValue = metricValue;
        this.timeCreated = timeCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CarMetricResponse{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", metricCode='" + metricCode + '\'' +
                ", metricValue='" + metricValue + '\'' +
                '}';
    }
}
