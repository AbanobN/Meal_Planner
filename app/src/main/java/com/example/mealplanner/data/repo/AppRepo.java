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

    private AppRepo(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManagerImp retrofitManagerImp, DataBaseMangerImp dataBaseMangerImp) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
        this.retrofitManagerImp = retrofitManagerImp;
        this.dataBaseMangerImp = dataBaseMangerImp;
        this.firebaseDatabaseServiceImp = new FirebaseDatabaseServiceImp();
        this.syncServiceImp = new SyncServiceImp(firebaseDatabaseServiceImp, dataBaseMangerImp);

    }

    public static AppRepo getInstance(FirebaseManger firebaseAuth, SharedPerferencesManger shPer, RetrofitManagerImp retrofitManagerImp, DataBaseMangerImp dataBaseMangerImp) {
        if (instance == null) {
            instance = new AppRepo(firebaseAuth, shPer, retrofitManagerImp, dataBaseMangerImp);
        }
        return instance;
    }

    //Prefrences
    public boolean readPreferences() {
        return shPer.readFromPreferences();
    }

    public void writePreferences(String email, String password) {
        shPer.addToPreferences(email, password);
    }

    public void removePreferences() {
        shPer.removePreferences();
    }

    public String getUserEmail(){
        return shPer.getEmail();
    }

    // firebase Auth
    public void signInApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signIn(email, password, callback);
    }

    public void signUpApp(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signUp(email, password, callback);
    }

    public void signOutApp() {
        firebaseAuth.signOut();
    }

    //Retrofit
    public Single<ApiResponse.CategoryResponse> getAllCategories()
    {
        return retrofitManagerImp.fetchCategories();
    }

    public Single<ApiResponse.IngredientResponse> getAllIngredients() {
        return retrofitManagerImp.fetchAllIngredients();
    }

    public Single<ApiResponse.AreaResponse>  getAreasList() {
        return retrofitManagerImp.fetchAreasList();
    }

    // complite from here

    public Single<List<MealData>> getMealsByCategory(String category) {
        return retrofitManagerImp.fetchMealsByCategory(category);
    }

    public Single<List<MealData>> getMealsByArea(String area) {
        return retrofitManagerImp.fetchMealsByArea(area);
    }

    public Single<List<MealData>> getMealsByIngredient(String ingredient) {
        return retrofitManagerImp.fetchMealsByIngredient(ingredient);
    }

    public Single<MealData> getRandomMeal() {
        return retrofitManagerImp.fetchRandomMeal();
    }

    public Single<MealData> getMealById(String id) {

        return retrofitManagerImp.fetchMealById(id);
    }

    public Single<List<MealData>> searchMealByName(String mealName) {
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
