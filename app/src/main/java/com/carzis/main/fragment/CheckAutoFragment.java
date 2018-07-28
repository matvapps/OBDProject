package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.carzis.R;
import com.carzis.base.BaseFragment;
import com.carzis.main.presenter.CheckAutoPresenter;
import com.carzis.main.view.CheckAutoView;
import com.carzis.model.response.InfoResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.github.mmin18.widget.RealtimeBlurView;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class CheckAutoFragment extends BaseFragment implements View.OnClickListener, CheckAutoView, PurchasesUpdatedListener {

    private static final String TAG = CheckAutoFragment.class.getSimpleName();

    private View checkCarByVin;
    private View checkCarByNum;
    private View btnSearch;
    private EditText edtxt;
    private TextView carInfoContent;
    private TextView titleTxt;
    private TextView subTitleTxt;
    private RealtimeBlurView blurView;

    private final int CHECK_CAR_BY_NUM = 1;
    private final int CHECK_CAR_BY_VIN = 2;

    private final String USE_BLUR = "use_blur";
    private final String CURRENT_CHECK = "current_check";
    private final String CURRENT_VALUE = "current_value";
    private final String CURRENT_RESPONSE = "current_response";

    private BillingClient mBillingClient;
    private KeyValueStorage keyValueStorage;
    private CheckAutoPresenter checkAutoPresenter;

    private String currentValue;
    private String currentResponse;
    private int currentCheck;
    private boolean useBlur = true;

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
        blurView = rootView.findViewById(R.id.blur_image);

        checkCarByNum.setOnClickListener(this);
        checkCarByVin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        checkCarByVin.callOnClick();

        keyValueStorage = new KeyValueStorage(getContext());
        checkAutoPresenter = new CheckAutoPresenter(keyValueStorage.getUserToken());
        checkAutoPresenter.attachView(this);

        mBillingClient = BillingClient.newBuilder(getContext()).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {


                    // The billing client is ready. You can query purchases here.
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


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
            useBlur = savedInstanceState.getBoolean(USE_BLUR);

            if (useBlur) {
                blurView.setVisibility(View.VISIBLE);
            } else {
                blurView.setVisibility(View.GONE);
            }
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
        currentResponse = infoResponse.toString();
        currentResponse = currentResponse.replaceAll("\\[", "");
        currentResponse = currentResponse.replaceAll("]", "");
        carInfoContent.setText(currentResponse);
        blurView.setVisibility(View.VISIBLE);

        if (infoResponse.getHistory() == null) {
            Toast.makeText(getContext(), R.string.no_info_about_car, Toast.LENGTH_SHORT).show();
        } else {
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSku("com.carzis.product.autocheck")
                    .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                    .build();
            mBillingClient.launchBillingFlow(getActivity(), flowParams);
        }
    }

    @Override
    public void onCheckAutoByNum(InfoResponse infoResponse) {
        Log.d(TAG, "onCheckAutoByNum: ");
        currentResponse = infoResponse.toString();
        currentResponse = currentResponse.replaceAll("\\[", "");
        currentResponse = currentResponse.replaceAll("]", "");
        carInfoContent.setText(currentResponse);
        blurView.setVisibility(View.VISIBLE);

        if (infoResponse.getHistory() == null) {
            Toast.makeText(getContext(), R.string.no_info_about_car, Toast.LENGTH_SHORT).show();
        } else {
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSku("com.carzis.product.autocheck")
                    .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                    .build();
            mBillingClient.launchBillingFlow(getActivity(), flowParams);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        useBlur = blurView.getVisibility() == View.VISIBLE;

        outState.putInt(CURRENT_CHECK, getCheckType());
        outState.putString(CURRENT_RESPONSE, currentResponse);
        outState.putString(CURRENT_VALUE, edtxt.getText().toString());
        outState.putBoolean(USE_BLUR, useBlur);
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }

        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        }
    }

    private void handlePurchase(Purchase purchase) {
        Log.d(TAG, "handlePurchase: " + purchase.getOrderId());
        if (purchase.getSku().equals("com.carzis.product.autocheck")) {

            mBillingClient.consumeAsync(purchase.getPurchaseToken(), (responseCode, purchaseToken) -> {
                blurView.setVisibility(View.GONE);
            });

        }
    }

}
