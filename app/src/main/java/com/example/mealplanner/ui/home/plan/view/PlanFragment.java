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
import com.example.mealplanner.data.localdata.database.MealEntity;
import com.example.mealplanner.ui.home.favorites.view.MealAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements MealAdapter.OnMealClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinnerWeekdays = view.findViewById(R.id.spinner_weekdays);
        TextView dayMeals = view.findViewById(R.id.daymeal);
        RecyclerView recyclerView = view.findViewById(R.id.rec_view);
        List<MealEntity> meals = new ArrayList<>();
        MealAdapter mealAdapter = new MealAdapter(meals, this);;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mealAdapter);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getContext(),
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
                    // Log the selected item
                    dayMeals.setVisibility(View.VISIBLE);
                    dayMeals.setText("Day Meals for " + selectedWeekday);
                }
                else {
                    dayMeals.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onMealClick(MealEntity mealEntity) {

    }

    @Override
    public void onMealCancel(MealEntity mealEntity) {

    }
}