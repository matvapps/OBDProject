package com.carzis.connect;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.repository.local.prefs.KeyValueStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by Alexandr.
 */
public class ConnectActivity extends BaseActivity {

    private static final String TAG = ConnectActivity.class.getSimpleName();

    public static final UUID BluetoothSerialUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final boolean D = true;
    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String EXTRA_DEVICE_NAME = "device_name";
    // Member fields
//    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
//    private ArrayAdapter<String> mNewDevicesArrayAdapter;


    private Calendar _discoveryStartTime;
    private SimpleDateFormat _logDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.getDefault());

    private List<BluetoothDevice> newBTDevices;

    private ImageView iconBth;
    private PulsatorLayout pulsatorLayout;
    private KeyValueStorage keyValueStorage;

    private static final int REQUEST_ENABLE_BT = 3;
    private static final int REQUEST_GET_CONNECT_DEVICE_DATA = 24;

    private BluetoothAdapter mBluetoothAdapter = null;
    private Intent serverIntent;


//    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
//            // Cancel discovery because it's costly and we're about to connect
//            mBtAdapter.cancelDiscovery();
//
//            // Get the device MAC address, which is the last 17 chars in the View
//            String info = ((TextView) v).getText().toString();
//            String address = info.substring(info.length() - 17);
//
//            // Create the result Intent and include the MAC address
//            Intent intent = new Intent();
//            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
//
//            // Set result and finish this Activity
//            setResult(Activity.RESULT_OK, intent);
//            finish();
//        }
//    };


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConnectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);


        this.setFinishOnTouchOutside(false);
        iconBth = findViewById(R.id.bth_icon);
        pulsatorLayout = findViewById(R.id.pulsator);

        keyValueStorage = new KeyValueStorage(ConnectActivity.this);

        // every time ask user for his car name
        if (!keyValueStorage.getCurrentCarName().isEmpty())
            keyValueStorage.setCurrentCarName("");

        pulsatorLayout.start();

//        iconBth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                connectBt();
//            }
//        });

        connectBt();
        newBTDevices = new ArrayList<>();

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }


    }


    public void connectBt() {
//        if (mWifiService != null) {
//            if (mWifiService.isConnected()) {
//                Toast.makeText(getActivity(), "First Disconnect WIFI Device.", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        if (!mBluetoothAdapter.isEnabled()) {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            return;
//        }


//        doDiscovery();
//        // Launch the DeviceListActivity to see devices and do scan
//        serverIntent = new Intent(this, DeviceListActivity.class);
//        startActivityForResult(serverIntent, REQUEST_GET_CONNECT_DEVICE_DATA);
    }


    private void doDiscovery() {
        if (D) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
//        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
//        newDevicesTitle.setVisibility(View.VISIBLE);
//        newDevicesTitle.setText(R.string.scanning);

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

//        if (bluetoothService != null) bluetoothService.stop();

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    newBTDevices.add(device);
                    Log.d(TAG, "onReceive: new device: " + device.getName() + ", " + device.getAddress());
//                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
//                setTitle(R.string.select_device);
                /*for (BluetoothDevice device : mBluetoothAdapter.getBondedDevices())
                {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }*/

                // Trawl through the logs to find any devices that were skipped >:(
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d -v time *:E");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    String line;
                    Pattern pattern = Pattern.compile("(.{18}).*\\[(.+)\\] class is 0x00 - skip it.");
                    while ((line = bufferedReader.readLine()) != null) {
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            // Found a blocked device, check if it was newly discovered.
                            // Android log timestamps don't contain the year!?
                            String logTimeStamp = Integer.toString(_discoveryStartTime.get(Calendar.YEAR)) + "-" + matcher.group(1);
                            Date logTime = null;
                            try {
                                logTime = _logDateFormat.parse(logTimeStamp);
                            } catch (ParseException e) {
                            }

                            if (logTime != null) {
                                if (logTime.after(_discoveryStartTime.getTime())) {
                                    // Device was discovered during this scan,
                                    // now we want to get the name of the device.
                                    String deviceAddress = matcher.group(2);
                                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceAddress);

                                    // In order to get the name, we must attempt to connect to the device.
                                    // This will attempt to pair with the device, and will ask the user
                                    // for a PIN device_id if one is required.
                                    try {
                                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(BluetoothSerialUuid);
                                        socket.connect();
                                        socket.close();
                                        newBTDevices.add(device);
                                        Log.d(TAG, "onReceive: new device: " + device.getName() + ", " + device.getAddress());
//                                        mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                }


//                if (mNewDevicesArrayAdapter.getCount() != 0) {
//                    Log.d(TAG, "onReceive: Other Available Devices");
////                    newDevicesTitle.setText(R.string.title_other_devices);
//                } else {
//                    Log.d(TAG, "onReceive: No devices found");
////                    newDevicesTitle.setText(R.string.none_found);
//                }
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_GET_CONNECT_DEVICE_DATA:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == RESULT_OK) {

                    String address = data.getStringExtra(EXTRA_DEVICE_ADDRESS);
                    String name = data.getStringExtra(EXTRA_DEVICE_NAME);

                    // Create the result Intent and include the MAC address
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                    intent.putExtra(EXTRA_DEVICE_NAME, name);

                    // Set result and finish this Activity
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                }
                break;

            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {
                    serverIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_GET_CONNECT_DEVICE_DATA);
                } else {
                    Toast.makeText(this, R.string.bt_not_enabled, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}