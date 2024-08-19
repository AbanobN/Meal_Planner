package com.example.mealplanner.ui.home.details.presenter;

import android.content.Context;

import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.OneMealCallback;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.ui.home.details.view.DetailsFragment;

public class DetailsFragmentPresenterImp implements OneMealCallback , DetailsFragmentPresenter{
    DetailsFragment view;
    AppRepo repo;

    public DetailsFragmentPresenterImp(Context context, DetailsFragment view) {
        this.view = view;
        this.repo = AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(context), RetrofitClient.getClient());
    }

    @Override
    public void getMealById(String id)
    {
        repo.getMealById(id,this);
    }

    @Override
    public void onMealSuccess(MealData meal) {
        view.updateDetails(meal);
    }

    @Override
    public void onMealFailure(Throwable t) {

    }
}
