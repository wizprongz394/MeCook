package com.example.mecook;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Recipe {
    private String name;
    private String ingredients;
    private String steps;
    private Date dateCreated;  // Date when the recipe was created
    private Date lastEdited;    // Date when the recipe was last edited

    // Constructor
    public Recipe(String name, String ingredients, String steps, Date dateCreated, Date lastEdited) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.dateCreated = dateCreated != null ? dateCreated : new Date();  // Default to current date if null
        this.lastEdited = lastEdited != null ? lastEdited : new Date();      // Default to current date if null
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    // New method to get formatted date created
    public String getFormattedDateCreated() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Adjust the format as needed
        return sdf.format(dateCreated);
    }

    // New method to get formatted last edited date
    public String getFormattedLastEdited() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Adjust the format as needed
        return sdf.format(lastEdited);
    }
}
