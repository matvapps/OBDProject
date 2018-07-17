package com.carzis.main.view;

import com.carzis.base.BaseView;
import com.carzis.model.response.NumInfoResponse;
import com.carzis.model.response.InfoResponse;

/**
 * Created by Alexandr.
 */
public interface CheckAutoView extends BaseView {
    void onCheckAutoByVin(InfoResponse infoResponse);
    void onCheckAutoByNum(InfoResponse numInfoResponse);
}
