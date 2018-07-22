package com.carzis.additionalscreen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.carzis.R;
import com.carzis.main.presenter.ProfilePresenter;
import com.carzis.main.view.ProfileView;
import com.carzis.model.AppError;
import com.carzis.model.response.ProfileResponse;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.CustomSpinnerAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Alexandr.
 */
public class ProfileSettingsFragment extends Fragment implements ProfileView {

    private static final String TAG = ProfileSettingsFragment.class.getSimpleName();
    private static final int GALLERY_REQUEST = 100;

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
    private ImageView userImage;

    private KeyValueStorage keyValueStorage;
    private ProfilePresenter profilePresenter;
    private ArrayAdapter<String> daySpinnerAdapter;
    private ArrayAdapter<String> monthSpinnerAdapter;
    private ArrayAdapter<String> yearSpinnerAdapter;

    private MultipartBody.Part body;


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
        userImage = rootView.findViewById(R.id.user_image);

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
                onSave();
            }
        });

        addUserImage.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        initView(rootView);

        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));

        profilePresenter = new ProfilePresenter(keyValueStorage.getUserToken());
        profilePresenter.attachView(this);
        daySpinnerAdapter = new CustomSpinnerAdapter(getContext(), getDays());
        monthSpinnerAdapter = new CustomSpinnerAdapter(getContext(), getMonths());
        yearSpinnerAdapter = new CustomSpinnerAdapter(getContext(), getYears());

        monthSpinner.setAdapter(monthSpinnerAdapter);
        daySpinner.setAdapter(daySpinnerAdapter);
        yearSpinner.setAdapter(yearSpinnerAdapter);

        profilePresenter.loadProfile();

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
        String email = emailEditText.getText().toString();

        Calendar bday = Calendar.getInstance();

        Long unixTime = 0L;
        if (name.equals(""))
            name = null;
        if (surname.equals(""))
            surname = null;
        if (email.equals(""))
            email = null;

        if (!month.equals("Месяц") && !day.equals("День") && !year.equals("Год")) {
            Log.d(TAG, "onSave: " + monthSpinner.getSelectedItemPosition());
            bday.set(Calendar.MONTH, monthSpinner.getSelectedItemPosition() - 1);
            bday.set(Calendar.DAY_OF_MONTH, daySpinner.getSelectedItemPosition());
            bday.set(Calendar.YEAR, Integer.valueOf(year));
            unixTime = bday.getTimeInMillis() / 1000L;
        }

//        if (!(phoneCodeEditText.getRawText().length() < 1)) {
//            if (!(phoneEditText.getRawText().length() < 10)) {
//                phoneStr = phoneCodeEditText.getRawText() + phoneEditText.getRawText();
//
//
//
//            } else
//                Toast.makeText(getContext(), "Введен неверный номер телефона", Toast.LENGTH_SHORT).show();
//        } else
//            Toast.makeText(getContext(), "Введен неверный код страны", Toast.LENGTH_SHORT).show();


        if (body == null) {
            profilePresenter.updateProfile(
                    keyValueStorage.getUserToken(),
                    email,
                    name,
                    surname,
                    unixTime);
        } else {
            profilePresenter.updateProfile(
                    keyValueStorage.getUserToken(),
                    email,
                    name,
                    surname,
                    unixTime,
                    body);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File file;

        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            final Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            file = new File(filePath);
            Picasso.get()
                    .load(file)
                    .centerCrop()
                    .fit()
                    .into(userImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            userImage.setPadding(0, 0, 0, 0);
                            Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();

                            //write the bytes in file
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                                fos.write(bitmapdata);
                                fos.flush();
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            body = MultipartBody.Part.createFormData("user_photo", file.getName(), reqFile);

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("ProfileSettingsFragment", "onError: " + e.getMessage());
                            showLoading(false);
                            Picasso.get()
                                    .load(R.drawable.person)
                                    .into(userImage);
                        }
                    });
        }
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {
        String firstName = profileResponse.getFirstName();
        String secondName = profileResponse.getSecondName();
        String email = profileResponse.getEmail();

        if (firstName.equals("null"))
            firstName = "";
        if (secondName.equals("null"))
            secondName = "";
        if (email.equals("null"))
            email = "";
        nameEditText.setText(firstName);
        surnameEditText.setText(secondName);
        emailEditText.setText(profileResponse.getEmail());

        String bday = profileResponse.getBirthday();
        if (bday.equals("null")) {
            bday = "";
        } else {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            try {
                cal.setTime(simpleDateFormat.parse(bday));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            daySpinner.setSelection(cal.get(Calendar.DAY_OF_MONTH));
            monthSpinner.setSelection(cal.get(Calendar.MONTH) + 1);
            yearSpinner.setSelection(getYearIndex(String.valueOf(cal.get(Calendar.YEAR))));

        }
    }

    private int getYearIndex(String year) {
        List<String> years = getYears();
        for (int i = 0; i < years.size(); i++) {
            if (year.equals(years.get(i)))
                return i;
        }
        return -1;
    }

    @Override
    public void onUpdateProfile() {
        Log.d(TAG, "onUpdateProfile: ");
        getActivity().finish();
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {

    }
}
