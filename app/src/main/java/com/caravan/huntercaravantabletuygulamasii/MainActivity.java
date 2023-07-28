package com.caravan.huntercaravantabletuygulamasii;

import static java.lang.Math.round;

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
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
    public static boolean output_update,humidty_set_update=false;
    AnimationDrawable drawableAnimation;
 // After completion of 2000 ms, the next activity will get started.

    Context context = this;
    private SimpleBluetoothDeviceInterface deviceInterface;
    BluetoothManager bluetoothManager = BluetoothManager.getInstance();
    String my_device_mac,my_device_name;
    //char outputs_data = 0x0000;
    char[] ouput_update_buf={0x55,0x74,0x00,0x00};
    char[] input_read_buf={0x55,0x41,0x00,0x00};

    char[] humidity_set_update_buf={0x55,0x53,0x00,0x00};
    boolean my_device_exist=false;
    //boolean output_update=false;
    Thread Thread_Comm = null;
    Boolean bluetooth_connected=false;
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


        // bitSet(test,0);
        //  Log.d("Bit_test:","Val: "+bitRead(test,0)+"("+Integer.toHexString(test)+")");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawable.setLevel(drawable.getLevel() + 100);

                    }
                });
            }
        }, 10, 50);

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
        SharedPreferences prefs = getSharedPreferences("Bluetoothcihazii", MODE_PRIVATE);
        my_device_mac = prefs.getString("cihazId", "");
        my_device_mac=my_device_mac.replace("[","");
        my_device_mac=my_device_mac.replace("]","");
        SharedPreferences pre = getSharedPreferences("Bluetoothcihazadi", MODE_PRIVATE);
        my_device_name = pre.getString("cihazadi", "");
        my_device_name=my_device_name.replace("[","");
        my_device_name=my_device_name.replace("]","");

        if (bluetoothManager == null) {
            // Bluetooth unavailable on this device :( tell the user
            Toast.makeText(context, "Bluetooth not available.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        }

        Log.d("BT_device", "MAC:" + my_device_mac+" NAME:"+my_device_name);
        Collection<BluetoothDevice> pairedDevices = bluetoothManager.getPairedDevices();
        Log.d("Devices_size",""+pairedDevices.size());
        for (BluetoothDevice device : pairedDevices) {
            Log.d("Founded_BT",device.getName()+" >> "+device.getAddress());
            if (device.getAddress().equals(my_device_mac)) {
                my_device_exist = true;
                Log.d("Device", "My device found:" + my_device_mac);
            }
        }
        if (my_device_exist) {
            connectDevice(my_device_mac);
        } else {
            Toast.makeText(context, my_device_name+" not found.", Toast.LENGTH_LONG).show();
        }

        Runnable bekleme = new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        };

        Handler isleyici = new Handler();
        isleyici.postDelayed(bekleme, 4700);

        Thread_Comm = new Thread(new MainActivity.Comm_Task());
        Thread_Comm.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i); // invoke the SecondActivity.
                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);


        Context context = getApplication();

        // Check whether has the write settings permission or not.

        // If do not have then open the Can modify system settings panel.
        if (!hasWritePermission(context)) {
            changeWritePermission();
        } else {  Integer brightnessValue = 255;
            // brightness cannot be less than 0 and every click decreases the brightness
            // by a value of 10
            if (brightnessValue >= 25) {
                brightnessValue += 255;
                changeBrightness(context, brightnessValue);

                // Brightness value (1-255) to percentage and output as a Toast

            }
        }
    }

    private void changeBrightness(Context context, int i) {
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
        // Apply the screen brightness value to the system, this will change
        // the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, i
        );
    }

    private void changeWritePermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        startActivity(intent);

    }

    private boolean hasWritePermission(Context context) {
        Boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
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


    class Comm_Task implements Runnable {
        public void run() {
            while(true) {
                while(bluetooth_connected) {
                    String message;
                    if (output_update) {
                        output_update = false;
                        ouput_update_buf[3] = (char) ((outputs_data & 0xFF00) >> 8);
                        ouput_update_buf[2] = (char) (outputs_data & 0x00FF);
                        message = new String(ouput_update_buf);
                    }
                    else if(humidty_set_update)
                    {
                        SharedPreferences set_prefs = getSharedPreferences("set_values", MODE_PRIVATE);
                        humidity_set_update_buf[2]=(char)set_prefs.getInt("humidty_set",55);
                        message = new String(humidity_set_update_buf);
                        humidty_set_update=false;
                    }
                    else {
                        message = new String(input_read_buf);

                    }
                    try {
                        deviceInterface.sendMessage(message);
                    } catch (Exception e) {
                        bluetooth_connected=false;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }
    private void onConnected(BluetoothSerialDevice connectedDevice) {
        deviceInterface = connectedDevice.toSimpleDeviceInterface();
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
        Toast.makeText(context, "Menar IO Module Connected.", Toast.LENGTH_LONG).show();
        bluetooth_connected=true;
    }

    private void onMessageSent(String message) {

    }

    private void onMessageReceived(String message) {
        char[] in_buf=message.toCharArray();
        if(in_buf[20]==0x55)
        {
            if(in_buf[21]==0x42)
            {
                Log.d("BT_length", "Input buffer length:"+in_buf.length);
                //Log.d("BT_buffer:",""+Integer.toHexString(in_buf[0])+"-"+Integer.toHexString(in_buf[1])+"-"+Integer.toHexString(in_buf[2])+"-"+Integer.toHexString(in_buf[3])+"-"+Integer.toHexString(in_buf[4])+"-"+Integer.toHexString(in_buf[5])+"-"+Integer.toHexString(in_buf[6])+"-"+Integer.toHexString(in_buf[7])+
                //        "-"+Integer.toHexString(in_buf[8])+"-"+Integer.toHexString(in_buf[9])+"-"+Integer.toHexString(in_buf[10])+"-"+Integer.toHexString(in_buf[11])+"-"+Integer.toHexString(in_buf[12])+"-"+Integer.toHexString(in_buf[13])+"-"+Integer.toHexString(in_buf[14])+"-"+Integer.toHexString(in_buf[15])+
                //        "-"+Integer.toHexString(in_buf[16])+"-"+Integer.toHexString(in_buf[17])+"-"+Integer.toHexString(in_buf[18])+"-"+Integer.toHexString(in_buf[19])+"-"+Integer.toHexString(in_buf[20])+"-"+Integer.toHexString(in_buf[21]));

                //Log.d("BT", "Input buffer length:"+in_buf.length);
                inputsdat= (char) ((in_buf[3]<<8)|in_buf[2]);
                v_batt=(int)((in_buf[5]<<8)|in_buf[4]);
                v_solar=(int)((in_buf[7]<<8)|in_buf[6]);
                cl_water_lvl=(int)((in_buf[9]<<8)|in_buf[8]);
                dt_water_lvl=(int)((in_buf[11]<<8)|in_buf[10]);
                t_in=(int)((in_buf[13]<<8)|in_buf[12]);
                t_out=(int)((in_buf[15]<<8)|in_buf[14]);
                dht_temp=(int)((in_buf[17]<<8)|in_buf[16]);
                dht_temp-=40;
                dht_humidty=(int)((in_buf[19]<<8)|in_buf[18]);
                if(inputsdat!=old_inputsdat)
                {
                    outputs_data=inputsdat;
                    old_inputsdat=inputsdat;
                }

                //Log.d("Reading","Outputs:"+Integer.toHexString(inputsdat)+" Vbatt:"+v_batt+" v_solar:"+v_solar+" cl_water_lvl:"+cl_water_lvl+" dt_water_lvl:"+dt_water_lvl+" t_in:"+t_in+" t_out:"+t_out+" dht_temp:"+dht_temp+" dht_humidty:"+dht_humidty);
            }
        }
    }
    private void onError(Throwable error) {
        bluetoothManager.closeDevice(my_device_mac);
        //Toast.makeText(context, "Menar IO Module Disonnected.", Toast.LENGTH_LONG).show();
        if(my_device_exist)
        {
            connectDevice(my_device_mac);
        }
        else
        {
            Toast.makeText(context, "Menar IO Module not found.", Toast.LENGTH_LONG).show();
        }
        Log.d("BT_exception","Bağlantı kesildi yeniden bağlanılıyor",error);
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