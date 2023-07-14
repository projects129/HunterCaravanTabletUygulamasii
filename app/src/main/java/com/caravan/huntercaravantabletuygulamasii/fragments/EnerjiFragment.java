package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caravan.huntercaravantabletuygulamasii.MainActivity;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.util.Timer;
import java.util.TimerTask;

public class EnerjiFragment extends Fragment {
    ClipDrawable drawable;
    private Handler handler = new Handler();
    TextView vbatt_txt,vbatt_perc_txt,vsolar_txt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enerji, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView akuimage = view.findViewById(R.id.akudolum3);
        vbatt_txt=(TextView)view.findViewById(R.id.textView18);
        vbatt_perc_txt=(TextView)view.findViewById(R.id.textView59);
        vsolar_txt=(TextView)view.findViewById(R.id.textView21);


        drawable = (ClipDrawable) akuimage.getDrawable();
        drawable.setLevel(0);
        Timer timer = new Timer();
        timer.schedule(refresh_timerTask,0,100);
    }

    final TimerTask refresh_timerTask = new TimerTask() {
        @Override
        public void run() {
            set_input_views();
        }
    };
    public void set_input_views()
    {
        handler.post(new Runnable() {
            public void run() {

                drawable.setLevel((int)(MainActivity.v_batt*0.694444444f));
                vbatt_perc_txt.setText(""+(int)(MainActivity.v_batt*0.694444444f/100));
                String s = String.format("%.1f", (float)MainActivity.v_batt/1000);
                vbatt_txt.setText(s);
                s = String.format("%.1f", (float)MainActivity.v_solar/100);
                vsolar_txt.setText(s);
            }
        });
    }
}