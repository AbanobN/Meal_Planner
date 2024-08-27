package com.example.mealplanner.data.remotedata.firebasedatabase;

public interface SyncService {
    void syncData(String userEmail);

    // Call this method when you want to dispose of the subscription
    void dispose();
}
