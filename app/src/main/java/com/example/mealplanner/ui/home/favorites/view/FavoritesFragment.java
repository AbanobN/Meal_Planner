package com.example.mealplanner.ui.home.favorites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.ui.home.search.view.MealAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements MealAdapter.OnMealClickListener {

    private AutoCompleteTextView searchBar;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private List<MealData> meals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.rec_view);

        meals = new ArrayList<>();
        mealAdapter = new MealAdapter(meals,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mealAdapter);
        

    }

    @Override
    public void onMealClick(MealData mealData) {

    }
}