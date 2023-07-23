package com.caravan.huntercaravantabletuygulamasii.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.caravan.huntercaravantabletuygulamasii.HomeScreen;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GelismisUygulamaAyarlari extends AppCompatActivity {
    ImageView diagnostikbtn;
    ImageView eslesmebtn;
    TextView eslesmeText;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnablingIntent;
    int requestCodeForeEnable;
    BluetoothDevice[] btArray;
    private Switch switchView;
    ListView pairedlist;
    Set<String> list = new HashSet<String>();

    ArrayList<String> list1 = new ArrayList<String>();
    private Set<BluetoothDevice> pairedDevice;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelismis_uygulama_ayarlari);
        int currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        diagnostikbtn = findViewById(R.id.diagnostikbtn);
        switchView = findViewById(R.id.bluetooth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        eslesmebtn = findViewById(R.id.eslesmebtn);
        eslesmeText = findViewById(R.id.eslesmetext);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForeEnable = 1;


        diagnostikbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GelismisUygulamaAyarlari.this, DiagnostikMenu.class);
                startActivity(intent);
                finish();
            }
        });


        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // on below line we are checking
                // if switch is checked or not.
                if (isChecked) {
                    // on below line we are setting text
                    // if switch is checked.
                    if (myBluetoothAdapter == null) {
                        Toast.makeText(getApplicationContext(), "Bluetooth does not support", Toast.LENGTH_LONG).show();


                    } else {

                        if (!myBluetoothAdapter.isEnabled()) {

                            startActivityForResult(btEnablingIntent, requestCodeForeEnable);


                        }





                    }
                } else {


                    if (ActivityCompat.checkSelfPermission(GelismisUygulamaAyarlari.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        myBluetoothAdapter.disable();

                        return;
                    }

                    Toast.makeText(getApplicationContext(), "kapat", Toast.LENGTH_LONG).show();


                    // on below line we are setting text
                    // if switch is unchecked.

                }
            }
        });

        SharedPreferences prefs = getSharedPreferences("Bluetoothcihazii",MODE_PRIVATE);
        String cihazid = prefs.getString("cihazId","");
        eslesmeText.setText(cihazid);

        SharedPreferences pre = getSharedPreferences("Bluetoothcihazadi", MODE_PRIVATE);
        String cihazadi = pre.getString("cihazadi", "");






       // @string/bluetootheslestirme

        eslesmebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             listDevice();



            }
        });

    }

    private void listDevice() {

       if(myBluetoothAdapter.isEnabled()){

          if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {



                pairedDevice = myBluetoothAdapter.getBondedDevices();


        if (pairedDevice.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice bt : pairedDevice) {
                String deviceName = bt.getName();
                Log.e("BT", "GELENN" + deviceName + "\n" + bt.getAddress());
                list.add(bt.getName());
                list1.add(bt.getAddress());


                SharedPreferences.Editor editor = getSharedPreferences("Bluetoothcihazadi", MODE_PRIVATE).edit();
                editor.putString("cihazadi", String.valueOf(list));

                editor.apply();


            }
        } else {
            Toast.makeText(this, "Eşleşmiş cihaz yok", Toast.LENGTH_SHORT).show();
        }


    }
}


if(myBluetoothAdapter.isEnabled()) {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
    mBuilder.setTitle("Choose Device");

    mBuilder.setItems(list.toArray(new String[0]), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SharedPreferences.Editor editor = getSharedPreferences("Bluetoothcihazii", MODE_PRIVATE).edit();
            editor.putString("cihazId", list1.get(i));
            editor.apply();
            SharedPreferences.Editor editor1 = getSharedPreferences("Bluetoothcihazadi", MODE_PRIVATE).edit();
            editor1.putString("cihazadi", list1.get(i));
            editor1.apply();


            SharedPreferences prefs = getSharedPreferences("Bluetoothcihazii", MODE_PRIVATE);
            String cihazid = prefs.getString("cihazId", "");
            eslesmeText.setText(cihazid);


        }
    });
    AlertDialog mDialog = mBuilder.create();
    mDialog.show();
}else {
    Toast.makeText(this,"Bluetooth not connect",Toast.LENGTH_SHORT).show();
}
        }











    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}