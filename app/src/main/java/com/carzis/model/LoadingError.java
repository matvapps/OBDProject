package com.carzis.model;

/**
 * Created by Alexandr.
 */
public enum LoadingError {
    GET_TROUBLE_FROM_LOCAL_REPO_ERROR(101),
    GET_TROUBLE_LIST_FROM_LOCAL_REPO_ERROR(102),
    GET_CAR_FROM_LOCAL_REPO_ERROR(103),
    GET_CAR_LIST_FROM_LOCAL_REPO_ERROR(104);

    public final int value;

    LoadingError(int value) {
        this.value = value;
    }
}
