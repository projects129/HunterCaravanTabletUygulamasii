package com.caravan.huntercaravantabletuygulamasii.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.caravan.huntercaravantabletuygulamasii.R;

public class GelismisUygulamaAyarlari extends AppCompatActivity {
ImageView diagnostikbtn;
ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelismis_uygulama_ayarlari);

        diagnostikbtn = findViewById(R.id.diagnostikbtn);
        backbutton = findViewById(R.id.backbtn);
        diagnostikbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GelismisUygulamaAyarlari.this,DiagnostikMenu.class);
                startActivity(intent);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GelismisUygulamaAyarlari.this,KullaniciUygulamaAyarlari.class);
                startActivity(intent);
            }
        });

    }
}