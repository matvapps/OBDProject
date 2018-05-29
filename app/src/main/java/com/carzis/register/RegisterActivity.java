package com.carzis.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.carzis.R;
import com.carzis.connect.ConnectActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterCallbackListener{

    private final String TAG = RegisterActivity.class.getSimpleName();

    public static final int FRAGMENT_SMS = 1;
    public static final int FRAGMENT_PHONE = 2;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

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
    public void onNextBtnClick(int fragment) {
        switch (fragment) {
            case FRAGMENT_PHONE: {
                showFragment(new SmsFragment());
                break;
            }
            case FRAGMENT_SMS: {
                ConnectActivity.start(this);
                finish();
                break;
            }
        }
    }
}
