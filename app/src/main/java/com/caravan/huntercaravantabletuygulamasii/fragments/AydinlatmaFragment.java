package com.caravan.huntercaravantabletuygulamasii.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.caravan.huntercaravantabletuygulamasii.MainActivity;
import com.caravan.huntercaravantabletuygulamasii.R;


public class AydinlatmaFragment extends Fragment {

    Switch OUTPUT_VIEWS[] = new Switch[12];
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

        OUTPUT_VIEWS[0] = view.findViewById(R.id.mutfakisiklari);
        OUTPUT_VIEWS[1] = view.findViewById(R.id.oturmaalanisikM);
        OUTPUT_VIEWS[2] = view.findViewById(R.id.yatakodaisik1);
        OUTPUT_VIEWS[3] = view.findViewById(R.id.mutfaktavanisik);
        OUTPUT_VIEWS[4] = view.findViewById(R.id.oturmaalanisiklari);
        OUTPUT_VIEWS[5] = view.findViewById(R.id.yatakodasiisik2);
        OUTPUT_VIEWS[6] = view.findViewById(R.id.disisiklarisol);
        OUTPUT_VIEWS[7] = view.findViewById(R.id.disisiklaron);
        OUTPUT_VIEWS[8] = view.findViewById(R.id.parkisiklarisag);
        OUTPUT_VIEWS[9] = view.findViewById(R.id.disisiklarsag);
        OUTPUT_VIEWS[10] =view.findViewById(R.id.disisiklararka);
        OUTPUT_VIEWS[11] =view.findViewById(R.id.parkisiklarisol);

        mutfakisiklari = view.findViewById(R.id.mutfakisiklari);
        oturmaalanisiklariM=view.findViewById(R.id.oturmaalanisikM);
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


        for (int i = 0; i < OUTPUT_VIEWS.length; i++) {
            int finalI = i;
            OUTPUT_VIEWS[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data | (1 << finalI))));
                    else MainActivity.outputs_data = (char) ((char) ((MainActivity.outputs_data & (~(1 << finalI)))));
                    MainActivity.output_update=true;
                    Log.d("TAG", "Outputs data:" + Integer.toHexString(MainActivity.outputs_data));
                }
            });
        }


    }
}