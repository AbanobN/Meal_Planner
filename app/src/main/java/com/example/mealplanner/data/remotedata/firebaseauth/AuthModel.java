package com.example.mealplanner.data.remotedata.firebaseauth;

public interface AuthModel {
    void signIn(String email, String password, FirebaseAuthCallback callback);
    void signUp(String email, String password, FirebaseAuthCallback callback);
    void signOut();

}
