package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandr.
 */
public class NumInfoResponse extends BaseResponse {

    @SerializedName("kbm_done")
    @Expose
    private int kbmDone;

    public NumInfoResponse() {
    }

    public NumInfoResponse(int kbmDone) {
        this.kbmDone = kbmDone;
    }

    public int getKbmDone() {
        return kbmDone;
    }

    public void setKbmDone(int kbmDone) {
        this.kbmDone = kbmDone;
    }

    @Override
    public String toString() {
        return "kbmDone = " + kbmDone;
    }
}
