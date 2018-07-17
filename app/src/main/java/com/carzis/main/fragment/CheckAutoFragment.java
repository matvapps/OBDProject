package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.main.presenter.CheckAutoPresenter;
import com.carzis.main.view.CheckAutoView;
import com.carzis.model.AppError;
import com.carzis.model.response.NumInfoResponse;
import com.carzis.model.response.VinInfoResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;

/**
 * Created by Alexandr.
 */
public class CheckAutoFragment extends Fragment implements View.OnClickListener, CheckAutoView{

    private static final String TAG = CheckAutoFragment.class.getSimpleName();

    private View checkCarByVin;
    private View checkCarByNum;
    private View btnSearch;
    private EditText edtxt;
    private TextView carInfoContent;

    private final int CHECK_CAR_BY_NUM = 1;
    private final int CHECK_CAR_BY_VIN = 2;

    private KeyValueStorage keyValueStorage;
    private CheckAutoPresenter checkAutoPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_check_auto, container, false);

        checkCarByNum = rootView.findViewById(R.id.check_car_num_btn);
        checkCarByVin = rootView.findViewById(R.id.check_car_vin_btn);
        btnSearch = rootView.findViewById(R.id.btn_search);
        edtxt = rootView.findViewById(R.id.vin_edtxt);
        carInfoContent = rootView.findViewById(R.id.car_content_info);

        checkCarByNum.setOnClickListener(this);
        checkCarByVin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        checkCarByVin.callOnClick();

        keyValueStorage = new KeyValueStorage(getContext());
        checkAutoPresenter = new CheckAutoPresenter(keyValueStorage.getUserToken());
        checkAutoPresenter.attachView(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_car_num_btn: {
                checkCarByNum.setSelected(true);
                checkCarByVin.setSelected(false);
                break;
            }
            case R.id.check_car_vin_btn: {
                checkCarByNum.setSelected(false);
                checkCarByVin.setSelected(true);
                break;
            }
            case R.id.btn_search: {
                if (getCheckType() == CHECK_CAR_BY_NUM)
                    checkAutoPresenter.getInfoByNum(edtxt.getText().toString());
                else if (getCheckType() == CHECK_CAR_BY_VIN)
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
    public void onCheckAutoByVin(VinInfoResponse vinInfoResponse) {
        Log.d(TAG, "onCheckAutoByVin: ");

    }

    @Override
    public void onCheckAutoByNum(NumInfoResponse numInfoResponse) {
        Log.d(TAG, "onCheckAutoByNum: ");
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }
}
