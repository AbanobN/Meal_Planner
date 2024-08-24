package com.example.mealplanner.ui.home.homeactivity.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.homeactivity.view.HomeActivity;

public class HomePersenter implements HomePer{
    private HomeActivity view;
    private AppRepo repo;

    public HomePersenter(Context context, HomeActivity view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
    }


    @Override
    public void logoutUser() {
        repo.removePreferences();
        repo.signOutApp();
    }
}
