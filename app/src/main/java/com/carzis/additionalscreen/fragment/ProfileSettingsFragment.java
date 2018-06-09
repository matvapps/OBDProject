package com.carzis.additionalscreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.Utility;
import com.carzis.util.custom.view.CustomSpinnerAdapter;
import com.github.reinaldoarrosi.maskededittext.MaskedEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class ProfileSettingsFragment extends Fragment {

    private static final String TAG = ProfileSettingsFragment.class.getSimpleName();

    private EditText nameEditText;
    private EditText surnameEditText;
    private MaskedEditText phoneCodeEditText;
    private MaskedEditText phoneEditText;
    private EditText emailEditText;
    private View policyChbxContainer;
    private ImageView policyChbx;
    private Button saveBtn;
    private View addUserImage;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;

    private KeyValueStorage keyValueStorage;
    private ArrayAdapter<String> daySpinnerAdapter;
    private ArrayAdapter<String> monthSpinnerAdapter;
    private ArrayAdapter<String> yearSpinnerAdapter;


    private void initView(View rootView) {

        nameEditText = rootView.findViewById(R.id.name);
        surnameEditText = rootView.findViewById(R.id.surname);
        phoneCodeEditText = rootView.findViewById(R.id.phone_country_code);
        phoneEditText = rootView.findViewById(R.id.phone_num);
        emailEditText = rootView.findViewById(R.id.email_edtxt);
        policyChbxContainer = rootView.findViewById(R.id.policy_chbx_container);
        policyChbx = rootView.findViewById(R.id.policy_chbx);
        saveBtn = rootView.findViewById(R.id.save_btn);
        addUserImage = rootView.findViewById(R.id.upload_photo);
        daySpinner = rootView.findViewById(R.id.day_spinner);
        monthSpinner = rootView.findViewById(R.id.month_spinner);
        yearSpinner = rootView.findViewById(R.id.year_spinner);

        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        policyChbx.setVisibility(View.INVISIBLE);
        policyChbx.setOnClickListener(view -> {
            if (policyChbx.getVisibility() == View.VISIBLE)
                policyChbx.setVisibility(View.INVISIBLE);
            else
                policyChbx.setVisibility(View.VISIBLE);
        });
        policyChbxContainer.setOnClickListener(view -> {
            if (policyChbx.getVisibility() == View.VISIBLE)
                policyChbx.setVisibility(View.INVISIBLE);
            else
                policyChbx.setVisibility(View.VISIBLE);
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        initView(rootView);

        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));

        daySpinnerAdapter = new CustomSpinnerAdapter(getContext(), getDays());
        monthSpinnerAdapter = new CustomSpinnerAdapter(getContext(), getMonths());
        yearSpinnerAdapter = new CustomSpinnerAdapter(getContext(), getYears());

        monthSpinner.setAdapter(monthSpinnerAdapter);
        daySpinner.setAdapter(daySpinnerAdapter);
        yearSpinner.setAdapter(yearSpinnerAdapter);


//        monthSpinnerAdapter.setItems(getMonths());
//        daySpinnerAdapter.setItems(getDays());
//        yearSpinnerAdapter.setItems(getYears());
        return rootView;
    }

    private List<String> getMonths() {
        List<String> monthsList = new ArrayList<>();
        monthsList.add("Месяц");

        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());

            monthsList.add(month_name);
        }

        return monthsList;
    }

    private List<String> getDays() {
        List<String> daysList = new ArrayList<>();
        daysList.add("День");

        for (int i = 1; i < 32; i++) {
            daysList.add(String.valueOf(i));
        }

        return daysList;
    }

    private List<String> getYears() {
        List<String> yearList = new ArrayList<>();
        yearList.add("Год");

        int startYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 0; i < 101; i++) {
            startYear--;
            yearList.add(String.valueOf(startYear));
        }

        return yearList;
    }

    private void onSave() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String month = monthSpinnerAdapter.getItem(monthSpinner.getSelectedItemPosition());
        String day = daySpinnerAdapter.getItem(daySpinner.getSelectedItemPosition());
        String year = yearSpinnerAdapter.getItem(yearSpinner.getSelectedItemPosition());

        String phoneCode = phoneCodeEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        String email = emailEditText.getText().toString();

        if (!name.isEmpty() && !surname.isEmpty()) {
            if (!month.equals("Месяц") && !day.equals("День") && !year.equals("Год")) {

                if (!phoneCode.isEmpty() && !phone.isEmpty()) {

                    if (!email.isEmpty()) {

                        if (new Utility.EmailValidator().validate(email)) {

                        } else {
                            Toast.makeText(getContext(), "Неверно введен email", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Заполните поле email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Заполните ваш номер телефона", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Заполните пожалуйста дату вашего рождения", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Введите ваше имя", Toast.LENGTH_SHORT).show();
        }


    }

}
