package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caravan.huntercaravantabletuygulamasii.R;


public class GostergelerFragment extends Fragment {


    int akugelensayi= 15;
    int temizsugelensayi= 5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gostergeler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView temizsuimage =  view.findViewById(R.id.temizsudolum1);
        ImageView atiksuimage = view.findViewById(R.id.atiksudolum);


        ClipDrawable drawable = (ClipDrawable) temizsuimage.getDrawable();
        drawable.setLevel(0);
        drawable.setLevel(drawable.getLevel()+ (500));



        ClipDrawable drawable1 = (ClipDrawable) atiksuimage.getDrawable();
        drawable1.setLevel(0);
        drawable1.setLevel(drawable1.getLevel()+ (500*temizsugelensayi));

    }
}