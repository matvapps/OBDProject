package com.carzis.base;

import com.carzis.model.AppError;

/**
 * Created by Alexandr.
 */
public interface BaseView{
    void showLoading(boolean load);
    void showError(AppError appError);
}
