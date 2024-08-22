package com.example.mealplanner.data.localdata.database;

import android.content.Context;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealRepository {

    private final MealDAO mealDAO;

    public MealRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealDAO = db.mealDAO();
    }

    // Get all favorite meals as Flowable
    public Flowable<List<MealEntity>> getAllMeals() {
        return mealDAO.getAll(); // This returns a Flowable
    }

    // Insert a new meal
    public Completable insertMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.insert(mealEntity));
    }

    // Delete a meal
    public Completable deleteMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.delete(mealEntity));
    }
}
