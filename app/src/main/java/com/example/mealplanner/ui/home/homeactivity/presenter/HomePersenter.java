package com.example.mealplanner.ui.home.homeactivity.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.ui.home.homeactivity.view.HomeActivity;

public class HomePersenter implements HomePer{
    private HomeActivity view;
    private AppRepo repo;

    public HomePersenter(Context context, HomeActivity view) {
        this.view = view;
        this.repo = AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(context), RetrofitClient.getClient());
    }


    @Override
    public void logoutUser() {
        repo.removePrefernces();
        repo.signOutApp();
    }
}
