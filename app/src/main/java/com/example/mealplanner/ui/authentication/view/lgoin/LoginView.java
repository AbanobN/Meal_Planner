package com.example.mealplanner.ui.authentication.view.lgoin;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void onSignInSuccess();
    void onSignInFailure(String error);
}
