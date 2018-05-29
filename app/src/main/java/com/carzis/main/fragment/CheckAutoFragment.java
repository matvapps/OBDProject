package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class CheckAutoFragment extends Fragment {

    private static final String TAG = CheckAutoFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_check_auto, container, false);



        return rootView;
    }

}
