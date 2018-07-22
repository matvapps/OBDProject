package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class CarResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("car_identifier")
    @Expose
    private String carId;

    @SerializedName("user_car_name")
    @Expose
    private String userCarName;

    @SerializedName("car_mark")
    @Expose
    private String carBrand;

    @SerializedName("car_model")
    @Expose
    private String carModel;

    @SerializedName("vin")
    @Expose
    private String vin;

    @SerializedName("state_number")
    @Expose
    private String stateNumber;

    @SerializedName("body_number")
    @Expose
    private String bodyNumber;

    @SerializedName("chassis_number")
    @Expose
    private String chassisNumber;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("car_type")
    @Expose
    private String carType;

    @SerializedName("body_type")
    @Expose
    private String bodyType;

    @SerializedName("number_of_dors")
    @Expose
    private String numberOfDoors;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("engine_capacity")
    @Expose
    private String engineCapacity;

    @SerializedName("engine_horse_power")
    @Expose
    private String engineHorsePower;

    @SerializedName("engine_kilowatt_power")
    @Expose
    private String engineKilowattPower;

    @SerializedName("torque")
    @Expose
    private String torque;

    @SerializedName("engine_number")
    @Expose
    private String engineNumber;

    @SerializedName("drive_unit")
    @Expose
    private String driveUnit;

    @SerializedName("gearbox_type")
    @Expose
    private String gearboxType;

    @SerializedName("number_of_gears")
    @Expose
    private String numberOfGears;

    @SerializedName("steering_column_positioning")
    @Expose
    private String steeringColumnPositioning;

    @SerializedName("country_of_assembly")
    @Expose
    private String countryOfAssembly;

    @SerializedName("country_of_origin")
    @Expose
    private String countryOfOrigin;

    @SerializedName("vechicle_class")
    @Expose
    private String vehicleClass;

    @SerializedName("provider_add_info")
    @Expose
    private String providerAddInfo;

    @SerializedName("weigth_without_load")
    @Expose
    private String weightWithoutLoad;

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public CarResponse() {
    }

    public CarResponse(String id, String carId, String userCarName, String carBrand, String carModel, String vin, String stateNumber, String bodyNumber, String chassisNumber, String year, String carType, String bodyType, String numberOfDoors, String color, String engineCapacity, String engineHorsePower, String engineKilowattPower, String torque, String engineNumber, String driveUnit, String gearboxType, String numberOfGears, String steeringColumnPositioning, String countryOfAssembly, String countryOfOrigin, String vehicleClass, String providerAddInfo, String weightWithoutLoad, String userId, String updatedAt, String createdAt) {
        this.id = id;
        this.carId = carId;
        this.userCarName = userCarName;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.vin = vin;
        this.stateNumber = stateNumber;
        this.bodyNumber = bodyNumber;
        this.chassisNumber = chassisNumber;
        this.year = year;
        this.carType = carType;
        this.bodyType = bodyType;
        this.numberOfDoors = numberOfDoors;
        this.color = color;
        this.engineCapacity = engineCapacity;
        this.engineHorsePower = engineHorsePower;
        this.engineKilowattPower = engineKilowattPower;
        this.torque = torque;
        this.engineNumber = engineNumber;
        this.driveUnit = driveUnit;
        this.gearboxType = gearboxType;
        this.numberOfGears = numberOfGears;
        this.steeringColumnPositioning = steeringColumnPositioning;
        this.countryOfAssembly = countryOfAssembly;
        this.countryOfOrigin = countryOfOrigin;
        this.vehicleClass = vehicleClass;
        this.providerAddInfo = providerAddInfo;
        this.weightWithoutLoad = weightWithoutLoad;
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public String getUserCarName() {
        return userCarName;
    }

    public void setUserCarName(String userCarName) {
        this.userCarName = userCarName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(String numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getEngineHorsePower() {
        return engineHorsePower;
    }

    public void setEngineHorsePower(String engineHorsePower) {
        this.engineHorsePower = engineHorsePower;
    }

    public String getEngineKilowattPower() {
        return engineKilowattPower;
    }

    public void setEngineKilowattPower(String engineKilowattPower) {
        this.engineKilowattPower = engineKilowattPower;
    }

    public String getTorque() {
        return torque;
    }

    public void setTorque(String torque) {
        this.torque = torque;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getDriveUnit() {
        return driveUnit;
    }

    public void setDriveUnit(String driveUnit) {
        this.driveUnit = driveUnit;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public String getNumberOfGears() {
        return numberOfGears;
    }

    public void setNumberOfGears(String numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    public String getSteeringColumnPositioning() {
        return steeringColumnPositioning;
    }

    public void setSteeringColumnPositioning(String steeringColumnPositioning) {
        this.steeringColumnPositioning = steeringColumnPositioning;
    }

    public String getCountryOfAssembly() {
        return countryOfAssembly;
    }

    public void setCountryOfAssembly(String countryOfAssembly) {
        this.countryOfAssembly = countryOfAssembly;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getProviderAddInfo() {
        return providerAddInfo;
    }

    public void setProviderAddInfo(String providerAddInfo) {
        this.providerAddInfo = providerAddInfo;
    }

    public String getWeightWithoutLoad() {
        return weightWithoutLoad;
    }

    public void setWeightWithoutLoad(String weightWithoutLoad) {
        this.weightWithoutLoad = weightWithoutLoad;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
