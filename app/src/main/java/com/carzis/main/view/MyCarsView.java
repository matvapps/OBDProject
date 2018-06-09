package com.carzis.main.view;

import com.carzis.base.BaseView;
import com.carzis.model.Car;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface MyCarsView extends BaseView {
    void onGetCar(Car car);
    void onGetCars(List<Car> cars);
}
