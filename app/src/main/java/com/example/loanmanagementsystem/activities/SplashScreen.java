package com.example.loanmanagementsystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.activities.SignIn;

public class SplashScreen extends AppCompatActivity {
    Animation top, bottom;
    TextView text1, text2;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);
        logo.setAnimation(top);
        text1.setAnimation(bottom);
        text2.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
            }
        },3000);

    }
}