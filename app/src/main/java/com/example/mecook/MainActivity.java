package com.example.mecook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recipeRecyclerView;
    private RecipeManager recipeManager;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> filteredRecipes; // For search functionality
    private SearchView searchView; // Reference to the SearchView
    private static final int EDIT_RECIPE_REQUEST_CODE = 1; // Request code for editing recipes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        searchView = findViewById(R.id.searchView); // Initialize the SearchView
        recipeManager = new RecipeManager(this);
        recipes = recipeManager.getRecipes();
        filteredRecipes = new ArrayList<>(recipes); // Start with all recipes

        // Set up the RecyclerView
        recipeAdapter = new RecipeAdapter(this, filteredRecipes,
                position -> {
                    Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                    intent.putExtra("recipe_index", position);
                    startActivity(intent);
                },
                position -> showDeleteConfirmationDialog(position)
        );

        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(recipeAdapter);

        // Set up the Add Recipe button
        Button addRecipeButton = findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivityForResult(intent, 0); // Start AddRecipeActivity
        });

        // Set up the SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Do nothing on submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText); // Filter recipes as the user types
                return true;
            }
        });
    }

    private void filterRecipes(String query) {
        filteredRecipes.clear(); // Clear the filtered list
        String lowerCaseQuery = query.toLowerCase().trim(); // Prepare the query

        if (lowerCaseQuery.isEmpty()) {
            filteredRecipes.addAll(recipes); // If query is empty, show all recipes
        } else {
            for (Recipe recipe : recipes) {
                // Check if the recipe name or ingredients contain the query
                if (recipe.getName().toLowerCase().contains(lowerCaseQuery) ||
                        recipe.getIngredients().toLowerCase().contains(lowerCaseQuery)) {
                    filteredRecipes.add(recipe); // Add matching recipes to filtered list
                }
            }
        }

        recipeAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
    }


    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", (dialog, which) -> deleteRecipe(position))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteRecipe(int position) {
        if (position >= 0 && position < filteredRecipes.size()) {
            recipes.remove(filteredRecipes.get(position)); // Remove from original list
            filteredRecipes.remove(position); // Remove from filtered list
            recipeManager.saveRecipes(recipes);
            recipeAdapter.notifyItemRemoved(position); // Refresh the adapter
            Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to delete recipe", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshRecipes(); // Refresh the recipe list
    }

    // Refresh the recipe list to ensure it is up to date
    private void refreshRecipes() {
        recipes.clear(); // Clear the current list to avoid duplicates
        recipes.addAll(recipeManager.getRecipes()); // Reload recipes
        filteredRecipes.clear();
        filteredRecipes.addAll(recipes); // Reset filtered list
        recipeAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
    }

    // Handle the result from EditRecipeActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_RECIPE_REQUEST_CODE) { // Check if the result is from EditRecipeActivity
            if (resultCode == RESULT_OK) {
                refreshRecipes(); // Refresh the recipe list
            }
        }
    }
}
