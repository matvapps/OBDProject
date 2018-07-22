package com.carzis.model;

/**
 * Created by Alexandr.
 */
public enum AppError {
    GET_TROUBLE_FROM_LOCAL_REPO_ERROR(101),
    GET_TROUBLE_LIST_FROM_LOCAL_REPO_ERROR(102),
    GET_CAR_FROM_LOCAL_REPO_ERROR(103),
    GET_CAR_LIST_FROM_LOCAL_REPO_ERROR(104),
    REGISTER_USER_ERROR(105),
    REGISTER_USER_PHONE_EXIST_ERROR(110),
    REGISTER_USER_DEVICE_ID_EXIST_ERROR(111),
    CONFIRM_REGISTER_USER_ERROR(108),
    AUTH_USER_ERROR(106),
    GET_TROUBLE_FROM_REMOTE_REPO_ERROR(107);

    public final int value;

    AppError(int value) {
        this.value = value;
    }
}