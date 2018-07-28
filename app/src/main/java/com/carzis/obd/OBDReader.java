package com.carzis.obd;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.connect.BluetoothService;
import com.carzis.util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class OBDReader {

    private final String TAG = OBDReader.class.getSimpleName();

    private Context context;

//   0	Automatic protocol detection
//   1	SAE J1850 PWM (41.6 kbaud)
//   2	SAE J1850 VPW (10.4 kbaud)
//   3	ISO 9141-2 (5 baud init, 10.4 kbaud)
//   4	ISO 14230-4 KWP (5 baud init, 10.4 kbaud)
//   5	ISO 14230-4 KWP (fast init, 10.4 kbaud)
//   6	ISO 15765-4 CAN (11 bit ID, 500 kbaud)
//   7	ISO 15765-4 CAN (29 bit ID, 500 kbaud)
//   8	ISO 15765-4 CAN (11 bit ID, 250 kbaud)
//   9	ISO --4 CAN (29 bit ID, 250 kbaud)

//      Auto select protocol and save. AUTO -> 0
//      41.6 kbaud SAE_J1850_PWM -> 1
//      10.4 kbaud SAE_J1850_VPW -> 2
//      5 baud init ISO_9141_2 -> 3
//      5 baud init ISO_14230_4_KWP -> 4
//      Fast init ISO_14230_4_KWP_FAST -> 5
//      11 bit ID, 500 kbaud ISO_15765_4_CAN -> 6
//      29 bit ID, 500 kbaud ISO_15765_4_CAN_B -> 7
//      11 bit ID, 250 kbaud ISO_15765_4_CAN_C -> 8
//      29 bit ID, 250 kbaud ISO_15765_4_CAN_D -> 9
//      29 bit ID, 250 kbaud (user adjustable) SAE_J1939_CAN -> A
//      11 bit ID (user adjustable), 125 kbaud (user adjustable) USER1_CAN -> B
//      11 bit ID (user adjustable), 50 kbaud (user adjustable) USER2_CAN -> C


    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    private static final String DEVICE_NAME = "device_name";
    private static final String TOAST = "toast";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice currentdevice;
    private BluetoothService bluetoothService;

    private OnReceiveFaultCodeListener onReceiveFaultCodeListener;
    private OnReceiveDataListener onReceiveDataListener;

    final List<String> defaultCommandsList = new ArrayList<>();
    private final List<Double> avgconsumption = new ArrayList<>();
    final List<String> troubleCodesArray = new ArrayList<>();

    private List<String> initializeCommands;
    private int protocolNum;
    private List<String> supportedPids;

    private final static char[] dtcLetters = {'P', 'C', 'B', 'U'};
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final String VOLTAGE = "ATRV";
    private final String PROTOCOL = "ATDP";
    private final String RESET = "ATZ";
    private final String SET_PROTOCOL = "ATSP";

//    private String ENGINE_LOAD = "0104",  // A*100/255
//            ENGINE_COOLANT_TEMP = "0105",  //A-40
//            SH_TERM_FUEL_TRIM_1 = "0106", // (A / 1.28) - 100
//            LN_TERM_FUEL_PERCENT_TRIM_1 = "0107", // (A / 1.28) - 100
//            SH_TERM_FUEL_TRIM_2 = "0108", // (A / 1.28) - 100
//            LN_TERM_FUEL_PERCENT_TRIM_2 = "0109", // (A / 1.28) - 100
//            FUEL_PRESSURE = "010A", // 3*A
//            INTAKE_MAN_PRESSURE = "010B", // A,  Intake manifold absolute pressure 0 - 255 kPa
//            ENGINE_RPM = "010C",  //((A*256)+B)/4
//            VEHICLE_SPEED = "010D",  //A
//            TIMING_ADVANCE = "010E", // (A / 2) - 64
//            INTAKE_AIR_TEMP = "010F",  //A-40
//            MAF_AIR_FLOW = "0110", //MAF air flow rate 0 - 655.35	grams/sec ((256*A)+B) / 100  [g/s]
//            THROTTLE_POSITION = "0111", //Throttle position 0 -100 % A*100/255
//            OXY_SENS_VOLT_B_1_SENS_1 = "0114", // (A / 200) - voltage; (100 / 128) * B - 100 - short term fuel trim
//            OXY_SENS_VOLT_B_1_SENS_2 = "0115",
//            OXY_SENS_VOLT_B_1_SENS_3 = "0116",
//            OXY_SENS_VOLT_B_1_SENS_4 = "0117",
//            OXY_SENS_VOLT_B_2_SENS_1 = "0118",
//            OXY_SENS_VOLT_B_2_SENS_2 = "0119",
//            OXY_SENS_VOLT_B_2_SENS_3 = "011A",
//            OXY_SENS_VOLT_B_2_SENS_4 = "011B",
//            FUEL_RAIL_PRESSURE = "0122", // ((A*256)+B)*0.079
//            FUEL_RAIL_PRESSURE_DIESEL = "0123", // 10 * (256 * A + B)
//            COMMANDED_EGR = "012C", // 	A*100/255
//            FUEL_LEVEL = "012F", // FUEL level 	0-100%	A*100/255
//            BAROMETRIC_PRESSURE = "0133", // A
//            CATALYST_TEMP_B1S1 = "013C",  // (((A*256)+B)/10)-40
//            CATALYST_TEMP_B2S1 = "013D", // (((A*256)+B)/10)-40
//            CATALYST_TEMP_B1S2 = "013E", // (((A*256)+B)/10)-40
//            CATALYST_TEMP_B2S2 = "013F", // (((A*256)+B)/10)-40
//            THROTTLE_POS_2 = "0145", // A*100 / 255
//            ENGINE_OIL_TEMP = "015C",  //A-40
//
//
//            CONT_MODULE_VOLT = "0142",  //((A*256)+B)/1000
//            AMBIENT_AIR_TEMP = "0146",  //A-40
//            STATUS_DTC = "0101", //Status since DTC Cleared
//            OBD_STANDARDS = "011C", //OBD standards this vehicle
//            PIDS_SUPPORTED = "0120"; //PIDs supported

    private boolean tryconnect = false;
    private boolean connected = false;
    private boolean initialized = false;
    private boolean checkedPids = false;

    private int whichCommand = 0;
    private int connectcount = 0;
    private int trycount = 0;
    private int Enginetype = 0;
    private double Enginedisplacement = 1500;

//    private int m_dedectPids = 0;
//    private int rpmval = 0;
//    private int intakeairtemp = 0;
//    private int ambientairtemp = 0;
//    private int coolantTemp = 0;
//    private int engineoiltemp = 0;
//    private int b1s1temp = 0;

    private String devicename = null;
    private String deviceadress = null;
    private String deviceprotocol = null;
    private String mConnectedDeviceName = "Ecu";

    public OBDReader(Context context) {
        this.context = context;
        whichCommand = 0;

        //ATZ reset all
        //ATDP Describe the current Protocol
        //ATAT0-1-2 Adaptive Timing Off - adaptive Timing Auto1 - adaptive Timing Auto2
        //ATE0-1 Echo Off - Echo On
        //ATSP0 Set Protocol to Auto and save it
        //ATMA Monitor All
        //ATL1-0 Linefeeds On - Linefeeds Off
        //ATH1-0 Headers On - Headers Off
        //ATS1-0 printing of Spaces On - printing of Spaces Off
        //ATAL Allow Long (>7 byte) messages
        //ATRD Read the stored data
        //ATSTFF Set time out to maximum
        //ATSTHH Set timeout to 4ms
        initializeCommands
                = Arrays.asList("ATZ", "ATL0", "ATE1", "ATH1", "ATAT1", "ATSTFF", "ATI", "ATDP");
        protocolNum = 0;
        supportedPids = new ArrayList<>();

        defaultCommandsList.clear();
        defaultCommandsList.add(VOLTAGE);
        defaultCommandsList.add(PID.PIDS_SUP_0_20.getCommand());
        defaultCommandsList.add(PID.PIDS_SUP_21_40.getCommand());
        defaultCommandsList.add(PID.PIDS_SUP_41_60.getCommand());
        defaultCommandsList.add(PID.VEHICLE_SPEED.getCommand());
        defaultCommandsList.add(PID.ENGINE_RPM.getCommand());
        defaultCommandsList.add(PID.FREEZE_DTCS.getCommand());
//        defaultCommandsList.addAll(Arrays.asList(PidItem.PIDS));

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothService = new BluetoothService(context, mBtHandler);

        if (bluetoothAdapter == null) {
            Toast.makeText(context, R.string.no_bt_connection, Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothService != null) {
                if (bluetoothService.getState() == BluetoothService.STATE_NONE) {
                    bluetoothService.start();
                }
            }
        }
    }

    public void connectDevice(String address, String name) {
        tryconnect = true;
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        try {
            // Attempt to connect to the device
            bluetoothService.connect(device);
            currentdevice = device;

        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.didnt_connect_to) + name, Toast.LENGTH_SHORT).show();
        }
    }

    public void disconnect() {
        if (bluetoothService != null) {
            bluetoothService.stop();
            onReceiveDataListener.onDisconnected();
        }
    }

