package com.example.mealplanner.ui.authentication.signup.view;

public interface SignupView {
    void onSignUpSuccess();
    void onSignUpFailure(String error);
    void passwordDoNotMatch();
}
