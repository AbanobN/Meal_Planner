package com.example.mealplanner.data.repo;

import android.content.Context;

import com.example.mealplanner.data.localdata.database.DataBaseManger;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesManger;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseManger;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitManager;


public class RepositoryProvider {

    public static Repository provideRepository(Context context) {
        FirebaseManger authModel = new FirebaseManger();
        RetrofitManager retrofitManager = new RetrofitManager();
        DataBaseManger dataBaseManger = new DataBaseManger(context);

        return AppRepo.getInstance(authModel, SharedPerferencesManger.getInstance(context), retrofitManager, dataBaseManger);
    }
}
