package com.carzis.entry.register;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.response.RegisterResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

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
                view.onRegister();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.REGISTER_USER_ERROR);
            }
        });
    }

    @Override
    public void detachView() {}
}
