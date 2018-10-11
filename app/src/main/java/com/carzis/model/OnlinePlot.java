package com.carzis.model;

import com.carzis.obd.PID;

import java.util.List;

public class OnlinePlot {

    private String pid;
    private List<HistoryItem> historyItems;

    public OnlinePlot() {
    }

    public OnlinePlot(String pid, List<HistoryItem> historyItems) {
        this.pid = pid;
        this.historyItems = historyItems;
    }

    public void addHistoryItem(HistoryItem historyItem) {
        historyItems.add(historyItem);
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }
}
