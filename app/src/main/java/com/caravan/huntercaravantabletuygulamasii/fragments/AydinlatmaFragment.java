package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.caravan.huntercaravantabletuygulamasii.R;


public class AydinlatmaFragment extends Fragment {


    Switch mutfakisiklari;
    Switch oturmaalanisiklariM;
    Switch yatakodasiisiklari1;
    Switch mutfaktavanisiklari;
    Switch oturmaalaniisiklari;
    Switch yatakodasiisiklari2;

    Switch disisiklarsol;

    Switch disisiklaron;

    Switch parkisiklarisag;

    Switch disisiklarisag;

    Switch disisiklararka;

    Switch parkisiklarisol;

        // Required empty public constructor


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aydinlatma, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mutfakisiklari = view.findViewById(R.id.mutfakisiklari);
        oturmaalanisiklariM=view.findViewById(R.id.oturmaalanisiklari);
        yatakodasiisiklari1 = view.findViewById(R.id.yatakodaisik1);
        mutfaktavanisiklari= view.findViewById(R.id.mutfaktavanisik);
        oturmaalaniisiklari = view.findViewById(R.id.oturmaalanisiklari);
        yatakodasiisiklari2=view.findViewById(R.id.yatakodasiisik2);


        disisiklarsol = view.findViewById(R.id.disisiklarisol);
        disisiklaron = view.findViewById(R.id.disisiklaron);
        parkisiklarisag = view.findViewById(R.id.parkisiklarisag);
        disisiklarisag = view.findViewById(R.id.disisiklarsag);
        disisiklararka= view.findViewById(R.id.disisiklararka);
        parkisiklarisol= view.findViewById(R.id.parkisiklarisol);

        mutfakisiklari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), "Mutfak Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), "Mutfak ışıkları kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });
        oturmaalanisiklariM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), "OturmaIşıklarıM Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), "OturmaIşıklarıM kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });

        yatakodasiisiklari1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), "YatakOdası1 Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), "YatakOdası1 kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mutfaktavanisiklari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Mutfaktavan Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Mutfaktavan Isiklari kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });

        oturmaalaniisiklari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " oturmaalani  Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " oturmaalani Isiklari kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });

        yatakodasiisiklari2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Yatak Odası2 Işıkları açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Yatak Odası2 Isiklari kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });



        disisiklarsol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Dış Işıkları Sol açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Dış  Isiklari Sol kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });


        disisiklaron.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Dış Işıkları Ön açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Dış  Isiklari Ön kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });


        parkisiklarisag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Park Işıkları sağ açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Park Isiklari sağ kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });


        disisiklarisag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Dış Işıkları Sağ açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Dış  Isiklari Sağ kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });

        disisiklararka.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Dış Işıkları Arka açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Dış  Isiklari Arka kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });


        parkisiklarisol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(requireContext(), " Park Işıkları Sol açıldı", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(requireContext(), " Park  Isiklari Sol kapandı", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}