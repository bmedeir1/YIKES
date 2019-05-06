package com.example.yikes;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean blt_supported = true;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            System.out.println("Couldn't establish bluetooth connection");
            blt_supported = false;
        }

        int REQUEST_ENABLE_BT = 1;
        int attempts = 0;
        while (blt_supported && REQUEST_ENABLE_BT != RESULT_OK && attempts < 5) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }
            ++attempts;
        }

        if (attempts >= 5) {
            System.out.println("Couldn't enable bluetooth");
        }

        if (blt_supported && REQUEST_ENABLE_BT == RESULT_OK) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            String deviceName = "HC-05";
            String deviceAddress;

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
//                    String deviceName = device.getName();
                    if (device.getName() == deviceName) {
                        deviceAddress = device.getAddress(); // MAC address
                        break;
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
            }

        }

    }


}
