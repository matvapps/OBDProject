
package com.carzis.prefs;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.CustomSpinner;
import com.carzis.util.custom.view.CustomWhiteSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private CustomSpinner languageSpinner;
    private CustomSpinner connectTypeSpinner;
    private CustomSpinner protocolSpinner;
    private View backBtn;
    private TextView title;
    private TextView deviceName;
    private EditText initStringEdtxt;

    private CustomWhiteSpinnerAdapter languageSpinnerAdapter;
    private CustomWhiteSpinnerAdapter connectTypeSpinnerAdapter;
    private CustomWhiteSpinnerAdapter protocolSpinnerAdapter;
    private KeyValueStorage keyValueStorage;

    public static void start(Activity activity, boolean isConnected) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        intent.putExtra("isConnected", isConnected);
        activity.startActivity(intent);
    }

    private void initView() {
        languageSpinner = findViewById(R.id.language_spinner);
        connectTypeSpinner = findViewById(R.id.connect_type_spinner);
        protocolSpinner = findViewById(R.id.protocol_spinner);
        backBtn = findViewById(R.id.back_btn);
        title = findViewById(R.id.title);
        deviceName = findViewById(R.id.device_name);
        initStringEdtxt = findViewById(R.id.init_string_edtxt);

        deviceName.setText(getLocalBluetoothName());
        title.setText("Настройки");
        backBtn.setOnClickListener(view -> finish());

        protocolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                keyValueStorage.setProtocol(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initStringEdtxt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                keyValueStorage.setInitString(initStringEdtxt.getText().toString());
                hideKeyboard(initStringEdtxt);
                return true;
            }
            return false;
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        keyValueStorage = new KeyValueStorage(this);
        boolean isConnected = getIntent().getBooleanExtra("isConnected", false);

        initView();

        languageSpinnerAdapter = new CustomWhiteSpinnerAdapter(this, getLanguages());
        connectTypeSpinnerAdapter = new CustomWhiteSpinnerAdapter(this, getConnectTypes());
        protocolSpinnerAdapter = new CustomWhiteSpinnerAdapter(this, getProtocols());


        languageSpinner.setAdapter(languageSpinnerAdapter);
        connectTypeSpinner.setAdapter(connectTypeSpinnerAdapter);
        protocolSpinner.setAdapter(protocolSpinnerAdapter);

        languageSpinner.setEnabled(false);
        languageSpinnerAdapter.setEnabled(false);
        connectTypeSpinnerAdapter.setEnabled(false);
        connectTypeSpinner.setEnabled(false);

        if (isConnected) {
            initStringEdtxt.setEnabled(false);
            protocolSpinnerAdapter.setEnabled(false);
            protocolSpinner.setEnabled(false);
        }
    }


    public List<String> getLanguages() {
        List<String> languages = new ArrayList<>();
        languages.add("Русский");
        return languages;
    }

    public List<String> getConnectTypes() {
        List<String> connectTypes = new ArrayList<>();
        connectTypes.add("Bluetooth");
        return connectTypes;
    }

    public List<String> getProtocols() {
        List<String> protocols = new ArrayList<>();
        protocols.add("Автоматическое определение протокола");
        protocols.add("SAE J1850 PWM (41.6 kbaud)");
        protocols.add("SAE J1850 VPW (10.4 kbaud)");
        protocols.add("ISO 9141-2 (5 baud init, 10.4 kbaud)");
        protocols.add("ISO 14230-4 KWP (5 baud init, 10.4 kbaud)");
        protocols.add("ISO 14230-4 KWP (fast init, 10.4 kbaud)");
        protocols.add("ISO 15765-4 CAN (11 bit ID, 500 kbaud)");
        protocols.add("ISO 15765-4 CAN (29 bit ID, 500 kbaud)");
        protocols.add("ISO 15765-4 CAN (11 bit ID, 250 kbaud)");
        protocols.add("ISO --4 CAN (29 bit ID, 250 kbaud)");
        return protocols;
    }

    public String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null)
            return "null";
        String name = mBluetoothAdapter.getName();
        if (name == null) {
            name = mBluetoothAdapter.getAddress();
        }
        return name;
    }

    private void hideKeyboard(EditText editText)
    {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}