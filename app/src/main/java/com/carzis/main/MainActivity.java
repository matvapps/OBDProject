package com.carzis.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.TroubleCodes;
import com.carzis.connect.BluetoothService;
import com.carzis.connect.ConnectActivity;
import com.carzis.main.fragment.CheckAutoFragment;
import com.carzis.main.fragment.DashboardFragment;
import com.carzis.main.fragment.TroubleCodesFragment;
import com.carzis.main.listener.ActivityToDashboardCallbackListener;
import com.carzis.main.listener.ActivityToTroublesCallbackListener;
import com.carzis.main.listener.DashboardToActivityCallbackListener;
import com.carzis.main.listener.TroublesToActivityCallbackListener;
import com.carzis.util.Utility;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DashboardToActivityCallbackListener,
        TroublesToActivityCallbackListener {

    private final String TAG = MainActivity.class.getSimpleName();

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private String mConnectedDeviceName = "Ecu";

    private View content;
    private TextView timeText;
    private ImageButton menuBtn;
    private View menuView;
    private View addView;

    private Button dashboardMenuBtn;
    private Button errorListMenuBtn;
    private Button myCarsMenuBtn;
    private Button checkCarMenuBtn;
    private Button profileMenuBtn;
    private Button historyMenuBtn;
    private Button settingsMenuBtn;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice currentdevice;
    private BluetoothService bluetoothService = null;

    public ActivityToDashboardCallbackListener activityFragmentCallbackListener;
    public ActivityToTroublesCallbackListener activityToTroublesCallbackListener;

    private TroubleCodes troubleCodes;

    final List<String> commandslist = new ArrayList<String>();
    private final List<Double> avgconsumption = new ArrayList<Double>();
    final List<String> troubleCodesArray = new ArrayList<String>();
    // init String it can be change by user TODO:
    private String[] initializeCommands;


    protected final static char[] dtcLetters = {'P', 'C', 'B', 'U'};
    protected final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final String[] PIDS = {
            "01", "02", "03", "04", "05", "06", "07", "08",
            "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
            "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "1A", "1B", "1C", "1D", "1E", "1F", "2F", "20"};

    private String VOLTAGE = "ATRV",
            PROTOCOL = "ATDP",
            RESET = "ATZ",
            ENGINE_COOLANT_TEMP = "0105",  //A-40
            ENGINE_RPM = "010C",  //((A*256)+B)/4
            ENGINE_LOAD = "0104",  // A*100/255
            VEHICLE_SPEED = "010D",  //A
            INTAKE_AIR_TEMP = "010F",  //A-40
            MAF_AIR_FLOW = "0110", //MAF air flow rate 0 - 655.35	grams/sec ((256*A)+B) / 100  [g/s]
            FUEL_LEVEL = "012F", // FUEL level 	0-100%	A*100/255
            ENGINE_OIL_TEMP = "015C",  //A-40
            FUEL_RAIL_PRESSURE = "0122", // ((A*256)+B)*0.079
            INTAKE_MAN_PRESSURE = "010B", //Intake manifold absolute pressure 0 - 255 kPa
            CONT_MODULE_VOLT = "0142",  //((A*256)+B)/1000
            AMBIENT_AIR_TEMP = "0146",  //A-40
            CATALYST_TEMP_B1S1 = "013C",  //(((A*256)+B)/10)-40
            STATUS_DTC = "0101", //Status since DTC Cleared
            THROTTLE_POSITION = "0111", //Throttle position 0 -100 % A*100/255
            OBD_STANDARDS = "011C", //OBD standards this vehicle
            PIDS_SUPPORTED = "0120"; //PIDs supported

    private boolean tryconnect = false;
    private boolean m_getPids = false;
    private boolean connected = false;
    private boolean initialized = false;

    private int connectcount = 0;
    private int trycount = 0;
    private int whichCommand = 0;
    private int m_dedectPids = 0;
    private int rpmval = 0;
    private int intakeairtemp = 0;
    private int ambientairtemp = 0;
    private int coolantTemp = 0;
    private int engineoiltemp = 0;
    private int b1s1temp = 0;
    private int Enginetype = 0;

    private double Enginedisplacement = 1500;

    private String devicename = null;
    private String deviceadress = null;
    private String deviceprotocol = null;


    public static void start(Activity activity, String deviceName, String deviceAddress) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(ConnectActivity.EXTRA_DEVICE_ADDRESS, deviceAddress);
        intent.putExtra(ConnectActivity.EXTRA_DEVICE_NAME, deviceName);
        activity.startActivity(intent);
    }


    private void initView() {

        content = findViewById(R.id.content);
        menuView = findViewById(R.id.menu);
        addView = findViewById(R.id.add_btn);
        timeText = findViewById(R.id.time_text_view);
        menuBtn = findViewById(R.id.menu_btn);

        initMenu();

        menuBtn.setOnClickListener(view -> {
            if (menuView.getVisibility() == View.VISIBLE)
                hideMenu();
            else
                showMenu();

//            BaseInputConnection mInputConnection = new BaseInputConnection(menuBtn, true);
//            KeyEvent kd = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MENU);
//            KeyEvent ku = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MENU);
//            mInputConnection.sendKeyEvent(kd);
//            mInputConnection.sendKeyEvent(ku);
        });
        content.setOnClickListener(view -> {
            if (menuView.getVisibility() == View.VISIBLE)
                hideMenu();
        });


        showFragment(new DashboardFragment());
        startTimeThread();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        deviceadress = getIntent().getStringExtra(ConnectActivity.EXTRA_DEVICE_ADDRESS);
        devicename = getIntent().getStringExtra(ConnectActivity.EXTRA_DEVICE_NAME);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        troubleCodes = new TroubleCodes();
        initializeCommands
                = new String[]{"ATZ", "ATL0", "ATE1", "ATH1", "ATAT1", "ATSTFF", "ATI", "ATDP", "ATSP0", "ATSP0"};

        commandslist.add(FUEL_LEVEL);
        commandslist.add(VOLTAGE);
        commandslist.add(ENGINE_OIL_TEMP);
        commandslist.add(ENGINE_RPM);
        commandslist.add(VEHICLE_SPEED);
        commandslist.add(ENGINE_LOAD);
        commandslist.add(ENGINE_COOLANT_TEMP);
        commandslist.add(INTAKE_AIR_TEMP);
        commandslist.add(MAF_AIR_FLOW);

        whichCommand = 0;

        // Initialize the BluetoothChatService to perform bluetooth connections
        bluetoothService = new BluetoothService(this, mBtHandler);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        } else {
            if (bluetoothService != null) {
                if (bluetoothService.getState() == BluetoothService.STATE_NONE) {
                    bluetoothService.start();
                }
            }
        }


        connectDevice(deviceadress, devicename);


    }


    private void connectDevice(String address, String name) {
        tryconnect = true;
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        try {
            // Attempt to connect to the device
            bluetoothService.connect(device);
            currentdevice = device;

        } catch (Exception e) {
            Toast.makeText(this, "Unable to connect to " + name, Toast.LENGTH_SHORT).show();
        }
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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want exit?");
            alertDialogBuilder.setPositiveButton("Ok",
                    (arg0, arg1) -> exit());

            alertDialogBuilder.setNegativeButton("cancel",
                    (arg0, arg1) -> {

                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (bluetoothService != null) bluetoothService.stop();
//            wl.release();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private void showFragment(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_body, fragment, TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private Fragment getFragment(Fragment fragment) {
        return getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
    }


    private View.OnClickListener onMenuItemClickListener = view -> {
        switch (view.getId()) {
            case R.id.dashboard_menu_btn: {
                showFragment(new DashboardFragment());
                addView.setVisibility(View.VISIBLE);
                content.setBackgroundResource(R.drawable.bg);
                hideMenu();
                break;
            }
            case R.id.error_list_menu_btn: {
                showFragment(new TroubleCodesFragment());
                addView.setVisibility(View.INVISIBLE);
                content.setBackgroundResource(R.drawable.bg_main);
                hideMenu();
                break;
            }
            case R.id.my_cars_btn: {
                addView.setVisibility(View.VISIBLE);

                hideMenu();
                break;
            }
            case R.id.check_car_menu_btn: {
                showFragment(new CheckAutoFragment());
                addView.setVisibility(View.INVISIBLE);
                content.setBackgroundResource(R.drawable.gradient_background);
                hideMenu();
                break;
            }
            case R.id.profile_menu_btn: {
                addView.setVisibility(View.INVISIBLE);
                hideMenu();
                break;
            }
            case R.id.history_menu_btn: {
                addView.setVisibility(View.INVISIBLE);
                hideMenu();
                break;
            }
            case R.id.settings_menu_btn: {
                addView.setVisibility(View.INVISIBLE);
                hideMenu();
                break;
            }

        }
    };

    private void initMenu() {
        dashboardMenuBtn = findViewById(R.id.dashboard_menu_btn);
        errorListMenuBtn = findViewById(R.id.error_list_menu_btn);
        myCarsMenuBtn = findViewById(R.id.my_cars_btn);
        checkCarMenuBtn = findViewById(R.id.check_car_menu_btn);
        profileMenuBtn = findViewById(R.id.profile_menu_btn);
        historyMenuBtn = findViewById(R.id.history_menu_btn);
        settingsMenuBtn = findViewById(R.id.settings_menu_btn);


        dashboardMenuBtn.setOnClickListener(onMenuItemClickListener);
        errorListMenuBtn.setOnClickListener(onMenuItemClickListener);
        myCarsMenuBtn.setOnClickListener(onMenuItemClickListener);
        checkCarMenuBtn.setOnClickListener(onMenuItemClickListener);
        profileMenuBtn.setOnClickListener(onMenuItemClickListener);
        historyMenuBtn.setOnClickListener(onMenuItemClickListener);
        settingsMenuBtn.setOnClickListener(onMenuItemClickListener);

    }

    private void showMenu() {
        ViewAnimator
                .animate(menuView)
                .slideLeft()
                .duration(300)

                .andAnimate(content)
                .translationX(Utility.convertDpToPx(this, 180))
                .duration(300)

                .onStart(() -> menuView.setVisibility(View.VISIBLE))
                .start();
    }

    private void hideMenu() {
        ViewAnimator
                .animate(menuView)
                .translationX(-Utility.convertDpToPx(this, 180))
                .duration(300)

                .andAnimate(content)
                .translationX(-Utility.convertDpToPx(this, 0))
                .duration(300)

                .onStop(() -> menuView.setVisibility(View.GONE))
                .start();
    }


    public void resetvalues() {

//        engineLoad.setText("0 %");
//        voltage.setText("0 V");
//        coolantTemperature.setText("0 C°");
//        Info.setText("");
//        airTemperature.setText("0 C°");
//        Maf.setText("0 g/s");
//        Fuel.setText("0 - 0 l/h");

        m_getPids = false;
        whichCommand = 0;
        trycount = 0;
        initialized = false;
//        defaultStart = false;
        avgconsumption.clear();
//        mConversationArrayAdapter.clear();

        // TODO:
//        resetgauges();

        sendEcuMessage(RESET);
    }

    private void sendEcuMessage(String message) {
//        if (mWifiService != null) {
//            if (mWifiService.isConnected()) {
//                try {
//                    if (message.length() > 0) {
//                        message = message + "\r";
//                        byte[] send = message.getBytes();
//                        mWifiService.write(send);
//                    }
//                } catch (Exception e) {
//                }
//            }
//        } else if (mBtService != null) {
        // Check that we're actually connected before trying anything
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Log.d(TAG, "INFO: " + getString(R.string.not_connected));
            return;
        }
        try {
            if (message.length() > 0) {

                message = message + "\r";
                // Get the message bytes and tell the BluetoothChatService to write
                byte[] send = message.getBytes();
                bluetoothService.write(send);
            }
        } catch (Exception e) {
            Toast.makeText(this, "sendECU Message Error", Toast.LENGTH_SHORT).show();
        }
//        }
    }


    private void analysMsg(Message msg) {

        String tmpmsg = clearMsg(msg);
        generateVolt(tmpmsg);
        getElmInfo(tmpmsg);

        if (!initialized) {

            sendInitCommands();

        } else {

            checkPids(tmpmsg);

            if (!m_getPids && m_dedectPids == 1) {
                String sPIDs = "0100";
                sendEcuMessage(sPIDs);
                return;
            }

//            if (getTroubles) {
            getFaultInfo(tmpmsg);
//                getTroubles = false;
//                return;
//            }

            try {
                analysPIDS(tmpmsg);
            } catch (Exception e) {
                Log.e(TAG, "ERROR: ", e);
            }

            sendDefaultCommands();
        }
    }

    private String clearMsg(Message msg) {
        String tmpmsg = msg.obj.toString();

        tmpmsg = tmpmsg.replace("null", "");
        tmpmsg = tmpmsg.replaceAll("\\s", ""); //removes all [ \t\n\x0B\f\r]
        tmpmsg = tmpmsg.replaceAll(">", "");
        tmpmsg = tmpmsg.replaceAll("SEARCHING...", "");
        tmpmsg = tmpmsg.replaceAll("ATZ", "");
        tmpmsg = tmpmsg.replaceAll("ATI", "");
        tmpmsg = tmpmsg.replaceAll("atz", "");
        tmpmsg = tmpmsg.replaceAll("ati", "");
        tmpmsg = tmpmsg.replaceAll("ATDP", "");
        tmpmsg = tmpmsg.replaceAll("atdp", "");
        tmpmsg = tmpmsg.replaceAll("ATRV", "");
        tmpmsg = tmpmsg.replaceAll("atrv", "");

        return tmpmsg;
    }

    private void generateVolt(String msg) {

        String VoltText = null;

        if ((msg != null) && (msg.matches("\\s*[0-9]{1,2}([.][0-9]{1,2})\\s*"))) {
            VoltText = msg;
//
//            mConversationArrayAdapter.add(mConnectedDeviceName + ": " + msg + "V");
        } else if ((msg != null) && (msg.matches("\\s*[0-9]{1,2}([.][0-9]{1,2})?V\\s*"))) {
            VoltText = msg.replace("V", "");
//            mConversationArrayAdapter.add(mConnectedDeviceName + ": " + msg);
        }

        if (VoltText != null) {
            activityFragmentCallbackListener
                    .onPassRealDataToFragment(Type.VOLTAGE, VoltText);
        }
    }

    private void getElmInfo(String tmpmsg) {

        if (tmpmsg.contains("ELM") || tmpmsg.contains("elm")) {
            devicename = tmpmsg;
        }

        if (tmpmsg.contains("SAE") || tmpmsg.contains("ISO")
                || tmpmsg.contains("sae") || tmpmsg.contains("iso") || tmpmsg.contains("AUTO")) {
            deviceprotocol = tmpmsg;
        }

        if (deviceprotocol != null && devicename != null) {
            devicename = devicename.replaceAll("STOPPED", "");
            deviceprotocol = deviceprotocol.replaceAll("STOPPED", "");
//            Status.setText(devicename + " " + deviceprotocol);
        }
    }

    private void sendInitCommands() {
        if (initializeCommands.length != 0) {

            if (whichCommand < 0) {
                whichCommand = 0;
            }

            String send = initializeCommands[whichCommand];
            sendEcuMessage(send);

            if (whichCommand == initializeCommands.length - 1) {
                initialized = true;
                whichCommand = 0;
                sendDefaultCommands();
            } else {
                whichCommand++;
            }
        }
    }

    private void sendDefaultCommands() {

        if (commandslist.size() != 0) {

            if (whichCommand < 0) {
                whichCommand = 0;
            }

            String send = commandslist.get(whichCommand);
            Log.d(TAG, "sendDefaultCommands: send " + send);
            sendEcuMessage(send);

            if (whichCommand >= commandslist.size() - 1) {
                whichCommand = 0;
            } else {
                whichCommand++;
            }
        }
    }

    private void checkPids(String tmpmsg) {
        if (tmpmsg.indexOf("41") != -1) {
            int index = tmpmsg.indexOf("41");

            String pidmsg = tmpmsg.substring(index, tmpmsg.length());
            if (pidmsg.contains("4100")) {

                setPidsSupported(pidmsg);
                return;
            }
        }
    }

    private void analysPIDS(String dataRecieved) {

        int A = 0;
        int B = 0;
        int PID = 0;

        if ((dataRecieved != null) && (dataRecieved.matches("^[0-9A-F]+$"))) {

            dataRecieved = dataRecieved.trim();

            int index = dataRecieved.indexOf("41");

            String tmpmsg = null;

            if (index != -1) {

                tmpmsg = dataRecieved.substring(index, dataRecieved.length());

                Log.d(TAG, "analysPIDS: tmpmsg = " + tmpmsg);

                if (tmpmsg.substring(0, 2).equals("41")) {

                    PID = Integer.parseInt(tmpmsg.substring(2, 4), 16);
                    A = Integer.parseInt(tmpmsg.substring(4, 6), 16);
                    B = Integer.parseInt(tmpmsg.substring(6, 8), 16);

                    Log.d(TAG, "analysPIDS: " + PID);

                    calculateEcuValues(PID, A, B);
                }
            }
        }
    }

    private void getFaultInfo(String tmpmsg) {

        String substr = "43";

        int index = tmpmsg.indexOf(substr);

        if (index == -1) {
            substr = "47";
            index = tmpmsg.indexOf(substr);
        }

        if (index != -1) {

            tmpmsg = tmpmsg.substring(index, tmpmsg.length());

            if (tmpmsg.substring(0, 2).equals(substr)) {

                performCalculations(tmpmsg);

                String faultCode = null;
                String faultDesc = null;

                if (troubleCodesArray.size() > 0) {

                    for (int i = 0; i < troubleCodesArray.size(); i++) {
                        faultCode = troubleCodesArray.get(i);
                        faultDesc = troubleCodes.getFaultCode(faultCode);

                        Log.e(TAG, "Fault Code: " + substr + " : " + faultCode + " desc: " + faultDesc);

                        if (faultCode != null && faultDesc != null) {
//                            TODO: found fault code
                            Log.d(TAG, "getFaultInfo: " + faultCode);
                            if (activityToTroublesCallbackListener != null)
                                activityToTroublesCallbackListener.onPassTroubleCode(faultCode, faultDesc);
//                            mConversationArrayAdapter.add(mConnectedDeviceName + ":  TroubleCode -> " + faultCode + "\n" + faultDesc);
                        } else if (faultCode != null && faultDesc == null) {
                            // TODO: found fault code
                            Log.d(TAG, "getFaultInfo: " + faultCode);
                            if (activityToTroublesCallbackListener != null)
                                activityToTroublesCallbackListener.onPassTroubleCode(faultCode, "no description found");
//                            mConversationArrayAdapter.add(mConnectedDeviceName + ":  TroubleCode -> " + faultCode +
//                                    "\n" + "Definition not found for code: " + faultCode);
                        }
                    }
                } else {
                    faultCode = "No error found...";
                    // TODO:
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  TroubleCode -> " + faultCode);
                }
            }
        }
    }

    protected void performCalculations(String fault) {

        final String result = fault;
        String workingData = "";
        int startIndex = 0;
        troubleCodesArray.clear();

        try {

            if (result.indexOf("43") != -1) {
                workingData = result.replaceAll("^43|[\r\n]43|[\r\n]", "");
            } else if (result.indexOf("47") != -1) {
                workingData = result.replaceAll("^47|[\r\n]47|[\r\n]", "");
            }

            for (int begin = startIndex; begin < workingData.length(); begin += 4) {
                String dtc = "";
                byte b1 = Utility.hexStringToByteArray(workingData.charAt(begin));
                int ch1 = ((b1 & 0xC0) >> 6);
                int ch2 = ((b1 & 0x30) >> 4);
                dtc += dtcLetters[ch1];
                dtc += hexArray[ch2];
                dtc += workingData.substring(begin + 1, begin + 4);

                if (dtc.equals("P0000")) {
                    continue;
                }

                Log.d(TAG, "performCalculations: " + dtc);
                troubleCodesArray.add(dtc);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    private void setPidsSupported(String buffer) {
        Log.d(TAG, "INFO: Trying to get available pids : " + String.valueOf(trycount));
        trycount++;

        StringBuilder flags = new StringBuilder();
        String buf = buffer.toString();
        buf = buf.trim();
        buf = buf.replace("\t", "");
        buf = buf.replace(" ", "");
        buf = buf.replace(">", "");

        if (buf.indexOf("4100") == 0 || buf.indexOf("4120") == 0) {

            for (int i = 0; i < 8; i++) {
                String tmp = buf.substring(i + 4, i + 5);
                int data = Integer.valueOf(tmp, 16).intValue();
//                String retStr = Integer.toBinaryString(data);
                if ((data & 0x08) == 0x08) {
                    flags.append("1");
                } else {
                    flags.append("0");
                }

                if ((data & 0x04) == 0x04) {
                    flags.append("1");
                } else {
                    flags.append("0");
                }

                if ((data & 0x02) == 0x02) {
                    flags.append("1");
                } else {
                    flags.append("0");
                }

                if ((data & 0x01) == 0x01) {
                    flags.append("1");
                } else {
                    flags.append("0");
                }
            }

            commandslist.clear();
            commandslist.add(0, VOLTAGE);
            int pid = 1;

            StringBuilder supportedPID = new StringBuilder();
            supportedPID.append("Supported PIDS:\n");
            for (int j = 0; j < flags.length(); j++) {
                if (flags.charAt(j) == '1') {
                    supportedPID.append(" " + PIDS[j] + " ");
                    if (!PIDS[j].contains("11") && !PIDS[j].contains("01") && !PIDS[j].contains("20")) {
                        commandslist.add(pid, "01" + PIDS[j]);
                        pid++;
                    }
                }
            }

            m_getPids = true;
//            TODO:
//            mConversationArrayAdapter.add(mConnectedDeviceName + ": " + supportedPID.toString());
            whichCommand = 0;
            sendEcuMessage("ATRV");

        } else {

            return;
        }
    }

    private void calculateEcuValues(int PID, int A, int B) {

        double val = 0;
        int intval = 0;
        int tempC = 0;

        switch (PID) {

            case 4://PID(04): Engine Load

                // A*100/255
                val = A * 100 / 255;
                int calcLoad = (int) val;

//                engineLoad.setText(Integer.toString(calcLoad) + " %");
//                TODO:
//                mConversationArrayAdapter.add("Engine Load: " + Integer.toString(calcLoad) + " %");

                String consumption = null;

                if (Enginetype == 0) {
                    consumption = String.format("%10.1f", (0.001 * 0.004 * 4.3 * Enginedisplacement * rpmval * 60 * calcLoad / 20)).trim();
                    avgconsumption.add((0.001 * 0.004 * 4 * Enginedisplacement * rpmval * 60 * calcLoad / 20));

                } else if (Enginetype == 1) {
                    consumption = String.format("%10.1f", (0.001 * 0.004 * 4.3 * 1.35 * Enginedisplacement * rpmval * 60 * calcLoad / 20)).trim();
                    avgconsumption.add((0.001 * 0.004 * 4 * 1.35 * Enginedisplacement * rpmval * 60 * calcLoad / 20));

                }
//                Fuel.setText(consumption + " - " + String.format("%10.1f", calculateAverage(avgconsumption)).trim() + " l/h");
//                TODO:
//                mConversationArrayAdapter.add("Fuel Consumption: " + consumption + " - " + String.format("%10.1f", calculateAverage(avgconsumption)).trim() + " l/h");

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.ENGINE_LOAD, String.valueOf(calcLoad));

                break;

            case 5://PID(05): Coolant Temperature

                // A-40
                tempC = A - 40;
                coolantTemp = tempC;
//                coolantTemperature.setText(Integer.toString(coolantTemp) + " C°");
//                mConversationArrayAdapter.add("Enginetemp: " + Integer.toString(tempC) + " C°");
                //TODO:
                Log.d(TAG, "CoolantTemp: " + tempC);

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.COOLANT_TEMP, String.valueOf(tempC));

                break;

            case 11://PID(0B)

                // A
//                TODO:
//                mConversationArrayAdapter.add("Intake Man Pressure: " + Integer.toString(A) + " kPa");
                Log.d(TAG, "Intake Man Pressure: " + A);

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.INTAKE_MAN_PRESSURE, String.valueOf(A));
                break;

            case 12: //PID(0C): RPM

                //((A*256)+B)/4
                val = ((A * 256) + B) / 4;
                intval = (int) val;
                rpmval = intval;
                Log.d(TAG, "RPM: " + intval);

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.TURNOVERS, String.valueOf(intval));

                break;


            case 13://PID(0D): KM

                // A
                Log.d(TAG, "Speed: " + A);
                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.SPEED, String.valueOf(A));
                break;

            case 15://PID(0F): Intake Temperature

                // A - 40
                tempC = A - 40;
                intakeairtemp = tempC;
//                airTemperature.setText(Integer.toString(intakeairtemp) + " C°");
//                TODO:
//                mConversationArrayAdapter.add("Intakeairtemp: " + Integer.toString(intakeairtemp) + " C°");
                Log.d(TAG, "Intakeairtemp: " + intakeairtemp);
//                if (dashboardItemsAdapter != null)

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.INTAKE_AIR_TEMP, String.valueOf(intakeairtemp));

                break;

            case 16://PID(10): Maf

                // ((256*A)+B) / 100  [g/s]
                val = ((256 * A) + B) / 100;
