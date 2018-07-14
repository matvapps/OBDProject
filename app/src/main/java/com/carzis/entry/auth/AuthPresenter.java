package com.carzis.entry.auth;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.response.ConfirmRegisterResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter implements Presenter<AuthView>{

    private AuthView view;
    private CarzisApi api;

    @Override
    public void attachView(AuthView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    public void auth(String phone, String password, String deviceId, String deviceName) {
        view.showLoading(true);
        api.auth(phone, password, deviceId, deviceName).enqueue(new Callback<ConfirmRegisterResponse>() {
            @Override
            public void onResponse(Call<ConfirmRegisterResponse> call, Response<ConfirmRegisterResponse> response) {
                view.showLoading(false);
            }

            @Override
            public void onFailure(Call<ConfirmRegisterResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.AUTH_USER_ERROR);
            }
        });
    }

    @Override
    public void detachView() {

    }
}
