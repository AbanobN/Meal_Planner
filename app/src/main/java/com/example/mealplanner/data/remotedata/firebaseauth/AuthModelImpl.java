package com.example.mealplanner.data.remotedata.firebaseauth;
import android.util.Log;

import com.example.mealplanner.data.repo.NetworkCallback;
import com.example.mealplanner.data.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;

public class AuthModelImpl implements AuthModel {
    private FirebaseAuth mAuth;

    public AuthModelImpl() {

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password, NetworkCallback callback) {
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
    public void signUp(String email, String password,NetworkCallback callback) {
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
}

