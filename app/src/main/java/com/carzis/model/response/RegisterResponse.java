package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseResponse{
    @SerializedName("device_id")
    @Expose
    protected String device_id;

    @SerializedName("phone")
    @Expose
    protected String phone;

    public RegisterResponse() {
    }

    public RegisterResponse(String device_id, String phone) {
        this.device_id = device_id;
        this.phone = phone;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "device_id = " + device_id + ", phone = " + phone;
    }
}
