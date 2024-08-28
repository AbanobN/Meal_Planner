package com.example.mealplanner.data.remotedata.firebasedatabase;

import android.util.Log;

import com.example.mealplanner.data.model.PlanEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FirebaseDatabaseServiceImp implements FirebaseDatabaseService {

    private final DatabaseReference databaseReference;

    public FirebaseDatabaseServiceImp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("PlanMeals");
    }

    @Override
    public Completable addPlanEntity(PlanEntity planEntity) {
        return Completable.create(emitter -> {
            String planId = planEntity.getId();

            databaseReference.child(planId).setValue(planEntity)
                    .addOnSuccessListener(aVoid -> emitter.onComplete()) // Operation succeeded
                    .addOnFailureListener(emitter::onError); // Operation failed
        });
    }

    @Override
    public Completable deletePlanEntity(String mealId) {
        return Completable.create(emitter -> {
            DatabaseReference mealRef = databaseReference.child(mealId);

            mealRef.removeValue()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(exception -> {
                        // Log the exception details
                        Log.e("Delete", "Error deleting plan: " + exception.getMessage());
                        emitter.onError(exception);
                    });
        });
    }



    @Override
    public Completable addPlanEntityList(List<PlanEntity> planEntities) {
        List<Completable> completables = planEntities.stream()
                .map(this::addPlanEntity)
                .collect(Collectors.toList());

        return Completable.merge(completables);
    }

    @Override
    public Single<List<PlanEntity>> getAllPlanEntities() {
        return Single.<List<PlanEntity>>create(emitter -> {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<PlanEntity> planEntities = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                PlanEntity planEntity = snapshot.getValue(PlanEntity.class);
                                if (planEntity != null) {
                                    planEntities.add(planEntity);
                                }
                            }
                            emitter.onSuccess(planEntities);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(planEntities -> {
                    if (planEntities != null && !planEntities.isEmpty()) {
                        return Single.just(planEntities);
                    } else {
                        return Single.error(new Exception("No data found"));
                    }
                });
    }

    @Override
    public Observable<List<PlanEntity>> observeAllPlanEntities() {
        return Observable.<List<PlanEntity>>create(emitter -> {
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<PlanEntity> planEntities = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                PlanEntity planEntity = snapshot.getValue(PlanEntity.class);
                                if (planEntity != null) {
                                    planEntities.add(planEntity);
                                }
                            }
                            emitter.onNext(planEntities);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    };

                    databaseReference.addValueEventListener(valueEventListener);


                    emitter.setCancellable(() -> databaseReference.removeEventListener(valueEventListener));
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<PlanEntity>> getAllPlanEntitiesByUserEmail(String userEmail) {
        return Single.<List<PlanEntity>>create(emitter -> {
                    Query query = databaseReference.orderByChild("userEmail").equalTo(userEmail);

                    query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<PlanEntity> planEntities = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                PlanEntity planEntity = snapshot.getValue(PlanEntity.class);
                                if (planEntity != null) {
                                    planEntities.add(planEntity);
                                }
                            }

                            emitter.onSuccess(planEntities);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }



}
