package com.example.mealplanner.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.localdata.database.MealEntity;
import com.example.mealplanner.data.localdata.database.MealRepository;
import com.example.mealplanner.data.localdata.database.PlanEntity;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.remotedata.retrofit.CategoryCallback;
import com.example.mealplanner.data.remotedata.retrofit.MealCallback;
import com.example.mealplanner.data.remotedata.retrofit.OneMealCallback;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import java.util.List;
import com.example.mealplanner.data.localdata.database.PlanDAO;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class AppRepo implements Repository{
    private static AppRepo instance = null;
    private AuthModelImpl firebaseAuth;
    private SharedPerferencesImp shPer;
    private RetrofitClient retrofit;
    private MealRepository mealRepository;

    private AppRepo(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer, RetrofitClient retrofit, MealRepository mealRepository) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
        this.retrofit = retrofit;
        this.mealRepository = mealRepository;
    }

    public static AppRepo getInstance(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer, RetrofitClient retrofit, MealRepository mealRepository)
    {
        if(instance == null)
        {
            instance = new AppRepo(firebaseAuth,shPer , retrofit, mealRepository);
        }
        return instance;
    }

    @Override
    public boolean readPrefernces() {

        return shPer.readFromPreferences();
    }

    @Override
    public void writePrefernces(String email, String password) {
        shPer.addToPreferences(email,password);
    }

    @Override
    public void removePrefernces() {
        shPer.removePreferences();
    }


    @Override
    public void signInApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signIn(email,password,callback);
    }

    @Override
    public void signUpApp(String email, String password, FirebaseAuthCallback callback) {

        firebaseAuth.signUp(email,password,callback);
    }

    @Override
    public void signOutApp() {
        firebaseAuth.signOut();
    }

    @Override
    public void getAllCategories(CategoryCallback categoryCallback) {
        retrofit.fetchCategories(categoryCallback);
    }

    @Override
    public void getMealsByCategory(String category, MealCallback mealCallback) {
        retrofit.fetchMealsByCategory(category,mealCallback);
    }

    @Override
    public void getMealById(String id, OneMealCallback oneMealCallback) {
        retrofit.fetchMealById(id,oneMealCallback);
    }

    @Override
    public Flowable<List<MealEntity>> getAllMeals() {
        return mealRepository.getAllMeals();
    }

    @Override
    public Completable insertMeal(MealEntity mealEntity) {
        return mealRepository.insertMeal(mealEntity);
    }

    @Override
    public Completable deleteMeal(MealEntity mealEntity) {
        return mealRepository.deleteMeal(mealEntity);
    }

    @Override
    public LiveData<List<PlanEntity>> getAllPlansByDay(String weekDay) {
        return mealRepository.getAllPlans(weekDay);
    }

    @Override
    public Completable insertPlan(PlanEntity planEntity) {
        return mealRepository.insertPlan(planEntity);
    }

    @Override
    public Completable deletePlan(PlanEntity planEntity) {
        return mealRepository.deletePlan(planEntity);
    }


}
