package com.carzis.additionalscreen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.model.Car;
import com.carzis.repository.local.database.LocalRepository;

import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class AddCarFragment extends Fragment implements View.OnClickListener {


    private EditText carName;
    private EditText carBrand;
    private EditText carModel;
    private EditText carYear;
    private EditText carEngineNum;
    private EditText carBodyNum;
    private Button addNewCar;


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

        localRepository = new LocalRepository(Objects.requireNonNull(getContext()));

        addNewCar = rootView.findViewById(R.id.add_car_btn);
        addNewCar.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        String carNameStr = carName.getText().toString();
        String carBrandStr = carBrand.getText().toString();
        String carModelStr = carModel.getText().toString();
        String carYearStr = carYear.getText().toString();
        String carEngineNumStr = carEngineNum.getText().toString();
        String carBodyNumStr = carBodyNum.getText().toString();

        if (!carNameStr.isEmpty() && !carBrandStr.isEmpty()
                && !carModelStr.isEmpty() && !carYearStr.isEmpty()) {
            localRepository.addCar(new Car(carBrandStr, carModelStr, carYearStr, carEngineNumStr, carBodyNumStr, carNameStr));
            getActivity().finish();
        } else
            Toast.makeText(getContext(), "Обязательные поля не заполнены", Toast.LENGTH_SHORT).show();

    }
}
