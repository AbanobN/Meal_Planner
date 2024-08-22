package com.example.mealplanner.ui.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnIngredientClickListener listener;

    public IngredientAdapter(List<Ingredient> ingredients , OnIngredientClickListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    public void setString(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.search_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ingredientName.setText(ingredients.get(position).getName());

        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + ingredients.get(position).getName() + "-Small.png")
                .into(holder.ingredientImage);

        Ingredient data = ingredients.get(position);

        holder.bind(data , listener);

    }

    public void updatedata(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        ImageView ingredientImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.search_card_title);
            ingredientImage = itemView.findViewById(R.id.search_card_img);
        }

        public void bind(Ingredient data , OnIngredientClickListener listener)
        {
            itemView.setOnClickListener(v -> listener.onIngredientClick(data));
        }
    }

    public interface OnIngredientClickListener {
        void onIngredientClick(Ingredient data);
    }
}