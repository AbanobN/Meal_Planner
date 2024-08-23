package com.example.mealplanner.ui.home.plan.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.data.localdata.database.PlanEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragment extends Fragment implements MealAdapter.OnMealClickListener {

    private AppRepo repo;
    private MealAdapter mealAdapter;
    private List<PlanEntity> mealList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = (AppRepo) RepositoryProvider.provideRepository(getContext());

        // Generate and insert random data when the fragment is created
        List<PlanEntity> randomMeals = generateRandomData();
        insertPlans(randomMeals);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinnerWeekdays = view.findViewById(R.id.spinner_weekdays);
        TextView dayMeals = view.findViewById(R.id.daymeal);
        RecyclerView recyclerView = view.findViewById(R.id.rec_view);

        mealList = new ArrayList<>();
        mealAdapter = new MealAdapter(mealList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mealAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.weekdays_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerWeekdays.setAdapter(adapter);

        spinnerWeekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWeekday = parent.getItemAtPosition(position).toString();

                if (!selectedWeekday.equals("Choose a day")) {
                    dayMeals.setVisibility(View.VISIBLE);
                    dayMeals.setText("Day Meals for " + selectedWeekday);
                    loadMealsForDay(selectedWeekday);
                } else {
                    dayMeals.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });
    }

    private List<PlanEntity> generateRandomData() {
        List<PlanEntity> plans = new ArrayList<>();
        Random random = new Random();
        String[] sampleMeals = {"Pizza", "Burger", "Pasta", "Salad", "Sushi", "Tacos", "Pancakes", "Waffles", "Burritos", "Sandwich"};
        String[] weekdays = getResources().getStringArray(R.array.weekdays_array);

        for (int i = 0; i < 10; i++) {
            String mealName = sampleMeals[random.nextInt(sampleMeals.length)];
            String mealUrlImg = "https://example.com/image" + (i + 1) + ".jpg"; // Replace with valid image URLs if needed
            String mealID = "ID" + (i + 1); // Generate a unique ID for each meal
            String userEmail = "user" + (i + 1) + "@example.com"; // Generate a dummy email or use actual user email
            String weekday = weekdays[random.nextInt(weekdays.length)]; // Randomly select a weekday

            PlanEntity planEntity = new PlanEntity(mealID, mealName, mealUrlImg, userEmail, weekday);
            plans.add(planEntity);
        }
        return plans;
    }

    private void insertPlans(List<PlanEntity> plans) {
        for (PlanEntity plan : plans) {
            repo.insertPlan(plan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        // Data inserted successfully
                        Log.d("PlanFragment", "Data inserted successfully: " + plan.getMealName());
                    }, throwable -> {
                        // Handle any errors
                        Log.e("PlanFragment", "Error inserting data", throwable);
                    });
        }
    }

    private void loadMealsForDay(String weekday) {
        repo.getAllPlansByDay(weekday).observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                mealAdapter.updateMealDataList(meals);
            } else {
                mealAdapter.updateMealDataList(new ArrayList<>()); // Clear list if no meals are available
            }
        });
    }

    @Override
    public void onMealClick(PlanEntity planEntity) {
        // Handle meal click
    }

    @Override
    public void onMealCancel(PlanEntity planEntity) {
        repo.deletePlan(planEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // Data deleted successfully
                    Log.d("PlanFragment", "Data deleted successfully: " + planEntity.getMealName());
                }, throwable -> {
                    // Handle any errors
                    Log.e("PlanFragment", "Error deleting data", throwable);
                });
    }
}
