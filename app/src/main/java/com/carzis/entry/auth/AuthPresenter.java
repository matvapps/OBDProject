package com.carzis.entry.auth;

import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.response.ConfirmRegisterResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import java.util.Arrays;

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

    public void auth(String phone, Integer password, String deviceId, String deviceName) {
        view.showLoading(true);
        api.auth(phone, password, deviceName, deviceId).enqueue(new Callback<ConfirmRegisterResponse>() {
            @Override
            public void onResponse(Call<ConfirmRegisterResponse> call, Response<ConfirmRegisterResponse> response) {
                view.showLoading(false);
                Log.d("AuthPresenter", "onResponse: " + response.code());
                if (response.code() == 200)
                    view.onAuth(response.body().getToken());
            }

            @Override
            public void onFailure(Call<ConfirmRegisterResponse> call, Throwable t) {
                view.showLoading(false);
                Log.d("LogRegActivity", "onFailure: " + Arrays.toString(t.getStackTrace()));
                Log.d("LogRegActivity", "onFailure: " + t.toString());
                Log.d("LogRegActivity", "onFailure: " + t.getLocalizedMessage());
                Log.d("LogRegActivity", "onFailure: " + t.getMessage());

                view.showError(AppError.AUTH_USER_ERROR);
            }
        });
    }

    @Override
    public void detachView() {

    }
}
