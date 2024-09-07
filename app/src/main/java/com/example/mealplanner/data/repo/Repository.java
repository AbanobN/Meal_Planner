package com.example.mealplanner.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.ui.authentication.login.presenter.OnLoginWithGmailResponse;

import java.util.List;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface Repository {
    public boolean readPreferences();

    public void writePreferences(String email, String password);

    public void removePreferences();

    public String getUserEmail();

    public void signInApp(String email, String password, FirebaseAuthCallback callback);

    public void signUpApp(String email, String password, FirebaseAuthCallback callback);

    public void signOutApp();

    public void signInWithGoogle(String idToken, OnLoginWithGmailResponse listener);

    public Single<ApiResponse.CategoryResponse> getAllCategories();

    public Single<ApiResponse.IngredientResponse> getAllIngredients();

    public Single<ApiResponse.AreaResponse>  getAreasList();

    public Single<ApiResponse.MealResponse> getMealsByCategory(String category);

    public Single<ApiResponse.MealResponse> getMealsByArea(String area);

    public Single<ApiResponse.MealResponse> getMealsByIngredient(String ingredient);

    public Single<ApiResponse.MealResponse> getRandomMeal();

    public Single<ApiResponse.MealResponse> getMealById(String id);

    public Single<ApiResponse.MealResponse> searchMealByName(String mealName);


    public Flowable<List<MealEntity>> getAllMeals(String userEmail);

    public Completable insertMeal(MealEntity mealEntity);

    public Completable deleteMeal(MealEntity mealEntity);

    public LiveData<List<PlanEntity>> getMealsForDay(String weekDay , String userEmail);

    public Completable insertPlan(PlanEntity planEntity);

    public Completable deletePlan(PlanEntity planEntity);

    public Completable insertAllPlans(List<PlanEntity> planEntities);

    public void syncData(String userEmail);

    public Completable deleteFromFirebase(String mealId);
}
