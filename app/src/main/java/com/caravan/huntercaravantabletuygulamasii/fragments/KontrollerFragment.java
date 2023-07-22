package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.caravan.huntercaravantabletuygulamasii.MainActivity;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.util.Timer;
import java.util.TimerTask;


public class KontrollerFragment extends Fragment {
    private Handler handler = new Handler();
    Button acikbutton;
    Button kapalibutton;
    Button acikhidrofor;
    Button kapalihidrofor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kontroller, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        acikbutton = view.findViewById(R.id.acikbtn);
        kapalibutton = view.findViewById(R.id.kapalibtn);
        acikhidrofor = view.findViewById(R.id.acikhidrofor);
        kapalihidrofor = view.findViewById(R.id.kapalıhidrofor);


        acikbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 kapalibutton.setBackgroundResource(R.drawable.kapalibutton);
                 acikbutton.setBackgroundResource(R.drawable.acikbuttonyesil);
                 MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << 12))));
                 MainActivity.output_update=true;
            }
        });


        kapalibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikbutton.setBackgroundResource(R.drawable.acikbtngri);
                kapalibutton.setBackgroundResource(R.drawable.kapalikirmizibtn);
                MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data & (~(1 << 12)))));
                MainActivity.output_update=true;
            }
        });

        acikhidrofor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikhidrofor.setBackgroundResource(R.drawable.acikbuttonyesil);
                kapalihidrofor.setBackgroundResource(R.drawable.kapalibutton);
                MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << 13))));
                MainActivity.output_update=true;
            }
        });

        kapalihidrofor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikhidrofor.setBackgroundResource(R.drawable.acikbtngri);
                kapalihidrofor.setBackgroundResource(R.drawable.kapalikirmizibtn);
                MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data & (~(1 << 13)))));
                MainActivity.output_update=true;
            }
        });
        Timer timer = new Timer();
        timer.schedule(refresh_timerTask,0,100);
    }
    public void set_input_views(char dat)
    {
        handler.post(new Runnable() {
            public void run() {
                if((MainActivity.inputsdat&0x1000)>0)
                {
                    kapalibutton.setBackgroundResource(R.drawable.kapalibutton);
                    acikbutton.setBackgroundResource(R.drawable.acikbuttonyesil);
                    MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << 12))));
                }
                else {

                    acikbutton.setBackgroundResource(R.drawable.acikbtngri);
                    kapalibutton.setBackgroundResource(R.drawable.kapalikirmizibtn);
                    MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << 12))));
                }

                if((MainActivity.inputsdat&0x2000)>0)
                {
                    acikhidrofor.setBackgroundResource(R.drawable.acikbuttonyesil);
                    kapalihidrofor.setBackgroundResource(R.drawable.kapalibutton);
                    MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << 13))));
                }
                else {
                    acikhidrofor.setBackgroundResource(R.drawable.acikbtngri);
                    kapalihidrofor.setBackgroundResource(R.drawable.kapalikirmizibtn);
                    MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data & (~(1 << 13)))));
                }
            }
        });
    }

    final TimerTask refresh_timerTask = new TimerTask() {
        @Override
        public void run() {
            if(MainActivity.inputsdat!=MainActivity.old_inputsdat) {
                MainActivity.old_inputsdat=MainActivity.inputsdat;
                set_input_views(MainActivity.inputsdat);
            }
        }
    };

}