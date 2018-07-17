package com.carzis.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.entry.register.RegisterCallbackListener;

import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * Created by Alexandr.
 */
public class PhoneFragment extends Fragment {

    private final String TAG = PhoneFragment.class.getSimpleName();

    private Button nextBtn;
    private RegisterCallbackListener callbackListener;
    private MaskedEditText coutryCode;
    private MaskedEditText phone;



    public PhoneFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        nextBtn = rootView.findViewById(R.id.next_btn);
        coutryCode = rootView.findViewById(R.id.phone_country_code);
        phone = rootView.findViewById(R.id.phone_number);

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
            if(callbackListener != null) {
                if (!(coutryCode.getRawText().length() < 1)) {
                    if (!(phone.getRawText().length() < 10)) {
                        String phoneStr = coutryCode.getRawText() + phone.getRawText();
                        callbackListener.onInputPhoneFinish(phoneStr);
                    } else
                        Toast.makeText(getContext(), "Введен неверный номер телефона", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Введен неверный код страны", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
