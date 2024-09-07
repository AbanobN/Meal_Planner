package com.example.mealplanner.ui.authentication.login.presenter;

import android.util.Log;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.ui.authentication.login.view.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginPresenterImpl implements LoginPresenter , FirebaseAuthCallback , OnLoginWithGmailResponse  {
    private AppRepo repo;
    private LoginView view;
    private String userEmail;
    private String userPassword;

    public LoginPresenterImpl(AppRepo repo, LoginView view) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void onSuccess() {
        repo.writePreferences(userEmail,userPassword);
        view.onSignInSuccess();
    }

    @Override
    public void onFailure(Exception e) {
        view.onSignInFailure(e.getMessage());
    }

    @Override
    public void signIn(String email, String password) {
        userEmail = email;
        userPassword = password;
        repo.signInApp(email, password, this);
    }


    @Override
    public void signInUsingGmailAccount(String idToken) {
        repo.signInWithGoogle(idToken , this);
    }

    @Override
    public void signInAnonymously() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String guestEmail = "guest@example.com";
                repo.writePreferences(guestEmail, "guest");
                view.onSignInSuccess();
            } else {
                view.onSignInFailure(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    @Override
    public void onLoginWithGmailSuccess(GoogleSignInAccount account) {
        repo.writePreferences(account.getEmail(),account.getIdToken());
        view.onSignInSuccess();

    }

    @Override
    public void onLoginWithGmailError(String error) {
        view.onSignInFailure(error);
    }

}
