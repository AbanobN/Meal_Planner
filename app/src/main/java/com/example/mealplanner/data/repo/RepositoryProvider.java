package com.example.mealplanner.data.repo;

import android.content.Context;

import com.example.mealplanner.data.localdata.database.AppDatabase;
import com.example.mealplanner.data.localdata.database.MealRepository;
import com.example.mealplanner.data.localdata.database.PlanDAO;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;

public class RepositoryProvider {

    public static Repository provideRepository(Context context) {
        AuthModelImpl authModel = new AuthModelImpl();
        RetrofitClient retrofitClient = RetrofitClient.getClient();
        MealRepository mealRepository = new MealRepository(context);

        return AppRepo.getInstance(authModel, SharedPerferencesImp.getInstance(context), retrofitClient, mealRepository);
    }
}
