package com.example.mecook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Recipe> recipes;
    private final OnRecipeClickListener onRecipeClickListener;
    private final OnRecipeLongClickListener onRecipeLongClickListener;

    // Constructor
    public RecipeAdapter(Context context, ArrayList<Recipe> recipes,
                         OnRecipeClickListener onRecipeClickListener,
                         OnRecipeLongClickListener onRecipeLongClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.onRecipeClickListener = onRecipeClickListener;
        this.onRecipeLongClickListener = onRecipeLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeNameTextView.setText(recipe.getName());
        holder.recipeDateTextView.setText("Created: " + recipe.getFormattedDateCreated()); // Display creation date
        holder.lastEditedTextView.setText("Modified: " + recipe.getFormattedLastEdited()); // Display last edited date

        // Set click and long-click listeners
        holder.itemView.setOnClickListener(v -> onRecipeClickListener.onRecipeClick(position));
        holder.itemView.setOnLongClickListener(v -> {
            onRecipeLongClickListener.onRecipeLongClick(position);
            return true; // Indicate long press was handled
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTextView;
        TextView recipeDateTextView;
        TextView lastEditedTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews
            recipeNameTextView = itemView.findViewById(R.id.recipeName);
            recipeDateTextView = itemView.findViewById(R.id.recipeDateTextView);
            lastEditedTextView = itemView.findViewById(R.id.lastEdited);
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position);
    }

    public interface OnRecipeLongClickListener {
        void onRecipeLongClick(int position);
    }
}
