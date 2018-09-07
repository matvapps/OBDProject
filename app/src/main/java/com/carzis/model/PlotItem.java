package com.carzis.model;

import java.util.List;

/**
 * Created by Alexandr
 */

public class PlotItem {

    private String title;
    private List<HistoryItem> historyItems;

    public PlotItem() {
    }

    public PlotItem(String title, List<HistoryItem> historyItems) {
        this.title = title;
        this.historyItems = historyItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }
}
