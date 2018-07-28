package com.carzis.history;

import com.carzis.base.BaseView;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface HistoryView extends BaseView {
    void onGetHistoryItems(List<HistoryItem> items, String carName);
    void onCarMetricAdded(CarMetric carMetric);
    void onRemoteRepoError();
}
