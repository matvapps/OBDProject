package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TroubleResponse extends BaseResponse {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("full_description")
    @Expose
    private String full_description;

    public TroubleResponse() {
    }

    public TroubleResponse(String description, String full_description) {
        this.description = description;
        this.full_description = full_description;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
