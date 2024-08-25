package com.example.mealplanner.data.remotedata.firebasedatabase;

import android.util.Log;

import com.example.mealplanner.data.localdata.database.DataBaseManger;
import com.example.mealplanner.data.model.PlanEntity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SyncService {

    private final FirebaseDatabaseService firebaseDatabaseService;
    private final DataBaseManger dataBaseManger;
    private Disposable syncDisposable;

    public SyncService(FirebaseDatabaseService firebaseDatabaseService, DataBaseManger dataBaseManger) {
        this.firebaseDatabaseService = firebaseDatabaseService;
        this.dataBaseManger = dataBaseManger;
    }

    public void syncData(String userEmail) {
        syncDisposable = Single.zip(
                        firebaseDatabaseService.getAllPlanEntitiesByUserEmail(userEmail),
                        dataBaseManger.getPlansByUserEmail(userEmail),
                        (firebasePlans, localPlans) -> {
                            Set<PlanEntity> firebaseSet = new HashSet<>(firebasePlans);
                            Set<PlanEntity> localSet = new HashSet<>(localPlans);

                            Set<PlanEntity> toAddToFirebase = new HashSet<>(localSet);
                            toAddToFirebase.removeAll(firebaseSet);

                            Set<PlanEntity> toAddToLocal = new HashSet<>(firebaseSet);
                            toAddToLocal.removeAll(localSet);

                            Completable firebaseCompletable = toAddToFirebase.isEmpty() ?
                                    Completable.complete() :
                                    firebaseDatabaseService.addPlanEntityList(new ArrayList<>(toAddToFirebase));

                            Completable localCompletable = toAddToLocal.isEmpty() ?
                                    Completable.complete() :
                                    dataBaseManger.insertAllPlans(new ArrayList<>(toAddToLocal))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread());

                            // Return a single that emits the concatenated Completable
                            return Completable.concatArray(firebaseCompletable, localCompletable);
                        }
                )
                .flatMapCompletable(completable -> completable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d("SyncService", "Sync completed successfully"),
                        throwable -> Log.e("SyncService", "Sync error: ", throwable)
                );
    }


    // Call this method when you want to dispose of the subscription
    public void dispose() {
        if (syncDisposable != null && !syncDisposable.isDisposed()) {
            syncDisposable.dispose();
        }
    }
}

