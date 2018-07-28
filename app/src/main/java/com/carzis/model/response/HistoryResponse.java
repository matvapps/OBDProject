package com.carzis.model.response;

import com.carzis.model.OwnershipPeriod;
import com.carzis.model.VehiclePassport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse extends BaseResponse {

    @SerializedName("engineVolume")
    @Expose
    private Float engineVolume;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("bodyNumber")
    @Expose
    private String bodyNumber;

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("engineNumber")
    @Expose
    private String engineNumber;

    @SerializedName("vin")
    @Expose
    private String vin;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("powerHp")
    @Expose
    private Integer powerHp;

    @SerializedName("powerKwt")
    @Expose
    private Float powerKwt;

    @SerializedName("vehiclePassport")
    @Expose
    private VehiclePassport vehiclePassport;

    @SerializedName("ownershipPeriods")
    @Expose
    private List<OwnershipPeriod> owernshipPeriodList;

    public HistoryResponse() {
    }

    public HistoryResponse(Float engineVolume, String color, String bodyNumber, Integer year, String engineNumber, String vin, String model, String category, String type, Integer powerHp, Float powerKwt, VehiclePassport vehiclePassport, List<OwnershipPeriod> owernshipPeriodList) {
        this.engineVolume = engineVolume;
        this.color = color;
        this.bodyNumber = bodyNumber;
        this.year = year;
        this.engineNumber = engineNumber;
        this.vin = vin;
        this.model = model;
        this.category = category;
        this.type = type;
        this.powerHp = powerHp;
        this.powerKwt = powerKwt;
        this.vehiclePassport = vehiclePassport;
        this.owernshipPeriodList = owernshipPeriodList;
    }

    public Float getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(Float engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPowerHp() {
        return powerHp;
    }

    public void setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
    }

    public Float getPowerKwt() {
        return powerKwt;
    }

    public void setPowerKwt(Float powerKwt) {
        this.powerKwt = powerKwt;
    }

    public VehiclePassport getVehiclePassport() {
        return vehiclePassport;
    }

    public void setVehiclePassport(VehiclePassport vehiclePassport) {
        this.vehiclePassport = vehiclePassport;
    }

    public List<OwnershipPeriod> getOwernshipPeriodList() {
        return owernshipPeriodList;
    }

    public void setOwernshipPeriodList(List<OwnershipPeriod> owernshipPeriodList) {
        this.owernshipPeriodList = owernshipPeriodList;
    }

    @Override
    public String toString() {
        return "История: " + '\n' +
                "\t" + "Объём двигателя: " + engineVolume + '\n' +
                "\t" + "Цвет'" + color + '\n' +
                "\t" + "Номер кузова: " + bodyNumber + '\n' +
                "\t" + "Год: " + year + '\n' +
                "\t" + "Номер двигателя: " + engineNumber + '\n' +
                "\t" + "VIN-код: '" + vin + '\n' +
                "\t" + "Модель: " + model + '\n' +
                "\t" + "Категория: " + category + '\n' +
                "\t" + "Тип: " + type + '\n' +
                "\t" + "Л/С: " + powerHp + '\n' +
                "\t" + "Квт: " + powerKwt + '\n' +
                "\t" + vehiclePassport + '\n' +
                owernshipPeriodList + '\n';
    }
}
