package com.carzis.history;

import android.support.annotation.NonNull;
import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.model.AppError;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;
import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.CarMetricResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
//        view.showLoading(true);

        api.addCarMetric(token, new CarMetric(carId, code, value), new CarMetric(carId, code, value)).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
//                view.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message() + "\n" + response.toString());
                if (response.code() == 200) {
                    view.onCarMetricAdded(new CarMetric(carId, code, value));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
//                view.showLoading(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
                view.showError(AppError.ADD_CAR_METRIC);
            }
        });
    }

    public void getCarMetric(String carName, String carId, String code, String from, String to) {
        view.showLoading(true);
        api.getCarMetrics(token, carId, code, from, to).enqueue(new Callback<List<CarMetricResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarMetricResponse>> call, @NonNull Response<List<CarMetricResponse>> response) {
                view.showLoading(false);
                List<HistoryItem> items = new ArrayList<>();
                if (response.code() == 200) {
                    for (CarMetricResponse item : response.body()) {
                        String timeCreated = item.getTimeCreated();
                        Calendar cal = Calendar.getInstance();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        try {
                            cal.setTime(simpleDateFormat.parse(timeCreated));

                            Calendar mCalendar = new GregorianCalendar();
                            TimeZone mTimeZone = mCalendar.getTimeZone();
                            int mGMTOffset = mTimeZone.getRawOffset() + mTimeZone.getDSTSavings();

                            cal.add(Calendar.MILLISECOND, mGMTOffset);

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
                view.showError(AppError.GET_HISTORY_ITEMS_ERROR);
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

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        try {
                            cal.setTime(simpleDateFormat.parse(timeCreated));

                            Calendar mCalendar = new GregorianCalendar();
                            TimeZone mTimeZone = mCalendar.getTimeZone();
                            int mGMTOffset = mTimeZone.getRawOffset() + mTimeZone.getDSTSavings();

                            cal.add(Calendar.MILLISECOND, mGMTOffset);

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
                view.showError(AppError.GET_HISTORY_ITEMS_ERROR);
            }
        });
    }

    public void getAllCarMetric(String carId, String carName) {
        view.showLoading(true);
        api.getCarMetrics(token, carId, "0", "999999999999999999").enqueue(new Callback<List<CarMetricResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarMetricResponse>> call, @NonNull Response<List<CarMetricResponse>> response) {
                List<HistoryItem> items = new ArrayList<>();
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200) {
                    for (CarMetricResponse item : response.body()) {
                        String timeCreated = item.getTimeCreated();
                        Calendar cal = Calendar.getInstance();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        try {
                            cal.setTime(simpleDateFormat.parse(timeCreated));

                            Calendar mCalendar = new GregorianCalendar();
                            TimeZone mTimeZone = mCalendar.getTimeZone();
                            int mGMTOffset = mTimeZone.getRawOffset() + mTimeZone.getDSTSavings();

                            cal.add(Calendar.MILLISECOND, mGMTOffset);

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
                    view.showLoading(false);
                    view.onGetHistoryItems(items, carName);
                } else {
                    view.showLoading(false);
                    view.onRemoteRepoError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarMetricResponse>> call, @NonNull Throwable t) {
                view.showLoading(false);
                view.onRemoteRepoError();
                view.showError(AppError.GET_HISTORY_ITEMS_ERROR);
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
