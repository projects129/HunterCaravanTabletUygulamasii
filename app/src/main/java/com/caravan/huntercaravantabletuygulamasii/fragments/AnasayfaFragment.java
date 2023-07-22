package com.caravan.huntercaravantabletuygulamasii.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caravan.huntercaravantabletuygulamasii.MainActivity;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class AnasayfaFragment extends Fragment {
    private Handler handler = new Handler();
    Thread Thread_refresh = null;
    TextView cl_water_txt,dt_water_txt,vbatt_txt,vbatt_perc_txt,tin_txt, tout_txt;
    KullaniciUygulamaAyarlari kullaniciUygulamaAyarlari;

    ClipDrawable drawable1,drawable;

  int akugelensayi= 15;
  int temizsugelensayi= 5;
    TextView saat;
    TextView dakika;
    TextView Gun;
    TextView Ay;
    TextView Yil;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anasayfa, container, false);

    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences Timee = getActivity().getSharedPreferences("Time",MODE_PRIVATE);
        String time = Timee.getString("time","00");
        String dk = Timee.getString("dk","00");


        String day = Timee.getString("gun","00");
        String ay = Timee.getString("ay","00");
        String yil = Timee.getString("yil","00");
        saat.setText(time);
        dakika.setText(dk);
        Gun.setText(day);
        Ay.setText(ay);
        Yil.setText(yil);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView akuimage =  view.findViewById(R.id.akudolum);
        ImageView temizsuimage = view.findViewById(R.id.temizsudolum);
        saat = view.findViewById(R.id.saat);
        dakika = view.findViewById(R.id.dk);
        Gun = view.findViewById(R.id.gun);
        Ay = view.findViewById(R.id.ay);
        Yil = view.findViewById(R.id.yil);




        cl_water_txt=(TextView)view.findViewById(R.id.textView67);
        dt_water_txt=(TextView)view.findViewById(R.id.textView63);
        vbatt_txt=(TextView)view.findViewById(R.id.textView57);
        vbatt_perc_txt=(TextView)view.findViewById(R.id.textView32);
        tin_txt=(TextView)view.findViewById(R.id.textView51);
        tout_txt=(TextView)view.findViewById(R.id.textView52);

        drawable = (ClipDrawable) akuimage.getDrawable();
        drawable.setLevel(0);

        drawable.setLevel(drawable.getLevel()+ (500*akugelensayi));


        drawable1 = (ClipDrawable) temizsuimage.getDrawable();
        drawable1.setLevel(0);

        Thread_refresh = new Thread(new AnasayfaFragment.refresh_Task());
        Thread_refresh.start();
    }

    class refresh_Task implements Runnable {
        public void run() {
            while(true) {
                set_input_views();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void set_input_views()
    {
        handler.post(new Runnable() {
            public void run() {
                drawable1.setLevel(MainActivity.cl_water_lvl*100);
                cl_water_txt.setText(""+MainActivity.cl_water_lvl);
                dt_water_txt.setText(""+MainActivity.dt_water_lvl);

                drawable.setLevel((int)(MainActivity.v_batt*0.694444444f));
                vbatt_perc_txt.setText(""+(int)(MainActivity.v_batt*0.694444444f/100));
                String s = String.format("%.1f", (float)MainActivity.v_batt/1000);
                vbatt_txt.setText(s);
                s = String.format("%.0f", (float)MainActivity.dht_temp/10);
                tin_txt.setText(s);
                s = String.format("%.0f", (float)MainActivity.t_out/100);
                tout_txt.setText(s);
            }
        });
    }
}