package com.example.mealplanner.data.localdata.database;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface DataBaseManger {
    Flowable<List<MealEntity>> getAllFavoritesByUserEmail(String userEmail);

    Completable insertMeal(MealEntity mealEntity);

    Completable deleteMeal(MealEntity mealEntity);

    Completable insertPlan(PlanEntity planEntity);

    Completable deletePlan(PlanEntity planEntity);

    Single<List<PlanEntity>> getPlansByUserEmail(String userEmail);

    Completable insertAllPlans(List<PlanEntity> planEntities);

    LiveData<List<PlanEntity>> getMealsForDay(String weekDay, String userEmail);
}
