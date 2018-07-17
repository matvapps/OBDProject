package com.carzis.model.response;

import com.carzis.model.Accident;
import com.carzis.model.Restriction;
import com.carzis.model.Search;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class InfoResponse extends BaseResponse {

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
    private HistoryResponse history;

    @SerializedName("accidents")
    @Expose
    private List<Accident> accidents;

    @SerializedName("searches")
    @Expose
    private List<Search> searches;

    @SerializedName("restrictions")
    @Expose
    private List<Restriction> restrictions;

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

    public InfoResponse() {
    }

    public InfoResponse(int gibddDone, int gibddHistoryDone, int gibddAccidentsDone, int gibddSearchesDone, int gibddRestrictionsDone, HistoryResponse history, List<Accident> accidents, List<Search> searches, List<Restriction> restrictions, float gibddAccidentsTotalTime, float gibddSearchesTotalTime, float gibddHistoryTotalTime, float gibddRestrictionsTotalTime) {
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

    public HistoryResponse getHistory() {
        return history;
    }

    public void setHistory(HistoryResponse history) {
        this.history = history;
    }

    public List<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<Accident> accidents) {
        this.accidents = accidents;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
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
        return
//                "gibddDone = " + gibddDone +
//                "\n" + "gibddHistoryDone = " + gibddHistoryDone +
//                "\n" + "gibddAccidentsDone = " + gibddAccidentsDone +
//                "\n" + "gibddSearchesDone = " + gibddSearchesDone +
//                "\n" + "gibddRestrictionsDone = " + gibddRestrictionsDone +
                "\n" + history +
                "\n" + accidents +
                "\n" + searches +
                "\n" + restrictions;
//                "\n" + "gibddAccidentsTotalTime = " + gibddAccidentsTotalTime +
//                "\n" + "gibddSearchesTotalTime = " + gibddSearchesTotalTime +
//                "\n" + "gibddHistoryTotalTime = " + gibddHistoryTotalTime +
//                "\n" + "gibddRestrictionsTotalTime = " + gibddRestrictionsTotalTime;
    }
}
