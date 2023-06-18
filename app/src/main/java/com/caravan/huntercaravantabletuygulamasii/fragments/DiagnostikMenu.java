package com.caravan.huntercaravantabletuygulamasii.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.caravan.huntercaravantabletuygulamasii.R;

public class DiagnostikMenu extends AppCompatActivity {
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostik_menu);


        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnostikMenu.this,GelismisUygulamaAyarlari.class);
                startActivity(intent);
            }
        });
    }
}