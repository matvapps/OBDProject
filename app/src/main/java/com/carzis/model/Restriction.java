package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restriction {

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("model_year")
    @Expose
    private String modelYear;

    @SerializedName("restriction_date")
    @Expose
    private String restrictionDate;

    @SerializedName("region")
    @Expose
    private String region;

    @SerializedName("restriction_name")
    @Expose
    private String restrictionName;

    @SerializedName("organization_name")
    @Expose
    private String organizationName;


    public Restriction() {
    }

    public Restriction(String model, String modelYear, String restrictionDate, String region, String restrictionName, String organizationName) {
        this.model = model;
        this.modelYear = modelYear;
        this.restrictionDate = restrictionDate;
        this.region = region;
        this.restrictionName = restrictionName;
        this.organizationName = organizationName;
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

    public String getRestrictionDate() {
        return restrictionDate;
    }

    public void setRestrictionDate(String restrictionDate) {
        this.restrictionDate = restrictionDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRestrictionName() {
        return restrictionName;
    }

    public void setRestrictionName(String restrictionName) {
        this.restrictionName = restrictionName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        return "Ограничение: " + '\n' +
                "\t" + "Модель: " + model + '\n' +
                "\t" + "Год выпуска: " + modelYear + '\n' +
                "\t" + "Дата наложения ограничения: " + restrictionDate + '\n' +
                "\t" + "Регион: " + region + '\n' +
                "\t" + "Тип ограничения: " + restrictionName + '\n' +
                "\t" + "Кто наложил ограничение: " + organizationName + '\n';
    }
}
