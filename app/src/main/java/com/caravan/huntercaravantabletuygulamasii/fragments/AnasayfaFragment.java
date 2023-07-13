package com.caravan.huntercaravantabletuygulamasii.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caravan.huntercaravantabletuygulamasii.R;


public class AnasayfaFragment extends Fragment {

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



        ClipDrawable drawable = (ClipDrawable) akuimage.getDrawable();
        drawable.setLevel(0);

        drawable.setLevel(drawable.getLevel()+ (500*akugelensayi));


        ClipDrawable drawable1 = (ClipDrawable) temizsuimage.getDrawable();
        drawable1.setLevel(0);
        drawable1.setLevel(drawable1.getLevel()+ (500*temizsugelensayi));






    }
}