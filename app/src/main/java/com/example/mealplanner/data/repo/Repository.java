package com.example.mealplanner.data.repo;

public interface Repository {
    public boolean readPrefernces();
    public void writePrefernces(String email, String password);

    public void signInApp(String email, String password, NetworkCallback callback);
    public void signUpApp(String email, String password, NetworkCallback callback);
    void signOutApp();

}
