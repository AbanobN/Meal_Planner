package com.example.mealplanner.data.firebase;

import com.example.mealplanner.data.repo.Repository;

public interface AuthModel {
    void signIn(String email, String password, Repository.NetworkCallback callback);
    void signUp(String email, String password, Repository.NetworkCallback callback);
    void signOut();

}
