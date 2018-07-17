package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.main.presenter.CheckAutoPresenter;
import com.carzis.main.view.CheckAutoView;
import com.carzis.model.AppError;
import com.carzis.model.response.InfoResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;

/**
 * Created by Alexandr.
 */
public class CheckAutoFragment extends Fragment implements View.OnClickListener, CheckAutoView {

    private static final String TAG = CheckAutoFragment.class.getSimpleName();

    private View checkCarByVin;
    private View checkCarByNum;
    private View btnSearch;
    private EditText edtxt;
    private TextView carInfoContent;
    private TextView titleTxt;
    private TextView subTitleTxt;

    private final int CHECK_CAR_BY_NUM = 1;
    private final int CHECK_CAR_BY_VIN = 2;

    private final String CURRENT_CHECK = "current_check";
    private final String CURRENT_VALUE = "current_value";
    private final String CURRENT_RESPONSE = "current_response";

    private KeyValueStorage keyValueStorage;
    private CheckAutoPresenter checkAutoPresenter;

    private String currentValue;
    private String currentResponse;
    private int currentCheck;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_check_auto, container, false);

        checkCarByNum = rootView.findViewById(R.id.check_car_num_btn);
        checkCarByVin = rootView.findViewById(R.id.check_car_vin_btn);
        btnSearch = rootView.findViewById(R.id.btn_search);
        edtxt = rootView.findViewById(R.id.vin_edtxt);
        carInfoContent = rootView.findViewById(R.id.car_content_info);
        titleTxt = rootView.findViewById(R.id.check_text);
        subTitleTxt = rootView.findViewById(R.id.sub_title_text);

        checkCarByNum.setOnClickListener(this);
        checkCarByVin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        checkCarByVin.callOnClick();

        keyValueStorage = new KeyValueStorage(getContext());
        checkAutoPresenter = new CheckAutoPresenter(keyValueStorage.getUserToken());
        checkAutoPresenter.attachView(this);

        if (savedInstanceState != null) {
            currentCheck = savedInstanceState.getInt(CURRENT_CHECK);
            currentResponse = savedInstanceState.getString(CURRENT_RESPONSE);
            currentValue = savedInstanceState.getString(CURRENT_VALUE);

            if (currentCheck == CHECK_CAR_BY_VIN)
                checkCarByVin.callOnClick();
            else
                checkCarByNum.callOnClick();

            Log.d(TAG, "onActivityCreated: " + currentValue);

            carInfoContent.setText(savedInstanceState.getString(CURRENT_RESPONSE));
            edtxt.setText(savedInstanceState.getString(CURRENT_VALUE));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_car_num_btn: {
                checkCarByNum.setSelected(true);
                checkCarByVin.setSelected(false);
                currentCheck = getCheckType();
                titleTxt.setText("Проверка авто по гос. номеру");
                subTitleTxt.setText("Введите номер авто в формате А000АА00");
                break;
            }
            case R.id.check_car_vin_btn: {
                checkCarByNum.setSelected(false);
                checkCarByVin.setSelected(true);
                currentCheck = getCheckType();
                titleTxt.setText("Проверка авто по VIN коду");
                subTitleTxt.setText("Введите VIN код в формате 4f2yu08102km26251");
                break;
            }
            case R.id.btn_search: {
                currentValue = edtxt.getText().toString();
                if (getCheckType() == CHECK_CAR_BY_NUM)
                    checkAutoPresenter.getInfoByNum(edtxt.getText().toString());
                else if (getCheckType() == CHECK_CAR_BY_VIN)
                    if (edtxt.getText().toString().length() < 17)
                        Toast.makeText(getContext(), "VIN код должен состоять из 17 знаков", Toast.LENGTH_SHORT).show();
                    else
                        checkAutoPresenter.getInfoByVin(edtxt.getText().toString());
                break;
            }
        }
    }

    private int getCheckType() {
        if (checkCarByNum.isSelected())
            return CHECK_CAR_BY_NUM;
        else if (checkCarByVin.isSelected())
            return CHECK_CAR_BY_VIN;
        return -1;
    }


    @Override
    public void onCheckAutoByVin(InfoResponse infoResponse) {
        Log.d(TAG, "onCheckAutoByVin: ");
        carInfoContent.setText(infoResponse.toString());
        currentResponse = infoResponse.toString();
    }

    @Override
    public void onCheckAutoByNum(InfoResponse infoResponse) {
        Log.d(TAG, "onCheckAutoByNum: ");
        carInfoContent.setText(infoResponse.toString());
        currentResponse = infoResponse.toString();
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_CHECK, getCheckType());
        outState.putString(CURRENT_RESPONSE, currentResponse);
        outState.putString(CURRENT_VALUE, edtxt.getText().toString());
    }
}
