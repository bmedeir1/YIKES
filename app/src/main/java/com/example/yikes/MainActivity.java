package com.example.yikes;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.app.Application;


//This class is never used but could replace inner class- connect thread in My Bluetooth Service.

//Program Arduino to send signal on pressure, then when the app receives the signal, it plays the default ringtone.



import com.example.yikes.Bluetooth.MyBluetoothService;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public boolean flag;
    protected YIKES yikes;
    BluetoothAdapter bluetoothAdapter;
    Handler handler;
    TextView connect_status;
    TextView value;
    TextView connect_switch;
    public static BluetoothDevice mmBluetoothDevice;
    private static final String TAG = "MY_APP_DEBUG_TAG";
//    public static boolean initialized= false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initialized = true;

        yikes = (YIKES)this.getApplicationContext();
        yikes.setCurrentActivity(this);
        this.flag = false;
//        appConstantsObj.setCurrentActivity(this);

        connect_status = findViewById(R.id.connect_status);
        value = findViewById(R.id.value);
        connect_switch = findViewById(R.id.swtich);


        if (mmBluetoothDevice == null) {


            Boolean blt_supported = true;

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth
                Log.d("TAG", "Couldn't establish bluetooth connection");
                blt_supported = false;
            }

            Log.d("TAG", "here 1");
            int REQUEST_ENABLE_BT = 1;
//        int attempts = 0;
//        while (blt_supported && REQUEST_ENABLE_BT != RESULT_OK) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }
//            ++attempts;
//        }

//        if (attempts >= 5) {
//            System.out.println("Couldn't enable bluetooth");
//        }

            String deviceAddress;

            Log.d("TAG", "HERE 2");
            if (bluetoothAdapter.isEnabled()) {
                Log.d("TAG", "HERE 3");
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                String deviceName = "HC-05";


                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        Log.i("TAG", "Device name " + device.getName());
                        if (device.getName().equals(deviceName)) {
                            deviceAddress = device.getAddress(); // MAC address
                            mmBluetoothDevice = device;
                            break;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }


            }

            Log.i("TAG", "Selected Device name " + mmBluetoothDevice.getName());

        }

        if (mmBluetoothDevice != null) {
//            Looper.getMainLooper();
//            Looper.prepare();
            handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {

//                    if (!flag) {


                        Log.d(TAG, msg.toString());
                        value.setText("" + msg.what);
                        connect_status.setText("Connected");
                        if (msg.what == 0) {
                            connect_switch.setText("ON");
                            Intent intent = new Intent(MainActivity.this, CallActivity.class);
                            flag = true;
                            startActivity(intent);

                        }

                    }

//                }
            };
            MyBluetoothService mmMyBluetoothService = new MyBluetoothService(mmBluetoothDevice, handler);

        }


    }


}