//    public void sendCommandForCheckPids() {
//        sendEcuMessage(PID.PIDS_SUP_0_20.getCommand());
//        sendEcuMessage(PID.PIDS_SUP_21_40.getCommand());
//        sendEcuMessage(PID.PIDS_SUP_41_60.getCommand());
//    }

    public boolean isConnected() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled()
                && bluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }

    public void reset() {
        whichCommand = 0;
        initialized = false;
        avgconsumption.clear();

        sendEcuMessage(RESET);
    }

    public void getTroubleCodes() {
        sendEcuMessage(PID.SAVED_TROUBLE_CODES.getCommand());
    }

    public void cleanSavedTroubleCodes() {
        sendEcuMessage(PID.CLEAN_SAVED_TROUBLE_CODES.getCommand());
    }

    public void sendEcuMessage(String message) {
//        if (mWifiService != null) {
//            if (mWifiService.isConnected()) {
//                try {
//                    if (phone.length() > 0) {
//                        phone = phone + "\r";
//                        byte[] send = phone.getBytes();
//                        mWifiService.write(send);
//                    }
//                } catch (Exception e) {
//                }
//            }
//        } else if (mBtService != null) {
        // Check that we're actually connected before trying anything
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Log.d(TAG, "INFO: " + context.getString(R.string.not_connected));
            return;
        }
        try {
            if (message.length() > 0) {

                message = message + "\r";
                // Get the phone bytes and tell the BluetoothChatService to write
                byte[] send = message.getBytes();
                bluetoothService.write(send);
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.error_send_message_ecu, Toast.LENGTH_SHORT).show();
        }
