package com.example.mealplanner.data.remotedata.firebaseauth;

import android.content.Context;

import com.example.mealplanner.ui.authentication.login.presenter.OnLoginWithGmailResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class FirebaseManger implements AuthModel {
    private FirebaseAuth mAuth;
    private Context context;

    public FirebaseManger(Context context)
    {
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    @Override
    public void signIn(String email, String password, FirebaseAuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signUp(String email, String password, FirebaseAuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    public void signInUsingGmailAccount(String idToken, OnLoginWithGmailResponse listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onLoginWithGmailSuccess(account);
            } else {
                listener.onLoginWithGmailError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

}

