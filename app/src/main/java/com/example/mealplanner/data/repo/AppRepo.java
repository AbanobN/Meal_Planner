package com.example.mealplanner.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.localdata.database.DataBaseMangerImp;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesManger;
import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseManger;
import com.example.mealplanner.data.remotedata.firebasedatabase.FirebaseDatabaseServiceImp;
import com.example.mealplanner.data.remotedata.firebasedatabase.SyncServiceImp;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitManagerImp;
import com.example.mealplanner.ui.authentication.login.presenter.OnLoginWithGmailResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class AppRepo implements Repository{
    private static AppRepo instance = null;
    private FirebaseManger firebaseAuth;
    private SharedPerferencesManger shPer;
    private RetrofitManagerImp retrofitManagerImp;
    private DataBaseMangerImp dataBaseMangerImp;
    private FirebaseDatabaseServiceImp firebaseDatabaseServiceImp;
    private SyncServiceImp syncServiceImp;

    private AppRepo(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManagerImp retrofitManagerImp, DataBaseMangerImp dataBaseMangerImp, FirebaseDatabaseServiceImp firebaseDatabaseServiceImp,SyncServiceImp syncServiceImp) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
        this.retrofitManagerImp = retrofitManagerImp;
        this.dataBaseMangerImp = dataBaseMangerImp;
        this.firebaseDatabaseServiceImp = firebaseDatabaseServiceImp;
        this.syncServiceImp = syncServiceImp;

    }

    public static AppRepo getInstance(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManagerImp retrofitManagerImp, DataBaseMangerImp dataBaseMangerImp ,FirebaseDatabaseServiceImp firebaseDatabaseServiceImp,SyncServiceImp syncServiceImp) {
        if (instance == null) {
            instance = new AppRepo(firebaseAuth, shPer, retrofitManagerImp, dataBaseMangerImp ,firebaseDatabaseServiceImp,syncServiceImp);
        }
        return instance;
    }

    //Prefrences

    @Override
    public boolean readPreferences() {
        return shPer.readFromPreferences();
    }

    @Override
    public void writePreferences(String email, String password) {
        shPer.addToPreferences(email, password);
    }

    @Override
    public void removePreferences() {
        shPer.removePreferences();
    }

    @Override
    public String getUserEmail(){
        return shPer.getEmail();
    }

    // firebase Auth

    @Override
    public void signInApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signIn(email, password, callback);
    }

    @Override
    public void signUpApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signUp(email, password, callback);
    }

    @Override
    public void signOutApp() {
        firebaseAuth.signOut();
    }

    @Override
    public void signInWithGoogle(String idToken, OnLoginWithGmailResponse listener)
    {
        firebaseAuth.signInUsingGmailAccount(idToken,listener);
    }

    //Retrofit

    @Override
    public Single<ApiResponse.CategoryResponse> getAllCategories()
    {
        return retrofitManagerImp.fetchCategories();
    }

    @Override
    public Single<ApiResponse.IngredientResponse> getAllIngredients() {
        return retrofitManagerImp.fetchAllIngredients();
    }

    @Override
    public Single<ApiResponse.AreaResponse>  getAreasList() {
        return retrofitManagerImp.fetchAllAreas();
    }

    @Override
    public Single<ApiResponse.MealResponse> getMealsByCategory(String category) {
        return retrofitManagerImp.fetchMealsByCategory(category);
    }

    @Override
    public Single<ApiResponse.MealResponse> getMealsByArea(String area) {
        return retrofitManagerImp.fetchMealsByAreas(area);
    }

    @Override
    public Single<ApiResponse.MealResponse> getMealsByIngredient(String ingredient) {
        return retrofitManagerImp.fetchMealsByIngredient(ingredient);
    }

    @Override
    public Single<ApiResponse.MealResponse> getRandomMeal() {
        return retrofitManagerImp.fetchRandomMeal();
    }

    @Override
    public Single<ApiResponse.MealResponse> getMealById(String id) {

        return retrofitManagerImp.fetchMealById(id);
    }

    @Override
    public Single<ApiResponse.MealResponse> searchMealByName(String mealName) {
        return retrofitManagerImp.searchMealByName(mealName);
    }

    //Database
    //FavoritesTable
    public Flowable<List<MealEntity>> getAllMeals(String userEmail) {
        return dataBaseMangerImp.getAllFavoritesByUserEmail(userEmail);
    }


    public Completable insertMeal(MealEntity mealEntity) {
        return dataBaseMangerImp.insertMeal(mealEntity);
    }

    public Completable deleteMeal(MealEntity mealEntity) {
        return dataBaseMangerImp.deleteMeal(mealEntity);
    }

    // PlanTable
    public LiveData<List<PlanEntity>> getMealsForDay(String weekDay , String userEmail) {
        return dataBaseMangerImp.getMealsForDay(weekDay, userEmail);
    }

    public Completable insertPlan(PlanEntity planEntity) {
        return dataBaseMangerImp.insertPlan(planEntity);
    }

    public Completable deletePlan(PlanEntity planEntity) {
        return dataBaseMangerImp.deletePlan(planEntity);
    }

    public Completable insertAllPlans(List<PlanEntity> planEntities) {
        return dataBaseMangerImp.insertAllPlans(planEntities);
    }

    //Sync Firebase database
    public void syncData(String userEmail)
    {
        syncServiceImp.syncData(userEmail);
    }

    //Delete recored from Firebase database
    public Completable deleteFromFirebase(String mealId) {
        return firebaseDatabaseServiceImp.deletePlanEntity(mealId);
    }

}
