//package com.example.mealplanner.ui.home.plan.view;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.example.mealplanner.R;
//import com.example.mealplanner.data.model.MealEntity;
//import com.example.mealplanner.data.localdata.database.MealRepository;
//import com.example.mealplanner.ui.home.favorites.view.MealAdapter;
//import com.example.mealplanner.ui.home.plan.presenter.PlanPresenter;
//import com.example.mealplanner.ui.home.plan.presenter.PlanPresenterImp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PlanFragment extends Fragment implements MealAdapter.OnMealClickListener, PlanFragmentView {
//    private PlanPresenter presenter;
//    private MealAdapter mealAdapter;
//    private TextView dayMeals;
//    private RecyclerView recyclerView;
//    private Spinner spinnerWeekdays;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        MealRepository mealRepository = new MealRepository(getContext()); // Assuming you have a MealRepository class
//        presenter = new PlanPresenterImp(mealRepository);
//        presenter.attachView(this.getView());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_plan, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        spinnerWeekdays = view.findViewById(R.id.spinner_weekdays);
//        dayMeals = view.findViewById(R.id.daymeal);
//        recyclerView = view.findViewById(R.id.rec_view);
//        List<MealEntity> meals = new ArrayList<>();
//        mealAdapter = new MealAdapter(meals, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(mealAdapter);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this.getContext(),
//                R.array.weekdays_array,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerWeekdays.setAdapter(adapter);
//
//        spinnerWeekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedWeekday = parent.getItemAtPosition(position).toString();
//
//                if (!selectedWeekday.equals("Choose a day")) {
//                    presenter.fetchMealsForDay(selectedWeekday);
//                } else {
//                    hideDayMealsLabel();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        presenter.detachView();
//    }
//
//    @Override
//    public void showMealsForSelectedDay(List<MealEntity> meals) {
//        mealAdapter.updateMealDataList(meals);
//    }
//
//    @Override
//    public void showDayMealsLabel(String day) {
//        dayMeals.setVisibility(View.VISIBLE);
//        dayMeals.setText("Day Meals for " + day);
//    }
//
//    @Override
//    public void hideDayMealsLabel() {
//        dayMeals.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showError(String message) {
//        // Handle error (e.g., show a Toast or Snackbar)
//    }
//
//    @Override
//    public void onMealClick(MealEntity mealEntity) {
//        // Handle meal click
//    }
//
//    @Override
//    public void onMealCancel(MealEntity mealEntity) {
//        // Handle meal cancel
//    }
//}