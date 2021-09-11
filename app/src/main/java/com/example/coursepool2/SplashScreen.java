package com.example.coursepool2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView splash;
    LinearLayout splatext;
    float v= (float) 2.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash=findViewById(R.id.spalshanim);
        splatext=findViewById(R.id.splashtext);


        splatext.setTranslationY(1000);
        splatext.setAlpha(v);
        splatext.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Sharedprefprovider.getInstance(getApplicationContext()).getEmail()!= null)
                {
                    Intent intent =new Intent(SplashScreen.this,MainHomescreen.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent =new Intent(SplashScreen.this,Mainactivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);

    }


}