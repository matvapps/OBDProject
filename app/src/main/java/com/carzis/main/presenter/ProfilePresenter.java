package com.carzis.main.presenter;

import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.main.view.ProfileView;
import com.carzis.model.AppError;
import com.carzis.model.response.ProfileResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class ProfilePresenter implements Presenter<ProfileView> {

    private final String TAG = ProfilePresenter.class.getSimpleName();

    private ProfileView profileView;
    private CarzisApi api;
    private String token;

    public ProfilePresenter(String token) {
        this.token = token;
    }

    public void loadProfile() {
        profileView.showLoading(true);
        api.getProfile("Bearer " + token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                profileView.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if(response.code() == 200) {
                    profileView.onGetProfile(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileView.showLoading(false);
                profileView.showError(AppError.PROFILE_ERROR);
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    public void updateProfile(String token,
                              String email,
                              String firstName,
                              String secondName,
                              Long date) {
        profileView.showLoading(true);
        api.updateProfile("Bearer " + token,
                email,
                firstName,
                secondName,
                String.valueOf(date))
                .enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                profileView.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200)
                    profileView.onUpdateProfile();
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileView.showLoading(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
                profileView.showError(AppError.PROFILE_ERROR);
            }
        });


    }
    public void updateProfile(String token,
                              String email,
                              String firstName,
                              String secondName,
                              Long date,
                              MultipartBody.Part userImage) {
        api.updateProfile("Bearer " + token,
                email,
                firstName,
                secondName,
                String.valueOf(date),
                userImage)
                .enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                profileView.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200)
                    profileView.onUpdateProfile();
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                profileView.showError(AppError.PROFILE_ERROR);
            }
        });


    }

    @Override
    public void attachView(ProfileView view) {
        this.profileView = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
