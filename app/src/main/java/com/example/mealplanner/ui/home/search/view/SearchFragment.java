package com.example.mealplanner.ui.home.search.view;

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
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.ui.home.search.presenter.SearchPresenter;
import com.example.mealplanner.ui.home.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements SearchView, CategoryAdapter.OnCategoryClickListener, IngredientAdapter.OnIngredientClickListener, AreaAdapter.OnAreaClickListener , MealAdapter.OnMealClickListener{

    private ChipGroup chipGroup;
    private RecyclerView recyclerView;
    private AutoCompleteTextView searchBar;
    private SearchPresenterImp presenter;

    private CategoryAdapter categoryAdapter;
    private IngredientAdapter ingredientAdapter;
    private AreaAdapter areaAdapter;
    private MealAdapter mealAdapter;

    private List<AreaData> countries;
    private List<CategorieData> categories;
    private List<IngredientData> ingredientData;
    private List<MealData> meals;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new SearchPresenterImp(getContext(), this);

        presenter.loadAllMeals();

        searchBar = view.findViewById(R.id.searchBar);
        chipGroup = view.findViewById(R.id.chipGroup);
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        meals = new ArrayList<>();
        mealAdapter = new MealAdapter(meals , this);

        countries = new ArrayList<>();
        areaAdapter = new AreaAdapter(countries, this);

        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories, this);

        ingredientData = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(ingredientData, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        updateChipsItems();

        observeSearchBar();
    }

    private void updateChipsItems() {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        String filterBy = chip.getText().toString();
                        switch (filterBy) {
                            case "Category":
                                presenter.getAllCategories();
                                break;
                            case "Area":
                                presenter.getAllCountries();
                                break;
                            case "Ingredients":
                                presenter.getAllIngredients();
                                break;
                        }
                    }
                }
            });
        }
    }

    private void observeSearchBar() {
        Disposable searchDisposable = Observable.create(emitter -> {
                    searchBar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            emitter.onNext(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                })
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    presenter.searchMeals(query.toString());
                });
    }

    @Override
    public void updateMealsList(ArrayList<MealData> meals) {
        this.meals = meals;
        mealAdapter.updateMealDataList(meals);
        recyclerView.setAdapter(mealAdapter);
    }

    @Override
    public void updateCategoryList(List<CategorieData> categories) {
        this.categories = categories;
        categoryAdapter.updateCategoryDataList(categories);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void updateCountryList(List<AreaData> areas) {
        this.countries = areas;
        areaAdapter.updatedata(areas);
        recyclerView.setAdapter(areaAdapter);
    }

    @Override
    public void updateIngredientsList(List<IngredientData> ingredientData) {
        this.ingredientData = ingredientData;
        ingredientAdapter.updatedata(ingredientData);
        recyclerView.setAdapter(ingredientAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clearDisposables();
    }

    @Override
    public void onAreaClick(AreaData areaData) {
        String data = areaData.getAreaName();
        presenter.onAreaClick(data);
    }

    @Override
    public void onCategoryClick(CategorieData categorieData) {
        String data = categorieData.getStrCategory();
        presenter.onCategoryClick(data);
    }

    @Override
    public void onIngredientClick(IngredientData ingredientData) {
        String data = ingredientData.getName();
        presenter.onIngredientClick(data);
    }

    @Override
    public void onMealClick(MealData mealData) {
        String data = mealData.getIdMeal();
        SearchFragmentDirections.ActionSearchToDetails action = SearchFragmentDirections.actionSearchToDetails(data,"Search");
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
    }

    @Override
    public void deleteFromFav(MealData meal) {
        presenter.removeFromFavorite(new MealEntity(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),""));
    }

    @Override
    public void insertIntoFav(MealData meal) {
        presenter.removeFromFavorite(new MealEntity(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),""));
    }

    public void handleError(Throwable t)
    {
        Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void updateFavoriteList(List<MealEntity> mealEntities)
    {
        mealAdapter.updateFavorites(mealEntities);
    }

    public void showToast(String msg)
    {
        Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();
    }

}
