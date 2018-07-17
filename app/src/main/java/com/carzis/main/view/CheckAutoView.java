package com.carzis.main.view;

import com.carzis.base.BaseView;
import com.carzis.model.response.NumInfoResponse;
import com.carzis.model.response.VinInfoResponse;

/**
 * Created by Alexandr.
 */
public interface CheckAutoView extends BaseView {
    void onCheckAutoByVin(VinInfoResponse vinInfoResponse);
    void onCheckAutoByNum(NumInfoResponse numInfoResponse);
}
