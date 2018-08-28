package com.carzis.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.additionalscreen.AdditionalActivity;
import com.carzis.base.BaseActivity;
import com.carzis.connect.BluetoothService;
import com.carzis.connect.ConnectActivity;
import com.carzis.dialoglist.DialogListActivity;
import com.carzis.history.HistoryPresenter;
import com.carzis.history.HistoryView;
import com.carzis.main.fragment.CheckAutoFragment;
import com.carzis.main.fragment.DashboardFragment;
import com.carzis.main.fragment.MyCarsFragment;
import com.carzis.main.fragment.ProfileFragment;
import com.carzis.main.fragment.TroubleCodesFragment;
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.listener.ActivityToTroublesCallbackListener;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.main.listener.TroublesToActivityCallbackListener;
import com.carzis.main.presenter.CarPresenter;
import com.carzis.main.view.MyCarsView;
import com.carzis.model.Car;
import com.carzis.model.CarMetric;
import com.carzis.model.HistoryItem;
import com.carzis.obd.PID;
import com.carzis.obd.OBDReader;
import com.carzis.obd.OnReceiveDataListener;
import com.carzis.obd.OnReceiveFaultCodeListener;
import com.carzis.prefs.SettingsActivity;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.Utility;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.carzis.dialoglist.DialogListActivity.DIALOG_LIST_ACTIVITY_CODE;
import static com.carzis.dialoglist.DialogListActivity.STRING_EXTRA;

public class MainActivity extends BaseActivity implements DashboardToActivityCallbackListener,
        TroublesToActivityCallbackListener, OnReceiveDataListener, OnReceiveFaultCodeListener, MyCarsView, HistoryView {

    private final String TAG = MainActivity.class.getSimpleName();

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final int REQUEST_CONNECT_TO_DEVICE = 8;
    private static final String FRAGMENT = "fragment";

    private String currentFragment = "";

    private View movingContainer;
    private ImageView content;
    private TextView timeText;
    private ImageButton menuBtn;
    private View menuView;
    private View addView;
    private View addCarView;
    private View contentBody;

    private Button connectToBtMenuBtn;
    private Button dashboardMenuBtn;
    private Button errorListMenuBtn;
    private Button myCarsMenuBtn;
    private Button checkCarMenuBtn;
    private Button profileMenuBtn;
    private Button settingsMenuBtn;
    private Button feedbackBtn;
    private SwitchCompat useAddingToHistorySwitch;

    private HistoryPresenter historyPresenter;
    private LocalRepository localRepository;
    private KeyValueStorage keyValueStorage;
    private CarPresenter carPresenter;
    private OBDReader obdReader;

    public ActivityToDashboardCallbackListener activityToDashboardCallbackListener;
    public ActivityToTroublesCallbackListener activityToTroublesCallbackListener;

    private List<Car> userCars = new ArrayList<>();

    private String devicename = null;
    private String deviceadress = null;
    private String deviceprotocol = null;
    private String carName = null;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
//        intent.putExtra(ConnectActivity.EXTRA_DEVICE_ADDRESS, deviceAddress);
//        intent.putExtra(ConnectActivity.EXTRA_DEVICE_NAME, deviceName);
        activity.startActivity(intent);
    }

    private void initView() {

        content = findViewById(R.id.content);
        contentBody = findViewById(R.id.content_body);
        menuView = findViewById(R.id.menu);
        addView = findViewById(R.id.add_btn);
        timeText = findViewById(R.id.time_text_view);
        menuBtn = findViewById(R.id.menu_btn);
        addCarView = findViewById(R.id.add_car_view);
        movingContainer = findViewById(R.id.moving_container);

        initMenu();

        addView.setOnClickListener(view -> activityToDashboardCallbackListener.onAddNewDevice(obdReader.getSupportedPids()));
        addCarView.setOnClickListener(view -> AdditionalActivity.start(MainActivity.this, AdditionalActivity.ADD_CAR_FRAGMENT));

        menuBtn.setOnClickListener(view -> {
            if (menuView.getVisibility() == View.VISIBLE)
                hideMenu();
            else
                showMenu();
        });
        contentBody.setOnClickListener(view -> {
            if (menuView.getVisibility() == View.VISIBLE)
                hideMenu();
        });


        startTimeThread();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getString(FRAGMENT);
            switch (currentFragment) {
                case "DashboardFragment": {
                    dashboardMenuBtn.callOnClick();
                    break;
                }
                case "CheckAutoFragment": {
                    checkCarMenuBtn.callOnClick();
                    break;
                }
                case "MyCarsFragment": {
                    myCarsMenuBtn.callOnClick();
                    break;
                }
                case "ProfileFragment": {
                    profileMenuBtn.callOnClick();
                    break;
                }
                case "TroubleCodesFragment": {
                    errorListMenuBtn.callOnClick();
                    break;
                }

            }
        } else {
            dashboardMenuBtn.callOnClick();
        }

