package com.carzis.model;

import com.carzis.model.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehiclePassport extends BaseResponse {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("issue")
    @Expose
    private String issue;

    public VehiclePassport() {
    }

    public VehiclePassport(String number, String issue) {
        this.number = number;
        this.issue = issue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "Паспорт авто:" + '\n' +
                "\t" + "номер: " + number + '\n' +
                "\t" + "выпуск: " + issue + '\n';
    }
}
