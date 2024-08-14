package com.example.mealplanner.ui.authentication.presnter.signup;

import com.example.mealplanner.data.firebase.AuthModel;
import com.example.mealplanner.ui.authentication.view.signup.SignupView;

public class SignupPresenterImpl implements SignupPresenter{
    private AuthModel model;
    private SignupView view;

    public SignupPresenterImpl(SignupView view, AuthModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void signUp(String email, String password) {
        view.showLoading();
        model.signUp(email, password, new AuthModel.AuthCallback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onSignUpSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                view.hideLoading();
                view.onSignUpFailure(e.getMessage());
            }
        });
    }
}
