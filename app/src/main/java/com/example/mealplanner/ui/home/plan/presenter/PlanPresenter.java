package com.example.mealplanner.ui.home.plan.presenter;

import android.view.View;

public interface PlanPresenter {
    void attachView(View view);
    void detachView();
    void fetchMealsForDay(String day);

}
