package com.example.mealplanner.data.remotedata.firebasedatabase;

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

public class FirebaseDatabaseService {

    private final DatabaseReference databaseReference;

    public FirebaseDatabaseService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("PlanMeals");
    }

    public Completable addPlanEntity(PlanEntity planEntity) {
        return Completable.create(emitter -> {
            String planId = planEntity.getId();

            databaseReference.child(planId).setValue(planEntity)
                    .addOnSuccessListener(aVoid -> emitter.onComplete()) // Operation succeeded
                    .addOnFailureListener(emitter::onError); // Operation failed
        });
    }

    public Completable deletePlanEntity(String planId) {
        return Completable.create(emitter -> {
            databaseReference.child(planId).removeValue()
                    .addOnSuccessListener(aVoid -> emitter.onComplete()) // Operation succeeded
                    .addOnFailureListener(emitter::onError); // Operation failed
        });
    }


    public Completable addPlanEntityList(List<PlanEntity> planEntities) {
        List<Completable> completables = planEntities.stream()
                .map(this::addPlanEntity)
                .collect(Collectors.toList());

        return Completable.merge(completables);
    }

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
                .subscribeOn(Schedulers.io()) // Perform the Firebase operation on the IO thread
                .observeOn(Schedulers.computation()) // Observe the result on the computation thread
                .flatMap(planEntities -> {
                    if (planEntities != null && !planEntities.isEmpty()) {
                        return Single.just(planEntities);
                    } else {
                        return Single.error(new Exception("No data found"));
                    }
                });
    }

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
                            emitter.onNext(planEntities); // Emit the updated list of PlanEntities
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException()); // Emit an error if the data read is cancelled
                        }
                    };

                    // Attach the listener to the database reference
                    databaseReference.addValueEventListener(valueEventListener);

                    // Remove the listener when the Observable is disposed
                    emitter.setCancellable(() -> databaseReference.removeEventListener(valueEventListener));
                })
                .subscribeOn(Schedulers.io()); // Perform the Firebase operation on the IO thread
    }

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

                            // Emit an empty list if no data is found
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
