package com.carzis.obd;

import com.carzis.base.BaseView;

import java.util.List;

/**
 * Created by Alexandr
 */
public interface PidView extends BaseView {
    void onGetPids(List<PidNew> pids);
}
