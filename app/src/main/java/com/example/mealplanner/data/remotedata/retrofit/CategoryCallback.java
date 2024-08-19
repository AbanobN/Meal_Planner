package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.CategorieData;

import java.util.List;

public interface CategoryCallback {
    void onCategorySuccess(List<CategorieData> categories);
    void onCategoryFailure(Throwable t);
}
