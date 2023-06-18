package com.caravan.huntercaravantabletuygulamasii.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.caravan.huntercaravantabletuygulamasii.R;

public class KullaniciUygulamaAyarlari extends AppCompatActivity {
    ImageView gelismisbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_uygulama_ayarlari);


        gelismisbtn = findViewById(R.id.gelismisayarlar);

        gelismisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KullaniciUygulamaAyarlari.this,GelismisUygulamaAyarlari.class);
                startActivity(intent);
            }
        });
    }
}