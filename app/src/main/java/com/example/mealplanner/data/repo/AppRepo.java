package com.example.mealplanner.data.repo;

import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.remotedata.retrofit.CategoryCallback;
import com.example.mealplanner.data.remotedata.retrofit.MealCallback;
import com.example.mealplanner.data.remotedata.retrofit.OneMealCallback;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;

public class AppRepo implements Repository{
    private static AppRepo instance = null;
    private AuthModelImpl firebaseAuth;
    private SharedPerferencesImp shPer;
    private RetrofitClient retrofit;

    private AppRepo(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer , RetrofitClient retrofit) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
        this.retrofit = retrofit;
    }

    public static AppRepo getInstance(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer, RetrofitClient retrofit)
    {
        if(instance == null)
        {
            instance = new AppRepo(firebaseAuth,shPer , retrofit);
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

//    public void getAllCountries(AreaCallback areaCallback)
//    {
//        retrofit.fetchAreaList(areaCallback);
//    }
//
//    public void getAllIngredients(IngredientCallback ingredientCallback)
//    {
//        retrofit.fetchAllIngredients(ingredientCallback);
//    }


}
