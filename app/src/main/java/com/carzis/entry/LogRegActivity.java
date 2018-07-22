package com.carzis.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.entry.auth.AuthPresenter;
import com.carzis.entry.auth.AuthView;
import com.carzis.entry.register.ActivityToSmsFragmentCallbackListener;
import com.carzis.entry.register.RegisterCallbackListener;
import com.carzis.entry.register.RegisterPresenter;
import com.carzis.entry.register.RegisterView;
import com.carzis.main.MainActivity;
import com.carzis.model.AppError;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.AndroidUtility;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

public class LogRegActivity extends AppCompatActivity implements RegisterCallbackListener,
        RegisterView, AuthView {

    private final String TAG = LogRegActivity.class.getSimpleName();

    public static final int FRAGMENT_SMS = 1;
    public static final int FRAGMENT_PHONE = 2;

    private static final String FRAGMENT = "fragment";

    private static int currentFragment = 2;
    public ActivityToSmsFragmentCallbackListener activityToSmsFragmentCallbackListener;


    private String phoneCode;
    private String phone;
    private boolean isRegister = true;



    private RegisterPresenter registerPresenter;
    private AuthPresenter authPresenter;
    private SmsVerifyCatcher smsVerifyCatcher;
    private KeyValueStorage keyValueStorage;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LogRegActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT, currentFragment);
        outState.putBoolean("ISREGISTER", isRegister);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(FRAGMENT);
            isRegister = savedInstanceState.getBoolean("ISREGISTER");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        keyValueStorage = new KeyValueStorage(this);
        if (!keyValueStorage.getUserToken().equals(""))
            startMain(keyValueStorage.getUserToken());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter();
        authPresenter = new AuthPresenter();

        registerPresenter.attachView(this);
        authPresenter.attachView(this);

        smsVerifyCatcher = new SmsVerifyCatcher(this,
                message -> {
                    if (activityToSmsFragmentCallbackListener != null)
                        activityToSmsFragmentCallbackListener.onGetSms(message);
                });
//        smsVerifyCatcher.setPhoneNumberFilter("932737847");
        smsVerifyCatcher.setPhoneNumberFilter("Webservis");

        if (savedInstanceState != null) {
            switch (currentFragment) {
                case FRAGMENT_PHONE: {
                    showFragment(new PhoneFragment());
                    break;
                }
                case FRAGMENT_SMS: {
                    SmsFragment smsFragment = new SmsFragment();
                    smsFragment.setRegistration(isRegister);
                    showFragment(smsFragment);
                    break;
                }
            }
        } else {
            showFragment(new PhoneFragment());
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(SmsFragment.class.getSimpleName()) != null) {
            currentFragment = FRAGMENT_PHONE;
        }
        super.onBackPressed();
    }

    private void showFragment(Fragment fragment) {
        if (fragment instanceof PhoneFragment)
            currentFragment = FRAGMENT_PHONE;
        else if (fragment instanceof SmsFragment)
            currentFragment = FRAGMENT_SMS;

        String TAG = fragment.getClass().getSimpleName();
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        if (frag == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.addToBackStack(TAG);
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            // fragment already added
        }
    }

    @Override
    public void onInputPhoneFinish(String phone) {
        this.phone = phone;
        Log.d(TAG, phone + " " + AndroidUtility.getDeviceId(this) + " " + AndroidUtility.getDeviceName());
        registerPresenter
                .register(phone,
                        AndroidUtility.getDeviceId(this),
                        AndroidUtility.getDeviceName());
    }

    @Override
    public void onLogRegFinish(boolean isRegistration, Integer code) {
        if (isRegistration) {
            Log.d("RegisterPresenter", "onLogRegFinish: " + phone + " " + code);
            registerPresenter
                    .confirmRegister(phone, code);
        } else {
//            Toast.makeText(this, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onLogRegFinish: " + phone + " " + code + " ");
            Log.d(TAG, "onLogRegFinish: " + AndroidUtility.getDeviceId(this) + " " + AndroidUtility.getDeviceName());
            authPresenter
                    .auth(phone,
                            code,
                            AndroidUtility.getDeviceId(this),
                            AndroidUtility.getDeviceName());
        }
    }


    @Override
    public void onRegister() {
        SmsFragment smsFragment = new SmsFragment();
        isRegister = true;
        smsFragment.setRegistration(isRegister);
        showFragment(smsFragment);
    }

    @Override
    public void onConfirmRegister(String token) {
        startMain(token);
    }

    @Override
    public void onAuth(String token) {
        startMain(token);
    }

    private void startMain(String token) {
        keyValueStorage.setUserToken(token);
        MainActivity.start(LogRegActivity.this);
        finish();
    }

    @Override
    public void onPhoneExisted(String phone) {
        registerPresenter.resendCode(phone);
        Log.d(TAG, "onPhoneExisted: " + phone);
        this.phone = phone;
        isRegister = false;
        SmsFragment smsFragment = new SmsFragment();
        smsFragment.setRegistration(isRegister);
        showFragment(smsFragment);
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {
        switch (appError) {
            case AUTH_USER_ERROR:
                Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                break;
            case REGISTER_USER_ERROR:
                break;
            case REGISTER_USER_DEVICE_ID_EXIST_ERROR:
//                Toast.makeText(this, "Устройство с таким id уже зарегистрировано", Toast.LENGTH_SHORT).show();
                break;
        }
//        Toast.makeText(this, appError.value + "", Toast.LENGTH_SHORT).show();
    }
}
