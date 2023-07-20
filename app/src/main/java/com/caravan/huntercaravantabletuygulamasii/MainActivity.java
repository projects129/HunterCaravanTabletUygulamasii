package com.caravan.huntercaravantabletuygulamasii;

import androidx.appcompat.app.AppCompatActivity;



import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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


import com.caravan.huntercaravantabletuygulamasii.fragments.AydinlatmaFragment;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.Collection;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static char outputs_data;
    public static boolean output_update;
    AnimationDrawable drawableAnimation;
 // After completion of 2000 ms, the next activity will get started.

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
    public static char inputsdat,old_inputsdat;


    public static int v_batt;
    public static int v_solar;
    public static int cl_water_lvl;
    public static int dt_water_lvl;
    public static int t_in;
    public static int t_out;
    public static int dht_temp;
    public static int dht_humidty;
    private static final int SPLASH_SCREEN_TIME_OUT = 6600; // After completion of 2000 ms, the next activity will get started.


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = (ImageView) findViewById(R.id.progressBar);
        ClipDrawable drawable = (ClipDrawable) image.getDrawable();
        drawable.setLevel(0);
        loadLocale();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        drawable.setLevel(drawable.getLevel()+100);

                    }
                });
            }
        }, 10,50);

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
        timer.schedule(timerTask,0,100);
    }

    private void onMessageSent(String message) {

    }

    private void onMessageReceived(String message) {
        char[] in_buf=message.toCharArray();

        if(in_buf[20]==0x55)
        {
            if(in_buf[21]==0x42)
            {/*
                Log.d("BT", "Input buffer length:"+in_buf.length);
                Log.d("BT","Input buffer:"+(int)in_buf[0]+"-"+(int)in_buf[1]+"-"+(int)in_buf[2]+"-"+(int)in_buf[3]+"-"+(int)in_buf[4]+"-"+(int)in_buf[5]+"-"+(int)in_buf[6]+"-"+(int)in_buf[7]+
                        "-"+(int)in_buf[8]+"-"+(int)in_buf[9]+"-"+(int)in_buf[10]+"-"+(int)in_buf[11]+"-"+(int)in_buf[12]+"-"+(int)in_buf[13]+"-"+(int)in_buf[14]+"-"+(int)in_buf[15]+
                        "-"+(int)in_buf[16]+"-"+(int)in_buf[17]+"-"+(int)in_buf[18]+"-"+(int)in_buf[19]+"-"+(int)in_buf[20]+"-"+(int)in_buf[21]+"-"+(int)in_buf[22]+"-"+(int)in_buf[23]);*/
                inputsdat= (char) ((in_buf[3]<<8)|in_buf[2]);
                v_batt=(int)((in_buf[5]<<8)|in_buf[4]);
                v_solar=(int)((in_buf[7]<<8)|in_buf[6]);
                cl_water_lvl=(int)((in_buf[9]<<8)|in_buf[8]);
                dt_water_lvl=(int)((in_buf[11]<<8)|in_buf[10]);
                t_in=(int)((in_buf[13]<<8)|in_buf[12]);
                t_out=(int)((in_buf[15]<<8)|in_buf[14]);
                dht_temp=(int)((in_buf[17]<<8)|in_buf[16]);
                dht_humidty=(int)((in_buf[19]<<8)|in_buf[18]);

                Log.d("ANALOG","Vbatt:"+v_batt+" v_solar:"+v_solar+" cl_water_lvl:"+cl_water_lvl+" dt_water_lvl:"+dt_water_lvl+" t_in:"+t_in+" t_out:"+t_out+" dht_temp:"+dht_temp+" dht_humidty:"+dht_humidty);
            }
        }
    }
    private void onError(Throwable error) {
        // Handle the error
    }
    private void setLocale(String s) {
        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE).edit();
        editor.putString("my lang",s);
        editor.putString("languagetext",s);
        if(s.equals("en")){
            editor.putInt("image", R.drawable.ingilizce);

        }
        else if(s.equals("tr")){
            editor.putInt("image", R.drawable.turkiye);

        }
        else {

            editor.putInt("image", R.drawable.almanca);


        }
        editor.apply();

    }
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("setting",MODE_PRIVATE);
        String language = prefs.getString("my lang","");
        setLocale(language);




    }
}