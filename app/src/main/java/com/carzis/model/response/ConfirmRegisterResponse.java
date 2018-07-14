package com.carzis.model.response;

import com.carzis.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfirmRegisterResponse extends BaseResponse {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("active_devices")
    @Expose
    private List<Device> activeDevices;

    public ConfirmRegisterResponse() {
    }

    public ConfirmRegisterResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public ConfirmRegisterResponse(User user, String token, List<Device> activeDevices) {
        this.user = user;
        this.token = token;
        this.activeDevices = activeDevices;
    }

    public List<Device> getActiveDevices() {
        return activeDevices;
    }

    public void setActiveDevices(List<Device> activeDevices) {
        this.activeDevices = activeDevices;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
