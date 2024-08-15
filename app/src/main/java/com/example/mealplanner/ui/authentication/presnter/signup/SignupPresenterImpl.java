package com.example.mealplanner.ui.authentication.presnter.signup;

import com.example.mealplanner.data.remotedata.firebaseauth.AuthModel;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.NetworkCallback;
import com.example.mealplanner.data.repo.Repository;
import com.example.mealplanner.ui.authentication.view.signup.SignupView;

public class SignupPresenterImpl implements SignupPresenter , NetworkCallback {
    private AppRepo model;
    private SignupView view;

    public SignupPresenterImpl(SignupView view, AppRepo model) {
        this.view = view;
        this.model = model;
    }

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

    @Override
    public void signUp(String email, String password) {
        view.showLoading();
        model.signUpApp(email, password,this);
    }

}
