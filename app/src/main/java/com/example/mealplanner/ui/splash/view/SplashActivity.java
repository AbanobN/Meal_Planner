package com.example.mealplanner.ui.splash.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.R;
import com.example.mealplanner.ui.authentication.view.AuthenticationActivity;
import com.example.mealplanner.ui.home.HomeActivity;
import com.example.mealplanner.ui.splash.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity implements SplashView{

    private SplashPresenter presenter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        presenter = new SplashPresenter(this, this);
        presenter.isAppUser();
        presenter.start(5L);

        TextView mealTextView = findViewById(R.id.meal);
        TextView plannerTextView = findViewById(R.id.Planner);

        // Animate "Meal"
        ObjectAnimator mealAnimator = ObjectAnimator.ofFloat(mealTextView, "translationX", -500f, 0f);
        mealAnimator.setDuration(1000);
        ObjectAnimator mealAlphaAnimator = ObjectAnimator.ofFloat(mealTextView, "alpha", 0f, 1f);
        mealAlphaAnimator.setDuration(1000);

        // Animate "Planner"
        ObjectAnimator plannerAnimator = ObjectAnimator.ofFloat(plannerTextView, "translationX", 500f, 0f);
        plannerAnimator.setDuration(1000);
        ObjectAnimator plannerAlphaAnimator = ObjectAnimator.ofFloat(plannerTextView, "alpha", 0f, 1f);
        plannerAlphaAnimator.setDuration(1000);

        // Play animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mealAnimator, mealAlphaAnimator, plannerAnimator, plannerAlphaAnimator);
        animatorSet.start();

    }

    @Override
    public void isAuthentice(boolean isAuthentice) {
        if(isAuthentice)
        {
            intent = new Intent(SplashActivity.this, HomeActivity.class);
        }
        else
        {
            intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
        }
    }

    @Override
    public void navigateToNextActivity() {
        startActivity(intent);
        finish();
    }
}