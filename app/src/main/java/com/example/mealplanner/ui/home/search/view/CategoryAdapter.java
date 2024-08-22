package com.example.mealplanner.ui.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategorieData> categorieDataList;
    private OnCategoryClickListener listener;

    public CategoryAdapter(List<CategorieData> categorieDataList,OnCategoryClickListener listener) {
        this.categorieDataList = categorieDataList;
        this.listener = listener;
    }

    public void updateCategoryDataList(List<CategorieData> newCategorieDataList) {
        this.categorieDataList = newCategorieDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategorieData data = categorieDataList.get(position);
        holder.bind(data , listener);
    }

    @Override
    public int getItemCount() {
        return categorieDataList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.search_card_title);
            categoryImage = itemView.findViewById(R.id.search_card_img);
        }

        public void bind(CategorieData data, OnCategoryClickListener listener) {
            categoryName.setText(data.getStrCategory());
           Glide.with(itemView.getContext()).load(data.getStrCategoryThumb()).into(categoryImage);

            itemView.setOnClickListener(v -> listener.onCategoryClick(data));

        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(CategorieData categorieData);
    }
}
