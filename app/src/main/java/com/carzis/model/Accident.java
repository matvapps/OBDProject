package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accident {

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("mark")
    @Expose
    private String mark;

    @SerializedName("accidentType")
    @Expose
    private String accidentType;

    @SerializedName("regionName")
    @Expose
    private String regionName;

    @SerializedName("vehicleDamageState")
    @Expose
    private String vehicleDamageState;

    @SerializedName("accidentDateTime")
    @Expose
    private String accidentDateTime;

    @SerializedName("damageImages")
    @Expose
    private String damageImages;

    public Accident() {
    }

    public Accident(String model, String mark, String accidentType, String regionName, String vehicleDamageState, String accidentDateTime, String damageImages) {
        this.model = model;
        this.mark = mark;
        this.accidentType = accidentType;
        this.regionName = regionName;
        this.vehicleDamageState = vehicleDamageState;
        this.accidentDateTime = accidentDateTime;
        this.damageImages = damageImages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getVehicleDamageState() {
        return vehicleDamageState;
    }

    public void setVehicleDamageState(String vehicleDamageState) {
        this.vehicleDamageState = vehicleDamageState;
    }

    public String getAccidentDateTime() {
        return accidentDateTime;
    }

    public void setAccidentDateTime(String accidentDateTime) {
        this.accidentDateTime = accidentDateTime;
    }

    public String getDamageImages() {
        return damageImages;
    }

    public void setDamageImages(String damageImages) {
        this.damageImages = damageImages;
    }

    @Override
    public String toString() {
        return "ДТП: " + '\n' +
                "\t" + "Модель: " + model + '\n' +
                "\t" + "Марка: " + mark + '\n' +
                "\t" + "Тип аварии: " + accidentType + '\n' +
                "\t" + "Регион: " + regionName + '\n' +
                "\t" + "Повреждение: " + vehicleDamageState + '\n' +
                "\t" + "Дата: " + accidentDateTime + '\n' +
                "\t" + "Изображение: " + damageImages + '\n';
    }
}
