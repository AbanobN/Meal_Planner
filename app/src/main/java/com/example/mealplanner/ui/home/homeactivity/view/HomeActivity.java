package com.example.mealplanner.ui.home.homeactivity.view;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealplanner.R;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.authentication.AuthenticationActivity;
import com.example.mealplanner.ui.home.homeactivity.presenter.HomePersenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private HomePersenter homePersenter;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppRepo repo = (AppRepo) RepositoryProvider.provideRepository(this);
        homePersenter = new HomePersenter(repo, this);
        homePersenter.syncData();


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_home_fragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            checkNetworkAndShowAnimation();
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (homePersenter.getUser().equals("guest@example.com")) {
                    if (item.getItemId() == R.id.favorites || item.getItemId() == R.id.plan) {
                        showLoginRequiredDialog();
                        return true;
                    }
                }

                if (item.getItemId() == R.id.logout) {
                    handelLogout();
                    return true;
                }
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });
    }


    private void checkNetworkAndShowAnimation() {
        AlertDialog noInternetDialog = null;

        if (NetworkUtils.isNetworkAvailable(this)) {

            if (noInternetDialog != null && noInternetDialog.isShowing()) {
                noInternetDialog.dismiss();
            }


        } else {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_no_internet, null);

            LottieAnimationView lottieAnimationView = dialogView.findViewById(R.id.lottie_no_internet);
            lottieAnimationView.playAnimation();

            if (noInternetDialog == null || !noInternetDialog.isShowing()) {
                noInternetDialog = new AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        }
    }


    private void showLoginRequiredDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Required")
                .setMessage("Please log in to access this feature.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, AuthenticationActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public void handelLogout() {
        homePersenter.logoutUser();
        Intent intent = new Intent(HomeActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }


}