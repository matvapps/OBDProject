package com.carzis.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carzis.R;
import com.carzis.main.MainActivity;
import com.carzis.main.adapter.TroubleCodesAdapter;
import com.carzis.main.listener.ActivityToTroublesCallbackListener;
import com.carzis.main.listener.TroublesToActivityCallbackListener;
import com.carzis.model.TroubleItem;
import com.carzis.util.custom.TroubleTypeBtn;

/**
 * Created by Alexandr.
 */
public class TroubleCodesFragment extends Fragment implements ActivityToTroublesCallbackListener,
        View.OnClickListener {

    private static final String TAG = TroubleCodesFragment.class.getSimpleName();


    private TroublesToActivityCallbackListener troublesToActivityCallbackListener;

    private TroubleTypeBtn carEngineBtn;
    private TroubleTypeBtn carChasisBtn;
    private TroubleTypeBtn carBodyBtn;
    private TroubleTypeBtn carNetworkBtn;
    private RecyclerView troubleCodesList;

    private TroubleCodesAdapter troubleCodesAdapter;

    private final String POWER_TRAIN_CODE = "P";
    private final String CHASIS_CODE = "C";
    private final String BODY_CODE = "B";
    private final String NETWORK_CODE = "U";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trouble_codes, container, false);

        troublesToActivityCallbackListener.getTroubleCodes();

        troubleCodesList = rootView.findViewById(R.id.trouble_codes_list);
        carEngineBtn = rootView.findViewById(R.id.car_engine);
        carChasisBtn = rootView.findViewById(R.id.car_chassis);
        carBodyBtn = rootView.findViewById(R.id.car_body);
        carNetworkBtn = rootView.findViewById(R.id.car_electronics);

        troubleCodesAdapter = new TroubleCodesAdapter();
        troubleCodesList.setAdapter(troubleCodesAdapter);

        carEngineBtn.setOnClickListener(this);
        carChasisBtn.setOnClickListener(this);
        carBodyBtn.setOnClickListener(this);
        carNetworkBtn.setOnClickListener(this);

        troubleCodesAdapter.setTroubleType(POWER_TRAIN_CODE);
        carEngineBtn.callOnClick();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        troublesToActivityCallbackListener = (TroublesToActivityCallbackListener) context;
        ((MainActivity) context).activityToTroublesCallbackListener = this;

    }

    @Override
    public void onPassTroubleCode(String troubleCode, String shortDesc) {
        Log.d(TAG, "onPassTroubleCode: " + troubleCode);

        if (!troubleCodesAdapter.contains(troubleCode)) {
            troubleCodesAdapter.addItem(new TroubleItem(troubleCode, shortDesc));
        }

//        if(!contentTxt.contains(troubleCode)) {
//            contentTxt += troubleCode + "\n";
//            content.setText(contentTxt);
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_engine:
                troubleCodesAdapter.setTroubleType(POWER_TRAIN_CODE);

                carEngineBtn.setSelected(true);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_chassis:
                troubleCodesAdapter.setTroubleType(CHASIS_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(true);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_body:
                troubleCodesAdapter.setTroubleType(BODY_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(true);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_electronics:
                troubleCodesAdapter.setTroubleType(NETWORK_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(true);
                break;
        }
    }
}