//                Maf.setText(Integer.toString(intval) + " g/s");
                Log.d(TAG, "MAF AIR FLOW: " + A);

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.MAF_AIR_FLOW, String.valueOf(val));
                break;

            case 17://PID(11)

                //A*100/255
                val = A * 100 / 255;
//                mConversationArrayAdapter.add(" Throttle position: " + Integer.toString(intval) + " %");
                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.THROTTLE_POS, String.valueOf(val));
                break;

            case 35://PID(23)

                // ((A*256)+B)*0.079
                val = ((A * 256) + B) * 0.079;
//                mConversationArrayAdapter.add("Fuel Rail Pressure: " + Integer.toString(intval) + " kPa");
                Log.d(TAG, "Fuel Rail pressure: " + Double.toString(val) + " kPa");

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.FUEL_RAIL_PRESSURE, String.valueOf(val));
                break;

            case 47:// PID(2F)

                int fuelLevel = A * 100 / 255;
                Log.d(TAG, "Fuel level: " + fuelLevel);

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.FUEL_LEVEL, String.valueOf(fuelLevel));
                break;

            case 49://PID(31)

                //(256*A)+B km
                val = (A * 256) + B;

                Log.d(TAG, "Distance traveled: " + Integer.toString(intval) + " km");

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.DISTANCE_TRAVELED, String.valueOf(val));

                break;

            case 70://PID(46)

                // A-40 [DegC]
                tempC = A - 40;
                ambientairtemp = tempC;
                Log.d(TAG, "Ambientairtemp: " + Integer.toString(ambientairtemp) + " C°");

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.AMBIENT_AIR_TEMP, String.valueOf(ambientairtemp));
                break;

            case 92://PID(5C)

                //A-40
                tempC = A - 40;
                engineoiltemp = tempC;
