package com.example.mecook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeNameTextView, recipeIngredientsTextView, recipeStepsTextView;
    private Button editRecipeButton;
    private RecipeManager recipeManager;
    private int recipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize UI components
        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeIngredientsTextView = findViewById(R.id.recipeIngredientsTextView);
        recipeStepsTextView = findViewById(R.id.recipeStepsTextView);
        editRecipeButton = findViewById(R.id.editButton);

        recipeManager = new RecipeManager(this);
        recipeIndex = getIntent().getIntExtra("recipe_index", -1);

        if (isRecipeIndexValid(recipeIndex)) {
            loadRecipeDetails(recipeIndex);
        } else {
            // Handle invalid index
            finish();
        }

        editRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeDetailActivity.this, EditRecipeActivity.class);
            intent.putExtra("recipe_index", recipeIndex);
            startActivity(intent);
        });
    }

    private boolean isRecipeIndexValid(int index) {
        return index >= 0 && index < recipeManager.getRecipes().size();
    }

    private void loadRecipeDetails(int index) {
        Recipe recipe = recipeManager.getRecipes().get(index);
        recipeNameTextView.setText(recipe.getName());
        recipeIngredientsTextView.setText(recipe.getIngredients());
        recipeStepsTextView.setText(recipe.getSteps());
        // Removed dateTextView loading since it should be on home screen cards
    }
}
