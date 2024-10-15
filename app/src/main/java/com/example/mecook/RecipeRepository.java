package com.example.mecook;

import java.util.ArrayList;

public class RecipeRepository {
    private static RecipeRepository instance;  // Singleton instance
    private ArrayList<Recipe> recipes;         // Store recipes

    // Private constructor to prevent external instantiation
    private RecipeRepository() {
        recipes = new ArrayList<>();
    }

    // Get the singleton instance
    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    // Get all recipes
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    // Add a new recipe
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    // Update an existing recipe
    public void updateRecipe(int index, Recipe updatedRecipe) {
        recipes.set(index, updatedRecipe);
    }
}

