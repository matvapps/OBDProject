package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;

/**
 * Created by Alexandr.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private Button openSettingProfileBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        openSettingProfileBtn = rootView.findViewById(R.id.btn_settings_profile);
        openSettingProfileBtn.setOnClickListener(
                view -> AdditionalActivity.start(getActivity(), AdditionalActivity.SETTINGS_PROFILE_FRAGMENT));


        return rootView;
    }

}
