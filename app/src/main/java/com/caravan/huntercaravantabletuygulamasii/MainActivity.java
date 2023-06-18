package com.caravan.huntercaravantabletuygulamasii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AnimationDrawable drawableAnimation;
    private static final int SPLASH_SCREEN_TIME_OUT = 4600; // After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.msk);
        imageView.setBackgroundResource(R.drawable.tasarim);
        drawableAnimation = (AnimationDrawable) imageView.getBackground();

        Runnable beklemeSuresi = new Runnable() {
            @Override
            public void run() {
                nextActivity();

            }
        };

        Handler isleyici = new Handler();
        isleyici.postDelayed(beklemeSuresi,3000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i); // invoke the SecondActivity.
                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }





    public void nextActivity(){
        ImageView imageView = (ImageView) findViewById(R.id.msk);

        ImageView Glass1=findViewById(R.id.mask);
        ImageView hunter = findViewById(R.id.hunter);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move);
        Glass1.startAnimation(animation1);
         hunter.startAnimation(animation1);

        Glass1.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        hunter.setVisibility(View.VISIBLE);


    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        drawableAnimation.start();

    }
}