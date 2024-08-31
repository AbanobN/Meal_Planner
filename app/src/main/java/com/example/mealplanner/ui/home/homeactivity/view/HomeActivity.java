package com.example.mealplanner.ui.home.homeactivity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mealplanner.R;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.authentication.AuthenticationActivity;
import com.example.mealplanner.ui.home.homeactivity.presenter.HomePersenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements HomeView{

    private HomePersenter homePersenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppRepo repo = (AppRepo) RepositoryProvider.provideRepository(this);
        homePersenter = new HomePersenter(repo,this);
        homePersenter.syncData();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        NavController navController = Navigation.findNavController(this,R.id.nav_host_home_fragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if(homePersenter.getUser().equals("guest@example.com"))
                {
                    if(item.getItemId() == R.id.favorites || item.getItemId() == R.id.plan )
                    {
                        Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                
                if(item.getItemId() == R.id.logout)
                {
                    handelLogout();
                    return true;
                }
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });
    }

    @Override
    public void handelLogout() {
        homePersenter.logoutUser();
        Intent intent = new Intent(HomeActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }


}