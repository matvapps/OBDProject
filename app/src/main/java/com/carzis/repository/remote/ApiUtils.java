package com.carzis.repository.remote;

public class ApiUtils {
    public static CarzisApi getCarzisApi() {
        return NetworkClient.getRetrofit().create(CarzisApi.class);
    }
}
