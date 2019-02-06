package com.carzis.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.response.ResultResponse;
import com.carzis.model.response.TokenResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;
import com.carzis.util.AndroidUtility;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr
 */
public class LoginPresenter implements Presenter<LoginView> {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginView view;
    private CarzisApi api;

    private KeyValueStorage keyValueStorage;
    private Context context;


    public LoginPresenter(Context context) {
        this.context = context;
        api = ApiUtils.getCarzisApi();
        keyValueStorage = new KeyValueStorage(context);
    }


    public void login(String email, String password) {
        view.showLoading(true);

        api.authWithEmail(AndroidUtility.getDeviceId(context), AndroidUtility.getDeviceName(), email, password)
                .enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        view.showLoading(false);
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            keyValueStorage.setUserToken(response.body().getToken());
                            view.onLogin();
                        } else {
                            view.showError(AppError.AUTH_USER_ERROR);
                            try {
                                Log.e(TAG, "onResponse: " + response.toString() );
                                Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        view.showLoading(false);
                        view.showError(AppError.AUTH_USER_ERROR);
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    public void sendMailWithPassword(String email) {
        view.showLoading(true);

        api.restorePassword(email)
                .enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        view.showLoading(false);
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().getResult());
                            view.onSendMailForRestorePassword();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        view.showLoading(false);
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    public void createAccount(String email, String password) {
        view.showLoading(true);

        api.registerWithEmail(AndroidUtility.getDeviceId(context), AndroidUtility.getDeviceName(), email, password)
                .enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        view.showLoading(false);
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            keyValueStorage.setUserToken(response.body().getToken());
                            view.onCreateAccount();
                        } else {
                            view.showError(AppError.AUTH_USER_ERROR);
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        view.showLoading(false);
                        view.showError(AppError.AUTH_USER_ERROR);
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }


    @Override
    public void attachView(LoginView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
