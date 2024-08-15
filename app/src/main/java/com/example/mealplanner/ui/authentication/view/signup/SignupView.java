package com.example.mealplanner.ui.authentication.view.signup;

public interface SignupView {
    void onSignUpSuccess();
    void onSignUpFailure(String error);
    void passwordDoNotMatch();
}
