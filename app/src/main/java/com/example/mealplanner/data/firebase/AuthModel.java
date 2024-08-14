package com.example.mealplanner.data.firebase;

public interface AuthModel {
    void signIn(String email, String password, AuthCallback callback);
    void signUp(String email, String password, AuthCallback callback);
    void signOut();

    interface AuthCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
