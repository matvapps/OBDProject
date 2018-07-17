package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnershipPeriod {

    @SerializedName("personType")
    @Expose
    private String personType;

    @SerializedName("from")
    @Expose
    private String fromDate;

    @SerializedName("to")
    @Expose
    private String toDate;

    @SerializedName("lastOperation")
    @Expose
    private String lastOperation;

    @SerializedName("lastOperationId")
    @Expose
    private Integer lastOperationId;

    public OwnershipPeriod() {
    }

    public OwnershipPeriod(String personType, String fromDate, String toDate, String lastOperation, Integer lastOperationId) {
        this.personType = personType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.lastOperation = lastOperation;
        this.lastOperationId = lastOperationId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public Integer getLastOperationId() {
        return lastOperationId;
    }

    public void setLastOperationId(Integer lastOperationId) {
        this.lastOperationId = lastOperationId;
    }

    @Override
    public String toString() {
        return "Период владения: " + '\n' +
                 "\t" + "Тип владельца: " + personType + '\n' +
                 "\t" + "От: " + fromDate + '\n' +
                 "\t" + "До: " + toDate + '\n' +
                 "\t" + "Последняя операция: " + lastOperation + '\n';
    }
}
