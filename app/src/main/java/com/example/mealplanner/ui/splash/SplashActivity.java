package com.example.mealplanner.ui.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.TextView;

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

        TextView mealTextView = findViewById(R.id.meal);
        TextView plannerTextView = findViewById(R.id.Planner);

        // Animate "Meal" to slide in from the left and fade in
        ObjectAnimator mealAnimator = ObjectAnimator.ofFloat(mealTextView, "translationX", -500f, 0f);
        mealAnimator.setDuration(1000);
        ObjectAnimator mealAlphaAnimator = ObjectAnimator.ofFloat(mealTextView, "alpha", 0f, 1f);
        mealAlphaAnimator.setDuration(1000);

        // Animate "Planner" to slide in from the right and fade in
        ObjectAnimator plannerAnimator = ObjectAnimator.ofFloat(plannerTextView, "translationX", 500f, 0f);
        plannerAnimator.setDuration(1000);
        ObjectAnimator plannerAlphaAnimator = ObjectAnimator.ofFloat(plannerTextView, "alpha", 0f, 1f);
        plannerAlphaAnimator.setDuration(1000);

        // Play animations together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mealAnimator, mealAlphaAnimator, plannerAnimator, plannerAlphaAnimator);
        animatorSet.start();

    }
}