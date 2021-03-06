package com.carzis.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.util.Utility;

/**
 * Created by Alexandr
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, LoginView{

    private EditText emailEdtxt;
    private Button sendEmailBtn;
    private Button privacyBtn;
    private ImageButton backBtn;

    private LoginPresenter loginPresenter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEdtxt = findViewById(R.id.email_edtxt);
        sendEmailBtn = findViewById(R.id.send_email_btn);
        privacyBtn = findViewById(R.id.privacy_policy_btn);
        backBtn = findViewById(R.id.back_btn);

        sendEmailBtn.setOnClickListener(this);
        privacyBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_email_btn:
                String email = emailEdtxt.getText().toString();
                if (Utility.isEmail(email)) {
                    loginPresenter.sendMailWithPassword(email);
                } else {
                    emailEdtxt.setTextColor(Color.RED);
                    Toast.makeText(this, R.string.email_isnt_correct, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.privacy_policy_btn:

                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onSendMailForRestorePassword() {
        Toast.makeText(this, "Письмо с паролем выслано на вашу почту", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateAccount() {

    }
}
