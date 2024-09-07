package com.example.mealplanner.ui.authentication.signup.presenter;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.ui.authentication.signup.view.SignupView;

public class SignupPresenterImpl implements SignupPresenter , FirebaseAuthCallback {
    private AppRepo repo;
    private SignupView view;
    private String userEmail;
    private String userPassword;

    public SignupPresenterImpl(AppRepo repo, SignupView view) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void onSuccess() {
        repo.writePreferences(userEmail,userPassword);
        view.onSignUpSuccess();
    }

    @Override
    public void onFailure(Exception e) {
        view.onSignUpFailure(e.getMessage());
    }

    @Override
    public void signUp(String email, String password , String rePassword) {
        if(!password.equals(rePassword))
        {
            view.passwordDoNotMatch();
            return;
        }
        userEmail = email;
        userPassword = password;
        repo.signUpApp(email, password,this);
    }

}
