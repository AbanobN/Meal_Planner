package com.example.mealplanner.ui.authentication.presnter.login;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.NetworkCallback;
import com.example.mealplanner.ui.authentication.view.lgoin.LoginView;

public class LoginPresenterImpl implements LoginPresenter , NetworkCallback {
    private AppRepo model;
    private LoginView view;

    public LoginPresenterImpl(LoginView view, AppRepo model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSuccess() {
        view.onSignInSuccess();
    }

    @Override
    public void onFailure(Exception e) {
        view.onSignInFailure(e.getMessage());
    }

    @Override
    public void signIn(String email, String password) {
        model.signInApp(email, password, this);
    }

}