//        }
    }

    private void analysMsg(Message msg) {
        String tmpmsg = clearMsg(msg);

        generateVolt(tmpmsg);
        getElmInfo(tmpmsg);
        // if not initialized
        if (!initialized) {
            sendInitCommands();
        } else {
//            if (trycount < 10) {
//                sendCommandForCheckPids();
//                trycount++;
//            }

            checkPids(tmpmsg);
            getFaultInfo(tmpmsg);
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
            if (onReceiveDataListener != null) {
                onReceiveDataListener.onReceiveVoltage(VoltText);

            }
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

//            Toast.makeText(context, "Device name: " + devicename, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "Device protocol: " + deviceprotocol, Toast.LENGTH_SHORT).show();
//            Status.setText(devicename + " " + deviceprotocol);
        }
    }

    private void sendInitCommands() {
        if (initializeCommands.size() != 0) {

            if (!initializeCommands.get(initializeCommands.size() - 1).contains(SET_PROTOCOL))
                initializeCommands.add(SET_PROTOCOL + protocolNum);

            if (whichCommand < 0) {
                whichCommand = 0;
            }

            String send = initializeCommands.get(whichCommand);
            sendEcuMessage(send);

            if (whichCommand == initializeCommands.size() - 1) {
                initialized = true;
                whichCommand = 0;
                sendDefaultCommands();
            } else {
                whichCommand++;
            }
        }
    }

    private void sendDefaultCommands() {

        // Add supported pids to default command list
        for (String item: supportedPids) {
            if (!defaultCommandsList.contains(item))
                defaultCommandsList.add(item);
        }

        if (defaultCommandsList.size() != 0) {

            if (whichCommand < 0) {
                whichCommand = 0;
            }

            String send = defaultCommandsList.get(whichCommand);
            Log.d(TAG, "sendDefaultCommands: send " + send);
            sendEcuMessage(send);

            if (whichCommand >= defaultCommandsList.size() - 1) {
                whichCommand = 0;
            } else {
                whichCommand++;
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
                String faultCode;

                if (troubleCodesArray.size() > 0) {
                    for (int i = 0; i < troubleCodesArray.size(); i++) {
                        faultCode = troubleCodesArray.get(i);

                        if (faultCode != null) {
                            if (onReceiveFaultCodeListener != null) {
                                onReceiveFaultCodeListener.onReceiveFaultCode(faultCode);
                                Log.d(TAG, "getFaultInfo: " + faultCode);
                            }
                        }
                    }
                } else {
                    faultCode = "No error found...";
                }
            }
        }
    }

    private void performCalculations(String fault) {
        final String result = fault;
        String workingData = "";
        int startIndex = 0;
        troubleCodesArray.clear();

        try {
            if (result.contains("43")) {
                workingData = result.replaceAll("^43|[\r\n]43|[\r\n]", "");
            } else if (result.contains("47")) {
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
                troubleCodesArray.add(dtc);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    private void checkPids(String tmpmsg) {
        if (tmpmsg.contains("41")) {
            int index = tmpmsg.indexOf("41");

            if (tmpmsg.contains("0100") || tmpmsg.contains("0120") ||
                    tmpmsg.contains("0140") || tmpmsg.contains("0160")) {

                String pidmsg = tmpmsg.substring(index, tmpmsg.length());
                setSupportedPids(pidmsg);
            }
        }
    }

    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    private void setSupportedPids(String buffer) {
            String buf = buffer.toString();
            buf = buf.trim();
            buf = buf.replace("\t", "");
            buf = buf.replace(" ", "");
            buf = buf.replace(">", "");

            int index;

            Log.d(TAG, "setSupportedPids: " + buf);
            if ((index = buf.indexOf("4100")) == 0) {
                buf = buf.substring(index + 4, index + 12);
                int decNum = hex2decimal(buf);
                String binary = Integer.toBinaryString(decNum);

                for (int i = 1; i < binary.length() + 1; i++) {
                    if (binary.charAt(i - 1) == '1')
                        if (!supportedPids.contains(PidItem.PIDS[i]))
                            supportedPids.add(PidItem.PIDS[i]);
                }

            } else if ((index = buf.indexOf("4120")) == 0) {
                buf = buf.substring(index + 4, index + 12);
                int decNum = hex2decimal(buf);
                String binary = Integer.toBinaryString(decNum);

                for (int i = 1; i < binary.length() + 1; i++) {
                    if (binary.charAt(i - 1) == '1')
                        if (!supportedPids.contains(PidItem.PIDS[i + 32]))
                            supportedPids.add(PidItem.PIDS[i + 32]);
                }
            } else if ((index = buf.indexOf("4140")) == 0) {
                buf = buf.substring(index + 4, index + 12);
                int decNum = hex2decimal(buf);
                String binary = Integer.toBinaryString(decNum);

                for (int i = 1; i < binary.length() + 1; i++) {
                    if (binary.charAt(i - 1) == '1')
                        if (!supportedPids.contains(PidItem.PIDS[i + 64]))
                            supportedPids.add(PidItem.PIDS[i + 64]);
                }
            }

            Log.d(TAG, "setSupportedPids: " + supportedPids.toString());
    }

    private boolean hasPidInSupportedSuchAs(String pid) {
        for (String item : supportedPids) {
            if (item.equals(pid))
                return true;
        }
        return false;
    }

    private void calculateEcuValues(int pidInDec, int A, int B) {
        String pidStr = String.format("%s%02X", "01", pidInDec);

        if (PidItem.contains(pidStr)) {
            PID pid = PID.getEnumByString(pidStr);
            PidItem pidItem = new PidItem(pid);

            if (onReceiveDataListener != null) {
                onReceiveDataListener.onReceiveData(pidItem.getPid(), pidItem.getValue(A, B));
            }
        }
//            case PIDS_SUP_0_20:
//                return "";
//            case DTCS_CLEARED_MIL_DTCS :
//                return "";
//            case FREEZE_DTCS:
//                return "";
//            case FUEL_SYSTEM_STATUS :
//                return "";
//            case COMMANDED_SECONDARY_AIR_STATUS :
//                return "";
//            case OXY_SENS_PRESENT_2_BANKS:
//                return "";
//            case OBD_STANDARDS_VEHICLE_CONFORMS_TO :
//                return "";
//            case OXY_SENS_PRESENT_4_BANKS:
//                return "";
//            case AUXILIARY_INPUT_STATUS:
//                return "";
//            case PIDS_SUP_21_40  :
//                return "";
//            case PIDS_SUP_41_60  :
//                return "";
//            case MONITOR_STATUS_THIS_DRIVE_CYCLE :
//                return "";
//            case MAX_VAL_FOR_FUEL_AIR_EQUIVALENCE_RATIO_OXY_SENS_VOLT_OXY_SENS_CURRENT_INTAKE_MANIFOLD_PRESSURE:
//                return "";
//            case MAX_VAL_FOR_AIR_FLOW_RATE_FROM_MASS_AIR_FLOW_SENS:
//                return "";
//            case FUEL_TYPE:
//                return "";
//            case EMISSION_REQUIREMENTS_TO_WHICH_VEHICLE_IS_DESIGNED:
//                return "";
//            case PIDS_SUP_61_80:
//                return "";
//            case ENGINE_PERCENT_TORQUE_DATA:
//                return "";
//            case AUXILIARY_IN_OUT_SUPPORTED:
//                return "";
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
                            Log.d(TAG, "Status: " + context.getString(R.string.title_connected_to, mConnectedDeviceName));
                            Log.d(TAG, "INFO: " + context.getString(R.string.title_connected));

                            Toast.makeText(context,
                                    context.getString(R.string.title_connected_to, mConnectedDeviceName), Toast.LENGTH_SHORT).show();

                            onReceiveDataListener.onConnected(mConnectedDeviceName);

                            connected = true;
                            tryconnect = false;
                            reset();
                            sendEcuMessage(RESET);
                            break;
                        case BluetoothService.STATE_CONNECTING:

                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            connected = true;
                            if (tryconnect) {
                                bluetoothService.connect(currentdevice);
                                connectcount++;
                                if (connectcount >= 2) {
                                    tryconnect = false;
                                }
                            }
                            reset();
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    Log.d(TAG, "Handle phone/ your-> " + writeMessage);
                    break;
                case MESSAGE_READ:
                    String tmpmsg = clearMsg(msg);
//                    Info.setText(tmpmsg);
//                    if (tmpmsg.contains(RSP_ID.NODATA.response) || tmpmsg.contains(RSP_ID.ERROR.response)) {
//
//                        try{
//                            String command = tmpmsg.substring(0,4);
//
//                            if(isHexadecimal(command))
//                            {
//                                removePID(command);
//                            }
//
//                        }catch(Exception e)
//                        {
//                            Toast.makeTextgetActivity(), e.getPhone(),
//                                Toast.LENGTH_LONG).show();
//                        }
//                    }
                    Log.d(TAG, "Handle phone/ Adapter say-> " + tmpmsg);
                    analysMsg(msg);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(context, msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public List<String> getSupportedPids() {
        return supportedPids;
    }

    public void setSupportedPids(List<String> supportedPids) {
        this.supportedPids = supportedPids;
    }

    public List<String> getInitializeCommands() {
        return initializeCommands;
    }

    public void setInitializeCommands(List<String> initializeCommands) {
        reset();
        this.initializeCommands = initializeCommands;
    }

    public OnReceiveFaultCodeListener getOnReceiveFaultCodeListener() {
        return onReceiveFaultCodeListener;
    }

    public void setOnReceiveFaultCodeListener(OnReceiveFaultCodeListener onReceiveFaultCodeListener) {
        this.onReceiveFaultCodeListener = onReceiveFaultCodeListener;
    }

    public OnReceiveDataListener getOnReceiveDataListener() {
        return onReceiveDataListener;
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public BluetoothService getBluetoothService() {
        return bluetoothService;
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public int getProtocolNum() {
        return protocolNum;
    }

    public void setProtocolNum(int protocolNum) {
        this.protocolNum = protocolNum;
    }
}
