package com.example.mealplanner.ui.splash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


    }
}