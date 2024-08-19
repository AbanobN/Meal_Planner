package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.MealData;
import java.util.List;

public interface MealCallback {
    void onMealSuccess(List<MealData> meals);
    void onMealFailure(Throwable t);
}
