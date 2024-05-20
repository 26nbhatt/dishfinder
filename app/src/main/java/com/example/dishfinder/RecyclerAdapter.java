package com.example.dishfinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Food> dishes;
    private Context context;
    public RecyclerAdapter(ArrayList<Food> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView name, ingredients, recipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.cardName);
            ingredients = itemView.findViewById(R.id.cardIngredients);
            recipe = itemView.findViewById(R.id.cardRecipe);
        }
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false));
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food dish = dishes.get(position);
        holder.name.setText(dish.getName());
        holder.ingredients.setText(dish.getIngredients());
        holder.recipe.setText(dish.getRecipe());
    }
    public int getItemCount() {
        return dishes.size();
    }
}
