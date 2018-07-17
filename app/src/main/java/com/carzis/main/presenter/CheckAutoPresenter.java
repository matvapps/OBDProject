package com.carzis.main.presenter;

import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.main.view.CheckAutoView;
import com.carzis.model.response.NumInfoResponse;
import com.carzis.model.response.VinInfoResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class CheckAutoPresenter implements Presenter<CheckAutoView>{

    private final String TAG = CheckAutoPresenter.class.getSimpleName();

    private CheckAutoView view;
    private CarzisApi api;
    private String token;

    public CheckAutoPresenter(String token) {
        this.token = token;
    }

    public void getInfoByVin(String vin) {
        view.showLoading(true);
        api.getInfoByVin("Bearer " + token, vin).enqueue(new Callback<VinInfoResponse>() {
            @Override
            public void onResponse(Call<VinInfoResponse> call, Response<VinInfoResponse> response) {
                view.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200)
                    view.onCheckAutoByVin(response.body());
            }

            @Override
            public void onFailure(Call<VinInfoResponse> call, Throwable t) {
                view.showLoading(false);
            }
        });
    }

    public void getInfoByNum(String num) {
        view.showLoading(true);
        api.getInfoByNum("Bearer " + token, num).enqueue(new Callback<NumInfoResponse>() {
            @Override
            public void onResponse(Call<NumInfoResponse> call, Response<NumInfoResponse> response) {
                view.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200)
                    view.onCheckAutoByNum(response.body());
            }

            @Override
            public void onFailure(Call<NumInfoResponse> call, Throwable t) {
                view.showLoading(false);
            }
        });
    }


    @Override
    public void attachView(CheckAutoView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
