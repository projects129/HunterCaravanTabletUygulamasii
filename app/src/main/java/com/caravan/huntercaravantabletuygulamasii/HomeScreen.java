package com.caravan.huntercaravantabletuygulamasii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.caravan.huntercaravantabletuygulamasii.adapter.DashboardPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Set;
import java.util.UUID;

public class HomeScreen extends AppCompatActivity {
private TabLayout tabLayout;
private ViewPager2 viewPager2;

private ImageView bluetooth;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnablingIntent;
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

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForeEnable = 1;



        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);


        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.anasayfabutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.aydinlatmabutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.enerjibutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.gosterge_button));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.kontrollerbutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.dengesistemibutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.ayarlarbutton));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.kapatmabutton));





        FragmentManager  fragmentmanager = getSupportFragmentManager();
        adapter = new DashboardPagerAdapter(fragmentmanager,getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
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


    }



}