//        deviceadress = getIntent().getStringExtra(ConnectActivity.EXTRA_DEVICE_ADDRESS);
//        devicename = getIntent().getStringExtra(ConnectActivity.EXTRA_DEVICE_NAME);

        localRepository = new LocalRepository(this);
        keyValueStorage = new KeyValueStorage(this);
        obdReader = new OBDReader(this);
        carPresenter = new CarPresenter(keyValueStorage.getUserToken());
        historyPresenter = new HistoryPresenter(keyValueStorage.getUserToken());
        historyPresenter.attachView(this);
        carPresenter.attachView(this);

//        obdReader.connectDevice(deviceadress, devicename);
        obdReader.setOnReceiveFaultCodeListener(this);
        obdReader.setOnReceiveDataListener(this);


//        localRepository = new LocalRepository(this);
//        keyValueStorage = new KeyValueStorage(this);
//
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
////        troubleCodes = new TroubleCodes();
//        initializeCommands
//                = new String[]{"ATZ", "ATL0", "ATE1", "ATH1", "ATAT1", "ATSTFF", "ATI", "ATDP", "ATSP0", "ATSP0"};
//
//        commandslist.add(VOLTAGE);
//        commandslist.add(ENGINE_LOAD);
//        commandslist.add(ENGINE_COOLANT_TEMP);
//        commandslist.add(SH_TERM_FUEL_TRIM_1);
//        commandslist.add(LN_TERM_FUEL_PERCENT_TRIM_1);
//        commandslist.add(SH_TERM_FUEL_TRIM_2);
//        commandslist.add(LN_TERM_FUEL_PERCENT_TRIM_2);
//        commandslist.add(FUEL_PRESSURE);
//        commandslist.add(INTAKE_MAN_PRESSURE);
//        commandslist.add(ENGINE_RPM);
//        commandslist.add(VEHICLE_SPEED);
//        commandslist.add(TIMING_ADVANCE);
//        commandslist.add(INTAKE_AIR_TEMP);
//        commandslist.add(MAF_AIR_FLOW);
//        commandslist.add(THROTTLE_POSITION);
//        commandslist.add(OXY_SENS_VOLT_B_1_SENS_1);
//        commandslist.add(OXY_SENS_VOLT_B_1_SENS_2);
//        commandslist.add(OXY_SENS_VOLT_B_1_SENS_3);
//        commandslist.add(OXY_SENS_VOLT_B_1_SENS_4);
//        commandslist.add(OXY_SENS_VOLT_B_2_SENS_1);
//        commandslist.add(OXY_SENS_VOLT_B_2_SENS_2);
//        commandslist.add(OXY_SENS_VOLT_B_2_SENS_3);
//        commandslist.add(OXY_SENS_VOLT_B_2_SENS_4);
//        commandslist.add(FUEL_RAIL_PRESSURE);
//        commandslist.add(FUEL_RAIL_PRESSURE_DIESEL);
//        commandslist.add(COMMANDED_EGR);
//        commandslist.add(FUEL_LEVEL);
//        commandslist.add(BAROMETRIC_PRESSURE);
//        commandslist.add(CATALYST_TEMP_B1S1);
//        commandslist.add(CATALYST_TEMP_B2S1);
//        commandslist.add(CATALYST_TEMP_B1S2);
//        commandslist.add(CATALYST_TEMP_B2S2);
//        commandslist.add(THROTTLE_POS_2);
//        commandslist.add(ENGINE_OIL_TEMP);
//
//
//        commandslist.add(CONT_MODULE_VOLT);
//        commandslist.add(AMBIENT_AIR_TEMP);
//        commandslist.add(STATUS_DTC);
//        commandslist.add(OBD_STANDARDS);
//        commandslist.add(PIDS_SUPPORTED);
//
//
//        whichCommand = 0;
//
//        // Initialize the BluetoothChatService to perform bluetooth connections
//        bluetoothService = new BluetoothService(this, mBtHandler);
//
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//        } else {
//            if (bluetoothService != null) {
//                if (bluetoothService.getState() == BluetoothService.STATE_NONE) {
//                    bluetoothService.start();
//                }
//            }
//        }
//
//
//        connectDevice(deviceadress, devicename);
    }

    private void startTimeThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String timeString = timeFormat.format(calendar.getTime());
                            timeText.setText(timeString);
