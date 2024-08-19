package com.example.mealplanner.data.remotedata.retrofit;
import com.example.mealplanner.data.model.MealData;

public interface OneMealCallback {
    void onMealSuccess(MealData meal);
    void onMealFailure(Throwable t);
}
