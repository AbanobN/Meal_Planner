package com.example.mealplanner.ui.home.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealData> mealDataList;

    public MealAdapter(List<MealData> mealDataList) {
        this.mealDataList = mealDataList;
    }

    public void updateMealDataList(List<MealData> newMealDataList) {
        this.mealDataList = newMealDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealData data = mealDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mealDataList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        Button mealDetailsBtn;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealCard_title);
            mealImage = itemView.findViewById(R.id.mealCard_img);
            mealDetailsBtn = itemView.findViewById(R.id.mealCard_btn);

        }

        public void bind(MealData data) {
            mealName.setText(data.getStrMeal());
            Glide.with(itemView.getContext()).load(data.getStrMealThumb()).into(mealImage);
            mealDetailsBtn.setOnClickListener((e) -> {
                HomeFragmentDirections.ActionHomeToDetailsFragment action =
                        HomeFragmentDirections.actionHomeToDetailsFragment(data.getIdMeal(),"");
                Navigation.findNavController(itemView).navigate(action);
            });
        }
    }
}
