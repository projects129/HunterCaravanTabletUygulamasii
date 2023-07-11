package com.caravan.huntercaravantabletuygulamasii;

import androidx.appcompat.app.AppCompatActivity;



import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static char outputs_data;
    public static boolean output_update;
    AnimationDrawable drawableAnimation;
    private static final int SPLASH_SCREEN_TIME_OUT = 4600; // After completion of 2000 ms, the next activity will get started.

    Context context = this;
    private SimpleBluetoothDeviceInterface deviceInterface;
    BluetoothManager bluetoothManager = BluetoothManager.getInstance();
    String my_device_mac;
    //char outputs_data = 0x0000;
    char[] ouput_update_buf={0x55,0x74,0x00,0x00};
    char[] input_read_buf={0x55,0x41,0x00,0x00};
    boolean my_device_exist=false;
    //boolean output_update=false;
    Timer timer;
    AnimationDrawable drawableAnimation;
    private static final int SPLASH_SCREEN_TIME_OUT = 6600; // After completion of 2000 ms, the next activity will get started.


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = (ImageView) findViewById(R.id.progressBar);
        ClipDrawable drawable = (ClipDrawable) image.getDrawable();
        drawable.setLevel(0);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        drawable.setLevel(drawable.getLevel()+500);

                    }
                });
            }
        }, 10,200);

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

       if (bluetoothManager == null) {
            // Bluetooth unavailable on this device :( tell the user
            Toast.makeText(context, "Bluetooth not available.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        }
        Collection<BluetoothDevice> pairedDevices = bluetoothManager.getPairedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if(device.getName().equals("Menar IO Module"))
            {
                my_device_mac=device.getAddress();
                my_device_exist=true;
                Log.d("BT","My device found:" +my_device_mac);
            }
        }
        if(my_device_exist)
        {
            connectDevice(my_device_mac);
        }
        else
        {
            Toast.makeText(context, "Menar IO Module not found.", Toast.LENGTH_LONG).show();
        }

        Runnable bekleme = new Runnable() {
            @Override
            public void run() {
                nextActivity();

            }
        };

        Handler isleyici = new Handler();
        isleyici.postDelayed(bekleme,4700);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i); // invoke the SecondActivity.
                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }





    public void nextActivity(){
        ImageView imageView = (ImageView) findViewById(R.id.maske);
          ImageView image = findViewById(R.id.progressBar);
        ImageView Glass1=findViewById(R.id.mask);
        ImageView hunter = findViewById(R.id.hunter);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move);
        Glass1.startAnimation(animation1);
         hunter.startAnimation(animation1);

        Glass1.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);
        hunter.setVisibility(View.VISIBLE);


    }




    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(output_update)
            {
                output_update=false;
                ouput_update_buf[3]= (char) ((outputs_data&0xFF00)>>8);
                ouput_update_buf[2]= (char) (outputs_data&0x00FF);
                String message = new String(ouput_update_buf);
                deviceInterface.sendMessage(message);
            }
            else
            {
                String message = new String(input_read_buf);
                deviceInterface.sendMessage(message);
            }
        }
    };
    private void onConnected(BluetoothSerialDevice connectedDevice) {
        deviceInterface = connectedDevice.toSimpleDeviceInterface();
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
        Toast.makeText(context, "Menar IO Module Connected.", Toast.LENGTH_LONG).show();
        timer = new Timer();
        timer.schedule(timerTask,200,200);
    }

    private void onMessageSent(String message) {

    }

    private void onMessageReceived(String message) {
        char[] in_buf=message.toCharArray();
        Log.d("BT",message);
        Log.d("BT","Input buffer:"+(int)in_buf[0]+"-"+(int)in_buf[1]+"-"+(int)in_buf[2]+"-"+(int)in_buf[3]);
        if(in_buf[0]==0x55)
        {
            if(in_buf[1]==0x42)
            {
                char inputsdat= (char) ((in_buf[3]<<8)|in_buf[2]);
                set_input_views(inputsdat);
                Log.d("BT","Inputs DATA:"+Integer.toHexString(inputsdat));
            }
        }
    }

    public void set_input_views(char dat)
    {/*
        for(int j=0;j<16;j++)
        {
            if((dat&(1<<j))>0)INPUT_VIEWS[j].setChecked(false);
            else INPUT_VIEWS[j].setChecked(true);
        }*/
    }
    private void onError(Throwable error) {
        // Handle the error
    }

}