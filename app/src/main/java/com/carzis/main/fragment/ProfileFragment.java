package com.carzis.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.main.presenter.ProfilePresenter;
import com.carzis.main.view.ProfileView;
import com.carzis.model.AppError;
import com.carzis.model.response.ProfileResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Alexandr.
 */
public class ProfileFragment extends Fragment implements ProfileView {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ImageView backgroundUserImage;
    private ImageView userImage;
    private TextView userName;
    private TextView userBDay;
    private TextView userPhone;
    private TextView userEmail;
    private Button openSettingProfileBtn;


    private ProfilePresenter profilePresenter;
    private KeyValueStorage keyValueStorage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(rootView);

        keyValueStorage = new KeyValueStorage(getContext());
        profilePresenter = new ProfilePresenter(keyValueStorage.getUserToken());
        profilePresenter.attachView(this);

        profilePresenter.loadProfile();
        return rootView;
    }

    private void initView(View rootView) {
        openSettingProfileBtn = rootView.findViewById(R.id.btn_settings_profile);
        openSettingProfileBtn.setOnClickListener(
                view -> AdditionalActivity.start(getActivity(), AdditionalActivity.SETTINGS_PROFILE_FRAGMENT));

        backgroundUserImage = rootView.findViewById(R.id.big_user_photo);
        userImage = rootView.findViewById(R.id.user_image);
        userName = rootView.findViewById(R.id.user_name);
        userBDay = rootView.findViewById(R.id.user_bday);
        userPhone = rootView.findViewById(R.id.user_phone);
        userEmail = rootView.findViewById(R.id.user_email);
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {
        String firstName = profileResponse.getFirstName();
        String secondName = profileResponse.getSecondName();
        String email = profileResponse.getEmail();

        if (firstName.equals("null"))
            firstName = "";
        if (secondName.equals("null"))
            secondName = "";
        if (email.equals("null"))
            email = "";

        userName.setText(String.format("%s %s", firstName, secondName));
        userPhone.setText(profileResponse.getPhone());
        userEmail.setText(email);

        String bday = profileResponse.getBirthday();
        if (bday.equals("null")) {
            bday = "";
        } else {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault());
            try {
                cal.setTime(simpleDateFormat.parse(bday));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat bdayFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            bday = bdayFormat.format(cal.getTime());
            Log.d(TAG, "onGetProfile: " + bday);

        }


        userBDay.setText(bday);


    }

    @Override
    public void onResume() {
        super.onResume();
        profilePresenter.loadProfile();
    }

    @Override
    public void onUpdateProfile() {

    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }
}
