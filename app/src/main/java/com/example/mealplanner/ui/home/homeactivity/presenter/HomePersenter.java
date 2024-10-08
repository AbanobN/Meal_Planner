package com.example.mealplanner.ui.home.homeactivity.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.homeactivity.view.HomeActivity;

public class HomePersenter implements HomePer{
    private HomeActivity view;
    private AppRepo repo;
    private final String userEmail;

    public HomePersenter(AppRepo repo, HomeActivity view) {
        this.view = view;
        this.repo = repo;
        this.userEmail = repo.getUserEmail();
    }

    @Override
    public void logoutUser() {
        repo.removePreferences();
        repo.signOutApp();
    }

    @Override
    public String getUser()
    {
        return userEmail;
    }

    @Override
    public void syncData()
    {
        repo.syncData(userEmail);
    }
}
