package com.carzis.entry.register;

import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.ConfirmRegisterResponse;
import com.carzis.model.response.RegisterResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements Presenter<RegisterView> {

    private RegisterView view;
    private CarzisApi api;

    @Override
    public void attachView(RegisterView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    public void register(String phone, String deviceId, String deviceName) {
        view.showLoading(true);
        api.registerUser(phone, deviceId, deviceName).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                view.showLoading(false);
                Log.d("RegisterPresenter", "onResponse: " + response.code() + response.toString());
                if (response.code() == 200) {
                    view.onRegister();
                } else if (response.code() == 400) {
                    try {
                        String errorResponse = response.errorBody().string();
                        if (errorResponse.contains("phone")) {
                            view.onPhoneExisted(phone);
                        }
                        if (errorResponse.contains("device_id")) {
                            view.showError(AppError.REGISTER_USER_DEVICE_ID_EXIST_ERROR);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.REGISTER_USER_ERROR);
                Log.d("RegisterPresenter", "onFailure: " + t.getMessage());
            }
        });
    }

    public void confirmRegister(String phone, Integer code) {
        view.showLoading(true);
        api.confirmRegister(phone, code).enqueue(new Callback<ConfirmRegisterResponse>() {
            @Override
            public void onResponse(Call<ConfirmRegisterResponse> call, Response<ConfirmRegisterResponse> response) {
                view.showLoading(false);
                if (response.code() == 200)
                    view.onConfirmRegister(response.body().getToken());
            }

            @Override
            public void onFailure(Call<ConfirmRegisterResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.CONFIRM_REGISTER_USER_ERROR);
            }
        });
    }

    public void resendCode(String phone) {
        view.showLoading(true);
        api.resendCode(phone).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void detachView() {
    }
}
