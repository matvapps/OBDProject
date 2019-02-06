package com.carzis.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.main.MainActivity;
import com.carzis.util.Utility;

/**
 * Created by Alexandr
 */
public class CreateAccountActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private EditText emailEdtxt;
    private EditText passwordEdtxt;
    private EditText repPasswordEdtxt;
    private ImageView passVisibilityBtn;
    private ImageView repPassVisibilityBtn;
    private Button createAccountBtn;
    private ImageButton backBtn;

    private LoginPresenter loginPresenter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateAccountActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEdtxt = findViewById(R.id.email_edtxt);
        passwordEdtxt = findViewById(R.id.password_edtxt);
        repPasswordEdtxt = findViewById(R.id.repeat_password_edtxt);
        passVisibilityBtn = findViewById(R.id.password_visibility);
        repPassVisibilityBtn = findViewById(R.id.repeat_password_visibility);
        createAccountBtn = findViewById(R.id.create_account_btn);
        backBtn = findViewById(R.id.back_btn);

        passVisibilityBtn.setOnClickListener(this);
        repPassVisibilityBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password_visibility:
                if (!passwordEdtxt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    passwordEdtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passVisibilityBtn.setImageResource(R.drawable.ic_visibility);
                } else {
                    passVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
                    passwordEdtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                passwordEdtxt.setSelection(passwordEdtxt.length());
                break;
            case R.id.repeat_password_visibility:
                if (!repPasswordEdtxt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    repPasswordEdtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    repPassVisibilityBtn.setImageResource(R.drawable.ic_visibility);
                } else {
                    repPassVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
                    repPasswordEdtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                repPasswordEdtxt.setSelection(repPasswordEdtxt.length());
                break;
            case R.id.create_account_btn:
                String email = emailEdtxt.getText().toString();
                String password = passwordEdtxt.getText().toString();
                String repPassword = repPasswordEdtxt.getText().toString();

                if (Utility.isEmail(email)) {
                    if (password.length() >= 6) {
                        if (password.equals(repPassword)) {
                            loginPresenter.createAccount(email, password);
                        } else {
                            passwordEdtxt.setText("");
                            repPasswordEdtxt.setText("");
                            Toast.makeText(this, R.string.passwords_not_equals, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        passwordEdtxt.setText("");
                        repPasswordEdtxt.setText("");
                        Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    emailEdtxt.setTextColor(Color.RED);
                    Toast.makeText(this, R.string.email_isnt_correct, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void onLogin() {
//        MainActivity.start(this);
//        finish();
    }

    @Override
    public void onSendMailForRestorePassword() {
        Toast.makeText(this, "Сообщение с паролем было отправлено на ваш email", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateAccount() {
        MainActivity.start(this);
        finish();
    }
}
