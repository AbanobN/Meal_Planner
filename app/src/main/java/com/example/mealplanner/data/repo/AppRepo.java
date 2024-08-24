package com.example.mealplanner.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.localdata.database.DataBaseManger;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesManger;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseManger;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class AppRepo implements Repository{
    private static AppRepo instance = null;
    private FirebaseManger firebaseAuth;
    private SharedPerferencesManger shPer;
    private RetrofitManager retrofitManager;
    private DataBaseManger dataBaseManger;

    private AppRepo(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManager retrofitManager, DataBaseManger dataBaseManger) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
        this.retrofitManager = retrofitManager;
        this.dataBaseManger = dataBaseManger;
    }

    public static AppRepo getInstance(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManager retrofitManager, DataBaseManger dataBaseManger) {
        if (instance == null) {
            instance = new AppRepo(firebaseAuth, shPer, retrofitManager, dataBaseManger);
        }
        return instance;
    }

    public boolean readPreferences() {
        return shPer.readFromPreferences();
    }

    public void writePreferences(String email, String password) {
        shPer.addToPreferences(email, password);
    }

    public void removePreferences() {
        shPer.removePreferences();
    }

    public void signInApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signIn(email, password, callback);
    }

    public void signUpApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signUp(email, password, callback);
    }

    public void signOutApp() {
        firebaseAuth.signOut();
    }

    public Single<List<CategorieData>> getAllCategories() {
        return retrofitManager.fetchCategories();
    }

    public Single<List<MealData>> getMealsByCategory(String category) {
        return retrofitManager.fetchMealsByCategory(category);
    }

    public Single<MealData> getMealById(String id) {
        return retrofitManager.fetchMealById(id);
    }

    public Flowable<List<MealEntity>> getAllMeals() {
        return dataBaseManger.getAllMeals();
    }

    public Completable insertMeal(MealEntity mealEntity) {
        return dataBaseManger.insertMeal(mealEntity);
    }

    public Completable deleteMeal(MealEntity mealEntity) {
        return dataBaseManger.deleteMeal(mealEntity);
    }

    public LiveData<List<PlanEntity>> getAllPlansByDay(String weekDay) {
        return dataBaseManger.getAllPlans(weekDay);
    }

    public Completable insertPlan(PlanEntity planEntity) {
        return dataBaseManger.insertPlan(planEntity);
    }

    public Completable deletePlan(PlanEntity planEntity) {
        return dataBaseManger.deletePlan(planEntity);
    }

    public Completable insertAllPlans(List<PlanEntity> planEntities) {
        return dataBaseManger.insertAllPlans(planEntities);
    }
}
