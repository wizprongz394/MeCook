package com.example.mecook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText recipeName, recipeIngredients, recipeSteps;
    private Button saveButton;
    private RecipeManager recipeManager;
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialize UI components
        recipeName = findViewById(R.id.recipeName);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeSteps = findViewById(R.id.recipeSteps);
        saveButton = findViewById(R.id.saveRecipeButton);

        // Initialize RecipeManager and retrieve the recipes list
        recipeManager = new RecipeManager(this);
        recipes = recipeManager.getRecipes();

        // Set a click listener for the Save button
        saveButton.setOnClickListener(v -> {
            String name = recipeName.getText().toString();
            String ingredients = recipeIngredients.getText().toString();
            String steps = recipeSteps.getText().toString();

            // Check if the recipe name is not empty
            if (!name.isEmpty()) {
                Date currentDate = new Date(); // Get the current date
                // Create a new recipe object with both dates
                Recipe newRecipe = new Recipe(name, ingredients, steps, currentDate, currentDate);

                // Add the new recipe to the list
                recipes.add(newRecipe);

                // Save the updated list back to storage
                recipeManager.saveRecipes(recipes);

                // Show a toast message indicating success
                Toast.makeText(this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();

                // Close AddRecipeActivity and return to MainActivity
                finish();
            } else {
                Toast.makeText(this, "Recipe name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
