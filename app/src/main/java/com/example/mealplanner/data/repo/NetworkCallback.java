package com.example.mealplanner.data.repo;

public interface NetworkCallback {
    void onSuccess();
    void onFailure(Exception e);
}
