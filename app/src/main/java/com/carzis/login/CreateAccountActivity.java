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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseActivity;
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


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateAccountActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_create_account);

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password_visibility:
                if (passVisibilityBtn.getDrawable()
                        == ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)) {
                    passVisibilityBtn.setImageResource(R.drawable.ic_visibility);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    passVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.repeat_password_visibility:
                if (repPassVisibilityBtn.getDrawable()
                        == ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)) {
                    repPassVisibilityBtn.setImageResource(R.drawable.ic_visibility);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    repPassVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
                    passwordEdtxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.create_account_btn:
                String email = emailEdtxt.getText().toString();
                String password = passwordEdtxt.getText().toString();
                String repPassword = repPasswordEdtxt.getText().toString();

                if (Utility.isEmail(email)) {
                    if (password.length() >= 6) {
                        if (password.equals(repPassword)) {
                            // TODO: Create account
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

    }

    @Override
    public void onSendMailForRestorePassword() {

    }

    @Override
    public void onCreateAccount() {

    }
}
