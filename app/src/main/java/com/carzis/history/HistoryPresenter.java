package com.carzis.history;

import android.support.annotation.NonNull;
import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.model.HistoryItem;
import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.CarMetricResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class HistoryPresenter implements Presenter<HistoryView> {

    private static final String TAG = HistoryPresenter.class.getSimpleName();

    private HistoryView view;
    private CarzisApi api;

    private String token;

    public HistoryPresenter(String token) {
        this.token = "Bearer " + token;
    }

    public void addMetric(String carId, String code, String value) {
        view.showLoading(true);
        api.addCarMetric(token, carId, code, value).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                view.showLoading(false);
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                view.showLoading(false);
            }
        });
    }

    public void getCarMetric(String carId, String code, String from, String to) {
        view.showLoading(true);
        api.getCarMetrics(token, carId, code, from, to).enqueue(new Callback<List<CarMetricResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarMetricResponse>> call, @NonNull Response<List<CarMetricResponse>> response) {
                view.showLoading(false);
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarMetricResponse>> call, @NonNull Throwable t) {
                view.showLoading(false);
            }
        });
    }

    public void getCarMetric(String carName, String carId, String code) {
        view.showLoading(true);
        api.getCarMetrics(token, carId, code, "0", "999999999999999999").enqueue(new Callback<List<CarMetricResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarMetricResponse>> call, @NonNull Response<List<CarMetricResponse>> response) {
                view.showLoading(false);
                List<HistoryItem> items = new ArrayList<>();
                if (response.code() == 200) {
                    for (CarMetricResponse item : response.body()) {
                        String timeCreated = item.getTimeCreated();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        try {
                            cal.setTime(simpleDateFormat.parse(timeCreated));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        HistoryItem historyItem = new HistoryItem(carName,
                                item.getMetricCode(),
                                item.getMetricValue(),
                                Long.toString(cal.getTimeInMillis()));

                        items.add(historyItem);

                        Log.d("TAG", "onGetHistoryItems presenter: " + items);
                    }
                    view.onGetHistoryItems(items, carName);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarMetricResponse>> call, @NonNull Throwable t) {
                view.showLoading(false);
            }
        });
    }

    @Override
    public void attachView(HistoryView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
