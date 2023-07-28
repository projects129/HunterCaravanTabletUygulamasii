package com.caravan.huntercaravantabletuygulamasii;

import static java.lang.Math.round;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.caravan.huntercaravantabletuygulamasii.adapter.DashboardPagerAdapter;
import com.caravan.huntercaravantabletuygulamasii.fragments.KullaniciUygulamaAyarlari;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class HomeScreen extends AppCompatActivity {
 TabLayout tabLayout;
private ViewPager2 viewPager2;

private ImageView homeimage;


    int brightnessValue = 255;
    // Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    // Window object, that will store a reference to the current window
    private Window window;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnablingIntent;

    KullaniciUygulamaAyarlari kullaniciUygulamaAyarlari;
    int requestCodeForeEnable;

    BluetoothDevice[] btArray;

    ListView pairedlist;

    private Set<BluetoothDevice> pairedDevice;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
private DashboardPagerAdapter  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
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




        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForeEnable = 1;



        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
      homeimage = findViewById(R.id.homeimage);

        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.anasayfabutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.aydinlatmabutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.enerjibutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.gosterge_button));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.kontrollerbutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.dengesistemibutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.ayarlarbutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.kapatmabutton));


        loadLocale();


        FragmentManager  fragmentmanager = getSupportFragmentManager();
        adapter = new DashboardPagerAdapter(fragmentmanager,getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.setVisibility(View.VISIBLE);
        homeimage.setImageResource(R.drawable.re);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(),false);
                int position = tab.getPosition();
                switch (position) {
                    case 7:
                        Brightness();

                        tabLayout.setVisibility(View.INVISIBLE);
                        homeimage.setImageResource(R.color.black);
                       Log.e("basılsı","basıldı");
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
              tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        SharedPreferences shared = getSharedPreferences("dengesistemi",MODE_PRIVATE);
        String deger = shared.getString("dengesistemi","");
        Log.e("deger",""+deger);
        if(deger.equals("true")){

            tabLayout.getTabAt(4).view.setVisibility(View.GONE);

        }else if(deger.equals("false")){
            tabLayout.getTabAt(4).view.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences shared = getSharedPreferences("dengesistemi",MODE_PRIVATE);
        String deger = shared.getString("dengesistemi","");
        Log.e("deger",""+deger);
        if(deger.equals("true")){

            tabLayout.getTabAt(5).view.setVisibility(View.GONE);

        }else if(deger.equals("false")){
            tabLayout.getTabAt(5).view.setVisibility(View.VISIBLE);
        }
    }



    private void Brightness() {
        Context context = getApplication();

        // Check whether has the write settings permission or not.

        // If do not have then open the Can modify system settings panel.
        if (!hasWritePermission(context)) {
            changeWritePermission();
        } else {  int brightnessValue = 255;
            // brightness cannot be less than 0 and every click decreases the brightness
            // by a value of 10
            brightnessValue -= 255;
            changeBrightness(context, brightnessValue);

            // Brightness value (1-255) to percentage and output as a Toast

        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
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
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
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