package com.example.mealplanner.data.repo;

import com.example.mealplanner.data.firebase.AuthModel;

public interface Repository {
    public boolean readPrefernces();
    public void writePrefernces(String email, String password);

    public void signInApp(String email, String password, Repository.NetworkCallback callback);
    public void signUpApp(String email, String password, Repository.NetworkCallback callback);
    void signOutApp();

    public interface NetworkCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

}
