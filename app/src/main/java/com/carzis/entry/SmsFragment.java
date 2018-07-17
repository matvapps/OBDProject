package com.carzis.entry;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.entry.register.ActivityToSmsFragmentCallbackListener;
import com.carzis.entry.register.RegisterCallbackListener;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.util.ArrayList;

/**
 * Created by Alexandr.
 */
public class SmsFragment extends Fragment implements ActivityToSmsFragmentCallbackListener{

    private final String TAG = SmsFragment.class.getSimpleName();
    private final String SMS = "sms";

    private Button nextBtn;
    private RegisterCallbackListener callbackListener;

    private boolean isRegistration = false;

    private EditText smsCodePart1;
    private EditText smsCodePart2;
    private EditText smsCodePart3;
    private EditText smsCodePart4;

    private ArrayList<String> savedSmsParts;

    private TextView smsTextTitle;
    private TextView smsTextSubTitle;

    private KeyValueStorage keyValueStorage;

    public SmsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms_code, container, false);

        nextBtn = rootView.findViewById(R.id.next_btn);
        smsTextTitle = rootView.findViewById(R.id.sms_text);
        smsTextSubTitle = rootView.findViewById(R.id.sms_code_text);

        savedSmsParts = new ArrayList<>();
        keyValueStorage = new KeyValueStorage(getContext());

//        if (!isRegistration) {
//            smsTextTitle.setText("Введите пароль");
//            smsTextSubTitle.setText("Этот номер уже авторизован, введите пароль");
//        }

        smsCodePart1 = rootView.findViewById(R.id.sms_code_1);
        smsCodePart2 = rootView.findViewById(R.id.sms_code_2);
        smsCodePart3 = rootView.findViewById(R.id.sms_code_3);
        smsCodePart4 = rootView.findViewById(R.id.sms_code_4);

        smsCodePart1.addTextChangedListener(new GenericTextWatcher(smsCodePart1));
        smsCodePart2.addTextChangedListener(new GenericTextWatcher(smsCodePart2));
        smsCodePart3.addTextChangedListener(new GenericTextWatcher(smsCodePart3));
        smsCodePart4.addTextChangedListener(new GenericTextWatcher(smsCodePart4));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof RegisterCallbackListener)
            callbackListener = (RegisterCallbackListener) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn.setOnClickListener(v -> {
            if (callbackListener != null) {
                // TODO:
                String sms = smsCodePart1.getText().toString() +
                        smsCodePart2.getText().toString() +
                        smsCodePart3.getText().toString() +
                        smsCodePart4.getText().toString();
                if (sms.length() < 4) {
                    Toast.makeText(getContext(), "Код должен состоять из 4 цифр", Toast.LENGTH_SHORT).show();
                } else {
                    callbackListener.onLogRegFinish(isRegistration, Integer.parseInt(sms));
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((LogRegActivity) context).activityToSmsFragmentCallbackListener = this;
    }

    @Override
    public void onResume() {
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onGetSms(String message) {
//        Toast.makeText(getContext(), "smsFragment = " + message, Toast.LENGTH_SHORT).show();
        smsCodePart1.setText(message.charAt(0));
        smsCodePart2.setText(message.charAt(1));
        smsCodePart3.setText(message.charAt(2));
        smsCodePart4.setText(message.charAt(3));
    }

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equalsIgnoreCase("otp")) {
//                final String message = intent.getStringExtra("message");
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
////                your_edittext.setText(message);
//                //Do whatever you want with the code here
//            }
//        }
//    };


    public boolean isRegistration() {
        return isRegistration;
    }

    public void setRegistration(boolean registration) {
        isRegistration = registration;
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.sms_code_1:
                    if (text.length() == 1)
                        smsCodePart2.requestFocus();
                    break;
                case R.id.sms_code_2:
                    if (text.length() == 1)
                        smsCodePart3.requestFocus();
                    break;
                case R.id.sms_code_3:
                    if (text.length() == 1)
                        smsCodePart4.requestFocus();
                    break;
                case R.id.sms_code_4:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    }




}
