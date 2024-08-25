package com.example.mealplanner.ui.authentication.login.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.authentication.login.view.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginPresenterImpl implements LoginPresenter , FirebaseAuthCallback {
    private AppRepo model;
    private LoginView view;
    private String userEmail;
    private String userPassword;
    private Context context;

    public LoginPresenterImpl(Context context, LoginView view) {
        this.view = view;
        this.context = context;
        this.model = (AppRepo) RepositoryProvider.provideRepository(context);
    }

    @Override
    public void onSuccess() {
        model.writePreferences(userEmail,userPassword);
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
        model.signInApp(email, password, this);
    }

    public void signInUsingGmailAccount(String idToken, OnLoginWithGmailResponse listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onLoginWithGmailSuccess();
                model.writePreferences(account.getEmail(),account.getIdToken());
            } else {
                listener.onLoginWithGmailError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public void signInAnonymously() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String guestEmail = "guest@example.com";
                model.writePreferences(guestEmail, "guest");
                view.onSignInSuccess();
            } else {
                view.onSignInFailure(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

}
