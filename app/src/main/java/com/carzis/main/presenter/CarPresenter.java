package com.carzis.main.presenter;

import android.util.Log;

import com.carzis.base.Presenter;
import com.carzis.main.view.MyCarsView;
import com.carzis.model.AppError;
import com.carzis.model.Car;
import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.CarResponse;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class CarPresenter implements Presenter<MyCarsView> {

    private final String TAG = CarPresenter.class.getSimpleName();

    private MyCarsView view;
    private CarzisApi api;

    private String token;

    public CarPresenter(String token) {
        this.token = "Bearer " + token;
    }

    @Override
    public void attachView(MyCarsView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }


    public void getCars() {
        view.showLoading(true);
        api.getCars(token).enqueue(new Callback<List<CarResponse>>() {
            @Override
            public void onResponse(Call<List<CarResponse>> call, Response<List<CarResponse>> response) {
                view.showLoading(false);
                List<Car> cars = new ArrayList<>();
                if (response.code() == 200) {
                    for (CarResponse item : response.body()) {
                        Car car = new Car(item.getId(),
                                item.getCarBrand(),
                                item.getCarModel(),
                                item.getYear(),
                                item.getEngineNumber(),
                                item.getBodyNumber(),
                                item.getUserCarName());

                        cars.add(car);
                    }
                    view.onGetCars(cars);
                } else {
                    view.onRemoteRepoError();
                }
            }

            @Override
            public void onFailure(Call<List<CarResponse>> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.CAR_ERROR);
                view.onRemoteRepoError();
            }
        });
    }

    public void addCar(String carIdentifier, String carName, String carMark,
                       String carModel, String engineNumber, String bodyNumber) {

        view.showLoading(true);
        api.addCar(token, carIdentifier, carName,
                carMark, carModel, engineNumber, bodyNumber)
                .enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                view.showLoading(false);
                Log.d(TAG, "onResponse: " + response.message());
                if (response.code() == 200) {
                    view.onCarAdded();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(AppError.PROFILE_ERROR);
            }
        });

    }


    @Override
    public void detachView() {

    }
}
