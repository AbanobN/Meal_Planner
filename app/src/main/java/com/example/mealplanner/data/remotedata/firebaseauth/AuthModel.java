package com.example.mealplanner.data.remotedata.firebaseauth;

import com.example.mealplanner.data.repo.NetworkCallback;
import com.example.mealplanner.data.repo.Repository;

public interface AuthModel {
    void signIn(String email, String password, NetworkCallback callback);
    void signUp(String email, String password, NetworkCallback callback);
    void signOut();

}
