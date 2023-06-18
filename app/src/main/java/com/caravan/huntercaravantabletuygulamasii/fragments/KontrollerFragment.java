package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.caravan.huntercaravantabletuygulamasii.R;


public class KontrollerFragment extends Fragment {

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
            }
        });


        kapalibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikbutton.setBackgroundResource(R.drawable.acikbtngri);
                kapalibutton.setBackgroundResource(R.drawable.kapalikirmizibtn);
            }
        });

        acikhidrofor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikhidrofor.setBackgroundResource(R.drawable.acikbuttonyesil);
                kapalihidrofor.setBackgroundResource(R.drawable.kapalibutton);
            }
        });

        kapalihidrofor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acikhidrofor.setBackgroundResource(R.drawable.acikbtngri);
                kapalihidrofor.setBackgroundResource(R.drawable.kapalikirmizibtn);
            }
        });
    }
}