package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List <Ingredient> ingredients;

    @SerializedName("steps")
    private List <Step> steps;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String imageUrl;


    // CONSTRUCTORS

    public Recipe() {}

    public Recipe(int id, String name, List <Ingredient> ingredients, List <Step> steps, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }


    // GETTERS
    public int getId() { return id; }
    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<Step> getSteps() { return steps; }
    public int getServings() { return servings; }
    public String getImageUrl() { return imageUrl; }


    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
