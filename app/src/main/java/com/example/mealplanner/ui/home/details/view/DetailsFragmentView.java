package com.example.mealplanner.ui.home.details.view;

import com.example.mealplanner.data.model.MealData;

public interface DetailsFragmentView {
    public void updateDetails(MealData mealData);
    public void showError(Throwable t);
    public  String extractVideoId(String url);
    public void showToast(String msg);
}
