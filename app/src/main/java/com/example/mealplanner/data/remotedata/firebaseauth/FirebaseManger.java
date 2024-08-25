package com.example.mealplanner.data.remotedata.firebaseauth;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManger implements AuthModel {
    private FirebaseAuth mAuth;

    public FirebaseManger() {

        mAuth = FirebaseAuth.getInstance();
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

}

