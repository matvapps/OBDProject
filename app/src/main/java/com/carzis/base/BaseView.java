package com.carzis.base;

import com.carzis.model.LoadingError;

/**
 * Created by Alexandr.
 */
public interface BaseView{
    void showLoading(boolean load);
    void showError(LoadingError loadingError);
}
