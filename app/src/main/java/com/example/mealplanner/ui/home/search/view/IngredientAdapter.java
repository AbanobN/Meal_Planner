package com.example.mealplanner.ui.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.IngredientData;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<IngredientData> ingredientData;
    private OnIngredientClickListener listener;

    public IngredientAdapter(List<IngredientData> ingredientData, OnIngredientClickListener listener) {
        this.ingredientData = ingredientData;
        this.listener = listener;
    }

    public void setString(List<IngredientData> ingredientData) {
        this.ingredientData = ingredientData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.search_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ingredientName.setText(ingredientData.get(position).getName());

        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + ingredientData.get(position).getName() + "-Small.png")
                .into(holder.ingredientImage);

        IngredientData data = ingredientData.get(position);

        holder.bind(data , listener);

    }

    public void updatedata(List<IngredientData> ingredientData) {
        this.ingredientData = ingredientData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredientData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        ImageView ingredientImage;
        ImageButton favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.search_card_title);
            ingredientImage = itemView.findViewById(R.id.search_card_img);
            favBtn = itemView.findViewById(R.id.favButton);
        }

        public void bind(IngredientData data , OnIngredientClickListener listener)
        {
            favBtn.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> listener.onIngredientClick(data));
        }
    }

    public interface OnIngredientClickListener {
        void onIngredientClick(IngredientData data);
    }
}