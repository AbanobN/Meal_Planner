package com.example.mealplanner.ui.home.favorites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.ui.home.favorites.presenter.FavoritesPresenter;
import com.example.mealplanner.ui.home.favorites.presenter.FavoritesPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesFragment extends Fragment implements FavoritesView, MealAdapter.OnMealClickListener {

    private AutoCompleteTextView searchBar;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private List<MealEntity> meals;
    private List<String> mealNames;
    private FavoritesPresenter favoritesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the presenter
        favoritesPresenter = new FavoritesPresenterImpl(this, getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.rec_view);

        // Set up RecyclerView and Adapter
        mealNames = new ArrayList<>();
        meals = new ArrayList<>();
        mealAdapter = new MealAdapter(meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mealAdapter);

        // Load meals
        favoritesPresenter.loadAllMeals();

        // Set up AutoCompleteTextView adapter
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        searchBar.setAdapter(autoCompleteAdapter);

        // Set up AutoCompleteTextView
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (query.isEmpty()) {
                    favoritesPresenter.loadAllMeals();
                } else {
                    List<MealEntity> filteredMeals = meals.stream()
                            .filter(meal -> meal.getMealName().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());

                    autoCompleteAdapter.clear();
                    autoCompleteAdapter.addAll(filteredMeals.stream().map(MealEntity::getMealName).collect(Collectors.toList()));
                    autoCompleteAdapter.notifyDataSetChanged();

                    // Update the RecyclerView with the filtered meals
                    mealAdapter.updateMealDataList(filteredMeals);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        searchBar.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedMealName = (String) parent.getItemAtPosition(position);
            MealEntity selectedMeal = meals.stream()
                    .filter(meal -> meal.getMealName().equals(selectedMealName))
                    .findFirst()
                    .orElse(null);

            if (selectedMeal != null) {
                // Call the onMealClick method when a meal is selected
                onMealClick(selectedMeal);
            }
        });

    }

    @Override
    public void showMeals(List<MealEntity> mealEntities) {
        meals.clear();
        meals.addAll(mealEntities);
        mealAdapter.updateMealDataList(meals);

        // Update AutoComplete suggestions
        mealNames.clear();
        mealNames.addAll(mealEntities.stream().map(MealEntity::getMealName).collect(Collectors.toList()));
        ((ArrayAdapter<String>) searchBar.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void showMealAdded() {
        // Handle meal added success and refresh the list
        favoritesPresenter.loadAllMeals();
    }

    @Override
    public void showMealRemoved() {
        // Handle meal removed success and refresh the list
        favoritesPresenter.loadAllMeals();
    }

    @Override
    public void showError(String error) {
        // Show error message (you might want to implement this)
    }

    @Override
    public void onMealClick(MealEntity mealEntity) {
        // Handle meal item click (implement as needed)
        FavoritesFragmentDirections.ActionFavoritesToDetails action =
                FavoritesFragmentDirections.actionFavoritesToDetails(mealEntity.getId(),"abanob@gmail.com");
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
        searchBar.setText("");
    }

    @Override
    public void onMealCancel(MealEntity mealEntity) {
        favoritesPresenter.removeMeal(mealEntity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up resources related to the presenter
        if (favoritesPresenter instanceof FavoritesPresenterImpl) {
            ((FavoritesPresenterImpl) favoritesPresenter).onDestroy();
        }
    }
}
