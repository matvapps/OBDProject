package com.carzis.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.carzis.R;
import com.carzis.entry.auth.AuthPresenter;
import com.carzis.entry.auth.AuthView;
import com.carzis.entry.register.RegisterCallbackListener;
import com.carzis.entry.register.RegisterPresenter;
import com.carzis.entry.register.RegisterView;
import com.carzis.main.MainActivity;
import com.carzis.model.AppError;
import com.carzis.util.AndroidUtility;

public class LogRegActivity extends AppCompatActivity implements RegisterCallbackListener,
        RegisterView, AuthView {

    private final String TAG = LogRegActivity.class.getSimpleName();

    public static final int FRAGMENT_SMS = 1;
    public static final int FRAGMENT_PHONE = 2;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LogRegActivity.class);
        activity.startActivity(intent);
    }

    private RegisterPresenter registerPresenter;
    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        showFragment(new PhoneFragment());
    }

    private void showFragment(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onInputPhoneFinish(String phone) {
        registerPresenter
                .register(phone,
                        AndroidUtility.getDeviceId(this),
                        AndroidUtility.getDeviceName());
    }

    @Override
    public void onLogRegFinish(boolean isRegistration) {
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onAuth() {

    }

    @Override
    public void onRegister() {
        showFragment(new SmsFragment());
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }
}
