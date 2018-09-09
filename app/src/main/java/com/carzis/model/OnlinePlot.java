package com.carzis.model;

import com.carzis.obd.PID;

import java.util.List;

public class OnlinePlot {

    private PID pid;
    private List<HistoryItem> historyItems;

    public OnlinePlot() {
    }

    public OnlinePlot(PID pid, List<HistoryItem> historyItems) {
        this.pid = pid;
        this.historyItems = historyItems;
    }

    public void addHistoryItem(HistoryItem historyItem) {
        historyItems.add(historyItem);
    }

    public PID getPid() {
        return pid;
    }

    public void setPid(PID pid) {
        this.pid = pid;
    }

    public List<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }
}
