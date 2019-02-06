package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class ResultResponse {

    @SerializedName("result")
    @Expose
    private String result;

    public ResultResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
