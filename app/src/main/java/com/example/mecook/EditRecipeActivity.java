package com.example.mecook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

public class EditRecipeActivity extends AppCompatActivity {
    private EditText recipeNameEditText, recipeIngredientsEditText, recipeStepsEditText;
    private Button saveButton;
    private RecipeManager recipeManager;
    private ArrayList<Recipe> recipes;
    private int recipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        // Initialize UI components
        recipeNameEditText = findViewById(R.id.recipeName);
        recipeIngredientsEditText = findViewById(R.id.recipeIngredients);
        recipeStepsEditText = findViewById(R.id.recipeSteps);
        saveButton = findViewById(R.id.saveRecipeButton);

        // Initialize RecipeManager and get recipes
        recipeManager = new RecipeManager(this);
        recipes = recipeManager.getRecipes();

        // Get the recipe index passed from the intent
        recipeIndex = getIntent().getIntExtra("recipe_index", -1);

        // Validate index and load recipe details
        if (isRecipeIndexValid()) {
            loadRecipeDetails();
        } else {
            Toast.makeText(this, "Invalid recipe", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if the index is invalid
        }

        // Set a click listener to save updated recipe
        saveButton.setOnClickListener(v -> saveUpdatedRecipe());
    }

    // Check if the index is valid
    private boolean isRecipeIndexValid() {
        return recipeIndex >= 0 && recipeIndex < recipes.size();
    }

    // Load the recipe details into EditText fields
    private void loadRecipeDetails() {
        Recipe recipe = recipes.get(recipeIndex);
        recipeNameEditText.setText(recipe.getName());
        recipeIngredientsEditText.setText(recipe.getIngredients());
        recipeStepsEditText.setText(recipe.getSteps());
    }

    // Save the updated recipe and finish the activity
    private void saveUpdatedRecipe() {
        if (!validateInputs()) return;

        // Update recipe details
        Recipe updatedRecipe = recipes.get(recipeIndex);
        updatedRecipe.setName(recipeNameEditText.getText().toString().trim());
        updatedRecipe.setIngredients(recipeIngredientsEditText.getText().toString().trim());
        updatedRecipe.setSteps(recipeStepsEditText.getText().toString().trim());
        updatedRecipe.setLastEdited(new Date());

        // Save the updated list
        recipeManager.saveRecipes(recipes);

        // Create an intent to go back to MainActivity
        Intent intent = new Intent(EditRecipeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the stack and start new MainActivity
        startActivity(intent); // Start MainActivity
        finish(); // Close EditRecipeActivity
    }


    // Validate inputs to ensure they are not empty
    private boolean validateInputs() {
        if (recipeNameEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Recipe name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
