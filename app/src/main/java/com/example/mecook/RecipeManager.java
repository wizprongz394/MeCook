package com.example.mecook;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeManager {
    private static final String PREFS_NAME = "recipes_prefs";
    private static final String RECIPES_KEY = "recipes_key";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public RecipeManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public ArrayList<Recipe> getRecipes() {
        String json = sharedPreferences.getString(RECIPES_KEY, null);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        ArrayList<Recipe> recipes = gson.fromJson(json, type);
        Log.d("RecipeManager", "Loaded Recipes: " + recipes);
        return (recipes != null) ? recipes : new ArrayList<>();
    }

    public void saveRecipes(ArrayList<Recipe> recipes) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(recipes);
        editor.putString(RECIPES_KEY, json);
        editor.apply();
        Log.d("RecipeManager", "Saved Recipes: " + recipes);
    }
}

