package com.carzis.main.view;

import com.carzis.base.BaseView;
import com.carzis.model.Trouble;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface TroubleCodesView extends BaseView {
    void onGetTroubleCode(Trouble trouble);
    void onGetTroubleCodes(List<Trouble> troubles);
    void onRemoteRepoError(String code);
}
