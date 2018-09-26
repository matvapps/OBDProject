package com.carzis.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.util.Utility;

/**
 * Created by Alexandr
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private EditText emailEdtxt;
    private EditText passwordEdtxt;
    private ImageView passwordVisibilityBtn;
    private Button forgetPasswordBtn;
    private Button createNewAccountBtn;
    private Button loginBtn;
    private Button privacyPolicyBtn;

    private LoginPresenter loginPresenter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_login);

        emailEdtxt = findViewById(R.id.email_edtxt);
        passwordEdtxt = findViewById(R.id.password_edtxt);
        passwordVisibilityBtn = findViewById(R.id.password_visibility);
        forgetPasswordBtn = findViewById(R.id.forget_password_btn);
        createNewAccountBtn = findViewById(R.id.create_account_btn);
        loginBtn = findViewById(R.id.login_btn);
        privacyPolicyBtn = findViewById(R.id.privacy_policy_btn);

        loginPresenter = new LoginPresenter();

        passwordVisibilityBtn.setOnClickListener(this);
        forgetPasswordBtn.setOnClickListener(this);
        createNewAccountBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        privacyPolicyBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password_visibility:
                if (passwordVisibilityBtn.getDrawable()
                        == ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)) {
                    passwordVisibilityBtn.setImageResource(R.drawable.ic_visibility);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    passwordVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.forget_password_btn:
                ForgetPasswordActivity.start(this);
                break;
            case R.id.create_account_btn:
                CreateAccountActivity.start(this);
                break;
            case R.id.login_btn:
                String email = emailEdtxt.getText().toString();
                String password = passwordEdtxt.getText().toString();

                if (Utility.isEmail(email)) {
                    if (password.length() >= 6) {
                        loginPresenter.login(email, password);
                    } else {
                        passwordEdtxt.setText("");
                        Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    emailEdtxt.setTextColor(Color.RED);
                    Toast.makeText(this, R.string.email_isnt_correct, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.privacy_policy_btn:
                // TODO:
                break;
        }
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onSendMailForRestorePassword() {

    }

    @Override
    public void onCreateAccount() {

    }
}
