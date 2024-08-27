package com.example.mealplanner.data.repo;

import android.content.Context;

import com.example.mealplanner.data.localdata.database.DataBaseMangerImp;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesManger;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseManger;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitManagerImp;


public class RepositoryProvider {

    public static Repository provideRepository(Context context) {
        FirebaseManger authModel = new FirebaseManger();
        RetrofitManagerImp retrofitManagerImp = new RetrofitManagerImp();
        DataBaseMangerImp dataBaseMangerImp = new DataBaseMangerImp(context);

        return AppRepo.getInstance(authModel, SharedPerferencesManger.getInstance(context), retrofitManagerImp, dataBaseMangerImp);
    }
}
