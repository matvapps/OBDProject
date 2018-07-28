package com.carzis.additionalscreen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseFragment;
import com.carzis.main.presenter.CarPresenter;
import com.carzis.main.view.MyCarsView;
import com.carzis.model.Car;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.util.List;
import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class AddCarFragment extends BaseFragment implements View.OnClickListener, MyCarsView {


    private EditText carName;
    private EditText carBrand;
    private EditText carModel;
    private EditText carYear;
    private EditText carEngineNum;
    private EditText carBodyNum;
    private Button addNewCar;

    private String carNameStr;
    private String carBrandStr;
    private String carModelStr;
    private String carYearStr;
    private String carEngineNumStr;
    private String carBodyNumStr;

    private boolean addedTolocal = false;

    private KeyValueStorage keyValueStorage;
    private CarPresenter carPresenter;
    private LocalRepository localRepository;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_car, container, false);

        carName = rootView.findViewById(R.id.car_name_edtxt);
        carBrand = rootView.findViewById(R.id.car_brand_edtxt);
        carModel = rootView.findViewById(R.id.car_model_edtxt);
        carYear = rootView.findViewById(R.id.car_date_edtxt);
        carEngineNum = rootView.findViewById(R.id.car_engine_num_edtxt);
        carBodyNum = rootView.findViewById(R.id.car_body_num_edtxt);

        keyValueStorage = new KeyValueStorage(getContext());
        carPresenter = new CarPresenter(keyValueStorage.getUserToken());
        carPresenter.attachView(this);
        localRepository = new LocalRepository(Objects.requireNonNull(getContext()));
        localRepository.attachView(this);

        addNewCar = rootView.findViewById(R.id.add_car_btn);
        addNewCar.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        carNameStr = carName.getText().toString();
        carBrandStr = carBrand.getText().toString();
        carModelStr = carModel.getText().toString();
        carYearStr = carYear.getText().toString();
        carEngineNumStr = carEngineNum.getText().toString();
        carBodyNumStr = carBodyNum.getText().toString();

        if (!carNameStr.isEmpty() && !carBrandStr.isEmpty()
                && !carModelStr.isEmpty() && !carYearStr.isEmpty()) {

            if (carEngineNumStr.equals(""))
                carEngineNumStr = "null";
            if (carBodyNumStr.equals(""))
                carBodyNumStr = "null";

            carPresenter.addCar(carNameStr + keyValueStorage.getUserToken(), carNameStr, carBrandStr, carModelStr, carEngineNumStr, carBodyNumStr);
        } else
            Toast.makeText(getContext(), R.string.non_optional_fields_is_empty, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGetCar(Car car) {

    }

    @Override
    public void onGetCars(List<Car> cars) {

    }

    @Override
    public void onRemoteRepoError() {
    }

    @Override
    public void onCarAdded() {
        if (addedTolocal)
            getActivity().finish();
        else {
            localRepository.addCar(new Car(carNameStr + keyValueStorage.getUserToken(), carBrandStr, carModelStr, carYearStr, carEngineNumStr, carBodyNumStr, carNameStr));
            addedTolocal = true;
        }

    }

    @Override
    public void onDeleteCar() {

    }
}
