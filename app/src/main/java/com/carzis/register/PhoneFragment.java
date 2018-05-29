package com.carzis.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class PhoneFragment extends Fragment {

    private final String TAG = PhoneFragment.class.getSimpleName();

    private Button nextBtn;
    private RegisterCallbackListener callbackListener;

    public PhoneFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        nextBtn = rootView.findViewById(R.id.next_btn);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof RegisterCallbackListener)
            callbackListener = (RegisterCallbackListener) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn.setOnClickListener(v -> {
            if(callbackListener != null)
                callbackListener.onNextBtnClick(RegisterActivity.FRAGMENT_PHONE);
        });
    }

}
