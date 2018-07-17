package com.carzis.main.presenter;

import com.carzis.base.Presenter;
import com.carzis.main.view.TroubleCodesView;
import com.carzis.model.AppError;
import com.carzis.model.Trouble;
import com.carzis.model.response.TroubleResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TroubleCodePresenter implements Presenter<TroubleCodesView> {

    private TroubleCodesView view;
    private CarzisApi api;

    public void getTroubleCodeDescription(String token, String troubleCode, String lang, String brand) {
        view.showLoading(true);
        api.getTrouble(token, troubleCode, lang, brand).enqueue(new Callback<TroubleResponse>() {
            @Override
            public void onResponse(Call<TroubleResponse> call, Response<TroubleResponse> response) {
                view.showLoading(false);
                if (response.code() == 200) {
                    view.onGetTroubleCode(new Trouble(troubleCode,
                            "",
                            response.body().getDescription(),
                            response.body().getFull_description()));
                } else {
                    view.onRemoteRepoError(troubleCode);
                    view.showError(AppError.GET_TROUBLE_FROM_REMOTE_REPO_ERROR);
                }
            }

            @Override
            public void onFailure(Call<TroubleResponse> call, Throwable t) {
                view.showLoading(false);
                view.onRemoteRepoError(troubleCode);
                view.showError(AppError.GET_TROUBLE_FROM_REMOTE_REPO_ERROR);
            }
        });
    }


    @Override
    public void attachView(TroubleCodesView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
