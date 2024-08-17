package com.example.mealplanner.data.repo;

import android.util.Log;

import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;

public class AppRepo implements Repository{
    private static AppRepo instance = null;
    private AuthModelImpl firebaseAuth;
    private SharedPerferencesImp shPer;

    private AppRepo(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer) {
        this.firebaseAuth = firebaseAuth;
        this.shPer = shPer;
    }

    public static AppRepo getInstance(AuthModelImpl firebaseAuth, SharedPerferencesImp shPer)
    {
        if(instance == null)
        {
            instance = new AppRepo(firebaseAuth,shPer);
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
    public void signInApp(String email, String password, NetworkCallback callback) {
        firebaseAuth.signIn(email,password,callback);
    }

    @Override
    public void signUpApp(String email, String password, NetworkCallback callback) {

        firebaseAuth.signUp(email,password,callback);
    }

    @Override
    public void signOutApp() {
        firebaseAuth.signOut();
    }
}
