package com.example.mealplanner.ui.authentication.presnter.login;

import com.example.mealplanner.data.firebase.AuthModel;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.Repository;
import com.example.mealplanner.ui.authentication.view.lgoin.LoginView;

public class LoginPresenterImpl implements LoginPresenter{
    private AppRepo model;
    private LoginView view;

    public LoginPresenterImpl(LoginView view, AppRepo model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void signIn(String email, String password) {
        view.showLoading();
        model.signInApp(email, password, new Repository.NetworkCallback(){
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onSignInSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                view.hideLoading();
                view.onSignInFailure(e.getMessage());
            }
        });
    }
}