//
//                            if (useAddingToHistorySwitch.isChecked() && carName != null) {
//                                historyPresenter.addMetric(getCarIdByName(carName), "010A", calendar.get(Calendar.SECOND) + "");
//                            }

                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (menuView.getVisibility() == View.VISIBLE) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.are_your_want_exit);
                alertDialogBuilder.setPositiveButton(R.string.yes,
                        (arg0, arg1) -> exit());

                alertDialogBuilder.setNegativeButton(R.string.no,
                        (arg0, arg1) -> {

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                showMenu();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (obdReader.getBluetoothService() != null)
            obdReader.getBluetoothService().stop();
//            wl.release();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void showFragment(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        currentFragment = TAG;

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
        } else {
            content.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        for (Fragment item : getSupportFragmentManager().getFragments()) {
            if (!item.getClass().getSimpleName().equals(currentFragment)) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.remove(item);
                trans.commit();
                getSupportFragmentManager().popBackStack();
//                getSupportFragmentManager().beginTransaction().remove(item).commit();
            }
        }

        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        if (frag == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_body, fragment, TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private Fragment getFragment(Fragment fragment) {
        return getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
    }

    int troubleCodeInt = 0;

    private View.OnClickListener onMenuItemClickListener = view -> {
        switch (view.getId()) {
            case R.id.connect_to_bt_btn: {
                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                startActivityForResult(intent, REQUEST_CONNECT_TO_DEVICE);
//                ConnectActivity.start(MainActivity.this);
                break;
            }
            case R.id.dashboard_menu_btn: {
                addCarView.setVisibility(View.INVISIBLE);
                DashboardFragment dashboardFragment = new DashboardFragment();
//                dashboardFragment.setSupportedDevices((ArrayList<String>) obdReader.getSupportedPids());
//                Log.d(TAG, "setSupportedDevices: " + obdReader.getSupportedPids());
                showFragment(dashboardFragment);

                addView.setVisibility(View.VISIBLE);

                if (getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_LANDSCAPE) {
                    content.setImageResource(R.drawable.bg_land);
                } else {
                    content.setImageResource(R.drawable.bg_vert);
                }
                hideMenu();
                break;
            }
            case R.id.error_list_menu_btn: {
                addCarView.setVisibility(View.INVISIBLE);
                showFragment(new TroubleCodesFragment());
                addView.setVisibility(View.INVISIBLE);
                content.setImageResource(R.drawable.bg_main);
                hideMenu();
                break;
            }
            case R.id.my_cars_btn: {
                addView.setVisibility(View.INVISIBLE);
                addCarView.setVisibility(View.VISIBLE);
                showFragment(new MyCarsFragment());
                content.setImageResource(R.drawable.bg_main);
                hideMenu();
                break;
            }
            case R.id.check_car_menu_btn: {
                addCarView.setVisibility(View.INVISIBLE);
                showFragment(new CheckAutoFragment());
                addView.setVisibility(View.INVISIBLE);
                content.setImageResource(R.drawable.gradient_background);
                hideMenu();
                break;
            }
            case R.id.profile_menu_btn: {
                addCarView.setVisibility(View.INVISIBLE);
                showFragment(new ProfileFragment());
                addView.setVisibility(View.INVISIBLE);
                content.setImageResource(R.drawable.gradient_background);
                hideMenu();
                break;
            }

            case R.id.settings_menu_btn: {
                SettingsActivity.start(this,
                        obdReader.getBluetoothService().getState() == BluetoothService.STATE_CONNECTED);
//                troubleCodeInt++;
//                String troubleCode = "P000" + troubleCodeInt;

//                activityToTroublesCallbackListener.onPassTroubleCode(troubleCode);
////                showFragment(new ProfileSettingsFragment());
//                addView.setVisibility(View.INVISIBLE);
////                content.setImageResource();(R.drawable.bg_main);
//                hideMenu();
                break;
            }
            case R.id.message_to_developers: {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:carziselm327@gmail.com"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            }
        }
    };

    private void initMenu() {
        connectToBtMenuBtn = findViewById(R.id.connect_to_bt_btn);
        dashboardMenuBtn = findViewById(R.id.dashboard_menu_btn);
        errorListMenuBtn = findViewById(R.id.error_list_menu_btn);
        myCarsMenuBtn = findViewById(R.id.my_cars_btn);
        checkCarMenuBtn = findViewById(R.id.check_car_menu_btn);
        profileMenuBtn = findViewById(R.id.profile_menu_btn);
        settingsMenuBtn = findViewById(R.id.settings_menu_btn);
        useAddingToHistorySwitch = findViewById(R.id.save_to_history_chbx);
        feedbackBtn = findViewById(R.id.message_to_developers);

        if (!Locale.getDefault().getLanguage().equals("ru"))
            checkCarMenuBtn.setVisibility(View.GONE);

        connectToBtMenuBtn.setOnClickListener(onMenuItemClickListener);
        dashboardMenuBtn.setOnClickListener(onMenuItemClickListener);
        errorListMenuBtn.setOnClickListener(onMenuItemClickListener);
        myCarsMenuBtn.setOnClickListener(onMenuItemClickListener);
        checkCarMenuBtn.setOnClickListener(onMenuItemClickListener);
        profileMenuBtn.setOnClickListener(onMenuItemClickListener);
        settingsMenuBtn.setOnClickListener(onMenuItemClickListener);
        useAddingToHistorySwitch.setOnClickListener(onMenuItemClickListener);
        feedbackBtn.setOnClickListener(onMenuItemClickListener);

        useAddingToHistorySwitch.setOnCheckedChangeListener((compoundButton, b) -> {

            if (useAddingToHistorySwitch.isChecked()) {
                carPresenter.getCars();
            } else {
                if (carName != null)
                    Toast.makeText(this, getString(R.string.recording_history_canceled) + carName, Toast.LENGTH_SHORT).show();
                carName = null;
            }

//            Toast.makeText(this, useAddingToHistorySwitch.isChecked() + "", Toast.LENGTH_SHORT).show();
        });

    }

    private void showMenu() {
        ViewAnimator
                .animate(menuView)
                .slideLeft()
                .duration(300)

                .andAnimate(movingContainer)
                .translationX(Utility.convertDpToPx(this, 202))
                .duration(300)

                .onStart(() -> menuView.setVisibility(View.VISIBLE))
                .start();
    }

    private void hideMenu() {
        ViewAnimator
                .animate(menuView)
                .translationX(-Utility.convertDpToPx(this, 202))
                .duration(300)

                .andAnimate(movingContainer)
                .translationX(-Utility.convertDpToPx(this, 0))
                .duration(300)

                .onStop(() -> menuView.setVisibility(View.GONE))
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (obdReader.getBluetoothService() != null) obdReader.getBluetoothService().stop();
    }

    @Override
    public void onPassDataToActivity() {

    }

    @Override
    public void getTroubleCodes() {
        obdReader.getTroubleCodes();
    }

    @Override
    public void cleanTroubleCodes() {
        obdReader.cleanSavedTroubleCodes();
    }

    @Override
    public void onReceiveData(PID pid, String value) {
        Log.d(TAG, "onReceiveData: PID: " + pid + ", " + value);
        if (activityToDashboardCallbackListener != null)
            activityToDashboardCallbackListener.onPassRealDataToFragment(pid, value);

        if (useAddingToHistorySwitch.isChecked() && carName != null) {
            historyPresenter.addMetric(getCarIdByName(carName), pid.getCommand(), value);
        }

    }

    @Override
    public void onReceiveVoltage(String voltage) {
        Log.d(TAG, "onReceiveVoltage: " + voltage);
        if (activityToDashboardCallbackListener != null)
            activityToDashboardCallbackListener.onPassRealDataToFragment(PID.VOLTAGE, voltage);

        if (useAddingToHistorySwitch.isChecked() && carName != null) {
            historyPresenter.addMetric(getCarIdByName(carName), PID.VOLTAGE.getCommand(), voltage.replace("V", ""));
        }
    }

    @Override
    public void onConnected(String deviceName) {
        connectToBtMenuBtn.setText(String.format(getString(R.string.connected_to), deviceName));
    }

    @Override
    public void onDisconnected() {
        connectToBtMenuBtn.setText(R.string.connected_to_device);
    }

    @Override
    public void onReceiveFaultCode(String faultCode) {
        if (activityToTroublesCallbackListener != null)
            activityToTroublesCallbackListener.onPassTroubleCode(faultCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CONNECT_TO_DEVICE: {
                if (resultCode == RESULT_OK) {
                    String deviceName = data.getStringExtra(ConnectActivity.EXTRA_DEVICE_NAME);
                    String deviceAddress = data.getStringExtra(ConnectActivity.EXTRA_DEVICE_ADDRESS);

                    if (menuView.getVisibility() == View.VISIBLE)
                        hideMenu();

                    obdReader.setProtocolNum(keyValueStorage.getProtocol());
                    obdReader.setInitializeCommands(
                            Arrays.asList(keyValueStorage.getInitString().split(";")));
                    obdReader.connectDevice(deviceAddress, deviceName);

                } else {
                    Toast.makeText(this, R.string.canceled_by_use, Toast.LENGTH_SHORT).show();
                    if (menuView.getVisibility() == View.VISIBLE)
                        hideMenu();
                }
                break;
            }
            case DIALOG_LIST_ACTIVITY_CODE: {
                if (resultCode == RESULT_OK) {
                    carName = data.getStringExtra(STRING_EXTRA);
                    carName = carName.substring(carName.indexOf("\"") + 1, carName.lastIndexOf("\""));
                    Toast.makeText(this, getString(R.string.starts_recording_for) + carName, Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, R.string.canceled_by_user, Toast.LENGTH_SHORT).show();
                    useAddingToHistorySwitch.setChecked(false);
                }
                break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FRAGMENT, currentFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentFragment = savedInstanceState.getString(FRAGMENT);
    }

    @Override
    public void onGetCar(Car car) {

    }

    @Override
    public void onGetCars(List<Car> cars) {
        userCars = cars;

        ArrayList<String> carTitles = new ArrayList<>();
        for (Car car : cars) {
            carTitles.add(car.getBrand() + " " + car.getModel() + "\t\"" + car.getName() + "\"");
        }

        Log.d(TAG, "onGetCars: " + cars);

        String title = getString(R.string.choose_car_for_history_recording);
        DialogListActivity.startForResult(this, title, carTitles);
    }

    @Override
    public void onGetHistoryItems(List<HistoryItem> items, String carName) {

    }

    @Override
    public void onCarMetricAdded(CarMetric carMetric) {
//        localRepository
//                .addHistoryItem(
//                        new HistoryItem(
//                                getCarNameById(carMetric.getCarId()),
//                                carMetric.getMetricCode(),
//                                carMetric.getMetricValue(),
//                                Calendar.getInstance().getTimeInMillis() + ""));
    }

    @Override
    public void onRemoteRepoError() {
        useAddingToHistorySwitch.setChecked(false);
    }

    @Override
    public void onCarAdded() {

    }

    private String getCarIdByName(String name) {
        for (Car car : userCars) {
            if (car.getName().equals(name))
                return car.getId();
        }
        return "";
    }

    private String getCarNameById(String id) {
        for (Car car : userCars) {
            if (car.getId().equals(id))
                return car.getName();
        }
        return "";
    }


    @Override
    public void onDeleteCar() {

    }

//    @Override
//    public void onBackPressed() {
////        if (menuView.getVisibility() == View.VISIBLE)
////            super.onBackPressed();
////        else
////            showMenu();
//    }
}
