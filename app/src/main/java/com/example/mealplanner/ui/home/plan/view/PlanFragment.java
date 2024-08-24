package com.example.mealplanner.ui.home.plan.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.ui.home.plan.presenter.PlanPresenterImp;
import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements PlanAdapter.OnPlanClickListener{
    private PlanPresenterImp presenter;
    private PlanAdapter mealAdapter;
    private TextView dayMeals;
    private RecyclerView recyclerView;
    private Spinner spinnerWeekdays;
    private List<PlanEntity> meals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlanPresenterImp(this,this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerWeekdays = view.findViewById(R.id.spinner_weekdays);
        dayMeals = view.findViewById(R.id.daymeal);
        recyclerView = view.findViewById(R.id.rec_view);
        meals = new ArrayList<>();
        mealAdapter = new PlanAdapter(meals, this);
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
                    presenter.loadMealsForDay(selectedWeekday);
                    showDayMealsLabel(selectedWeekday);
                } else {
                    hideDayMealsLabel();
                    mealAdapter.updatePlanDataList(new ArrayList<>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void showMealsForSelectedDay(List<PlanEntity> meals) {
        mealAdapter.updatePlanDataList(meals);
    }


    public void showDayMealsLabel(String day) {
        dayMeals.setVisibility(View.VISIBLE);
        if(!meals.isEmpty())
        {
            dayMeals.setText("Day Meals for " + day);
        }
        else{
            dayMeals.setText("You have no Meals for " + day);
        }
    }

    public void hideDayMealsLabel() {
        dayMeals.setVisibility(View.GONE);
    }


    public void showError(String message) {
        Toast.makeText(getContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPlanClick(PlanEntity planEntity) {
        PlanFragmentDirections.ActionPlanToDetails action =
                PlanFragmentDirections.actionPlanToDetails(planEntity.getId(),"Plan");
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
    }

    @Override
    public void onPlanCancel(PlanEntity planEntity) {
        presenter.deletePlan(planEntity);
        mealAdapter.updatePlanDataList(new ArrayList<>());
    }
}