package com.example.mealplanner.data.remotedata.firebaseauth;

public interface FirebaseAuthCallback {
    void onSuccess();
    void onFailure(Exception e);
}
