package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class VinInfoResponse extends BaseResponse {

    @SerializedName("gibdd_done")
    @Expose
    private int gibddDone;

    @SerializedName("gibdd_history_done")
    @Expose
    private int gibddHistoryDone;

    @SerializedName("gibdd_accidents_done")
    @Expose
    private int gibddAccidentsDone;

    @SerializedName("gibdd_searches_done")
    @Expose
    private int gibddSearchesDone;

    @SerializedName("gibdd_restrictions_done")
    @Expose
    private int gibddRestrictionsDone;

    @SerializedName("history")
    @Expose
    private String history;

    @SerializedName("accidents")
    @Expose
    private List<String> accidents;

    @SerializedName("searches")
    @Expose
    private List<String> searches;

    @SerializedName("restrictions")
    @Expose
    private List<String> restrictions;

    @SerializedName("gibdd_accidents_total_time")
    @Expose
    private float gibddAccidentsTotalTime;

    @SerializedName("gibdd_searches_total_time")
    @Expose
    private float gibddSearchesTotalTime;

    @SerializedName("gibdd_history_total_time")
    @Expose
    private float gibddHistoryTotalTime;

    @SerializedName("gibdd_restrictions_total_time")
    @Expose
    private float gibddRestrictionsTotalTime;

    public VinInfoResponse() {
    }

    public VinInfoResponse(int gibddDone, int gibddHistoryDone, int gibddAccidentsDone, int gibddSearchesDone, int gibddRestrictionsDone, String history, List<String> accidents, List<String> searches, List<String> restrictions, float gibddAccidentsTotalTime, float gibddSearchesTotalTime, float gibddHistoryTotalTime, float gibddRestrictionsTotalTime) {
        this.gibddDone = gibddDone;
        this.gibddHistoryDone = gibddHistoryDone;
        this.gibddAccidentsDone = gibddAccidentsDone;
        this.gibddSearchesDone = gibddSearchesDone;
        this.gibddRestrictionsDone = gibddRestrictionsDone;
        this.history = history;
        this.accidents = accidents;
        this.searches = searches;
        this.restrictions = restrictions;
        this.gibddAccidentsTotalTime = gibddAccidentsTotalTime;
        this.gibddSearchesTotalTime = gibddSearchesTotalTime;
        this.gibddHistoryTotalTime = gibddHistoryTotalTime;
        this.gibddRestrictionsTotalTime = gibddRestrictionsTotalTime;
    }

    public int getGibddDone() {
        return gibddDone;
    }

    public void setGibddDone(int gibddDone) {
        this.gibddDone = gibddDone;
    }

    public int getGibddHistoryDone() {
        return gibddHistoryDone;
    }

    public void setGibddHistoryDone(int gibddHistoryDone) {
        this.gibddHistoryDone = gibddHistoryDone;
    }

    public int getGibddAccidentsDone() {
        return gibddAccidentsDone;
    }

    public void setGibddAccidentsDone(int gibddAccidentsDone) {
        this.gibddAccidentsDone = gibddAccidentsDone;
    }

    public int getGibddSearchesDone() {
        return gibddSearchesDone;
    }

    public void setGibddSearchesDone(int gibddSearchesDone) {
        this.gibddSearchesDone = gibddSearchesDone;
    }

    public int getGibddRestrictionsDone() {
        return gibddRestrictionsDone;
    }

    public void setGibddRestrictionsDone(int gibddRestrictionsDone) {
        this.gibddRestrictionsDone = gibddRestrictionsDone;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<String> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<String> accidents) {
        this.accidents = accidents;
    }

    public List<String> getSearches() {
        return searches;
    }

    public void setSearches(List<String> searches) {
        this.searches = searches;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public float getGibddAccidentsTotalTime() {
        return gibddAccidentsTotalTime;
    }

    public void setGibddAccidentsTotalTime(float gibddAccidentsTotalTime) {
        this.gibddAccidentsTotalTime = gibddAccidentsTotalTime;
    }

    public float getGibddSearchesTotalTime() {
        return gibddSearchesTotalTime;
    }

    public void setGibddSearchesTotalTime(float gibddSearchesTotalTime) {
        this.gibddSearchesTotalTime = gibddSearchesTotalTime;
    }

    public float getGibddHistoryTotalTime() {
        return gibddHistoryTotalTime;
    }

    public void setGibddHistoryTotalTime(float gibddHistoryTotalTime) {
        this.gibddHistoryTotalTime = gibddHistoryTotalTime;
    }

    public float getGibddRestrictionsTotalTime() {
        return gibddRestrictionsTotalTime;
    }

    public void setGibddRestrictionsTotalTime(float gibddRestrictionsTotalTime) {
        this.gibddRestrictionsTotalTime = gibddRestrictionsTotalTime;
    }

    @Override
    public String toString() {
        return "gibddDone = " + gibddDone + "\n" +
                "gibddHistoryDone = " + gibddHistoryDone + "\n" +
                "gibddAccidentsDone = " + gibddAccidentsDone + "\n" +
                "gibddSearchesDone = " + gibddSearchesDone + "\n" +
                "gibddRestrictionsDone = " + gibddRestrictionsDone + "\n" +
                "history = " + history + "\n" +
                "accidents = " + accidents + "\n" +
                "searches = " + searches + "\n" +
                "restrictions = " + restrictions + "\n" +
                "gibddAccidentsTotalTime = " + gibddAccidentsTotalTime + "\n" +
                "gibddSearchesTotalTime = " + gibddSearchesTotalTime + "\n" +
                "gibddHistoryTotalTime = " + gibddHistoryTotalTime + "\n" +
                "gibddRestrictionsTotalTime = " + gibddRestrictionsTotalTime + "\n";
    }
}