//                mConversationArrayAdapter.add("Engineoiltemp: " + Integer.toString(engineoiltemp) + " C°");
                Log.d(TAG, "Engineoiltemp: " + Integer.toString(ambientairtemp) + " C°");

                activityFragmentCallbackListener
                        .onPassRealDataToFragment(Type.ENGINE_OIL_TEMP, String.valueOf(engineoiltemp));
                break;

            default:
        }
    }


    @SuppressLint("HandlerLeak")
    private final Handler mBtHandler = new Handler() {
        @SuppressLint("StringFormatInvalid")
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:

                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.d(TAG, "Status: " + getString(R.string.title_connected_to, mConnectedDeviceName));
                            Log.d(TAG, "INFO: " + getString(R.string.title_connected));
//                            Toast.makeText(DashboardFragment.this, "Status: " + getString(R.string.title_connected_to, mConnectedDeviceName), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(DashboardFragment.this, "INFO: " + getString(R.string.title_connected), Toast.LENGTH_SHORT).show();

//                            Status.setText(getString(R.string.title_connected_to, mConnectedDeviceName));
//                            Info.setText(R.string.title_connected);
                            connected = true;
//                            try {
////                                itemtemp = menu.findItem(R.id.menu_connect_bt);
////                                itemtemp.setTitle(R.string.disconnectbt);
////                                Info.setText(R.string.title_connected);
//                            } catch (Exception e) {
//                            }
                            tryconnect = false;
                            resetvalues();
                            sendEcuMessage(RESET);

                            break;
                        case BluetoothService.STATE_CONNECTING:
//                            Status.setText(R.string.title_connecting);
//                            Info.setText(R.string.tryconnectbt);
                            break;
                        case BluetoothService.STATE_LISTEN:

                        case BluetoothService.STATE_NONE:

//                            Status.setText(R.string.title_not_connected);
//                            itemtemp = menu.findItem(R.id.menu_connect_bt);
//                            itemtemp.setTitle(R.string.connectbt);
                            connected = true;
                            if (tryconnect) {
                                bluetoothService.connect(currentdevice);
                                connectcount++;
                                if (connectcount >= 2) {
                                    tryconnect = false;
                                }
                            }
                            resetvalues();

                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);

                    // TODO:
//                    if (commandmode || !initialized) {
//                        mConversationArrayAdapter.add("Command:  " + writeMessage);
//                    }

                    break;
                case MESSAGE_READ:

                    String tmpmsg = clearMsg(msg);

//                    Info.setText(tmpmsg);

                    /*if (tmpmsg.contains(RSP_ID.NODATA.response) || tmpmsg.contains(RSP_ID.ERROR.response)) {

                        try{
                            String command = tmpmsg.substring(0,4);

                            if(isHexadecimal(command))
                            {
                                removePID(command);
                            }

                        }catch(Exception e)
                        {
                            Toast.makeTextgetActivity(), e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        }
                    }*/

//                    if (commandmode || !initialized) {
//                        mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + tmpmsg);
//                    }

                    analysMsg(msg);

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(MainActivity.this, msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void sendClearFaultsPID() {
        sendEcuMessage("04");
    }

    public void sendTroubleCodesPID() {
        sendEcuMessage("03");
    }


    @Override
    public void onPassDataToActivity() {

    }

    @Override
    public void getTroubleCodes() {
        sendTroubleCodesPID();
    }
}
