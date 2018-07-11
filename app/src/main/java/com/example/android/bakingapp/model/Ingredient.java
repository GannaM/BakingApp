package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredientName;


    // CONSTRUCTORS

    public Ingredient() {}

    public Ingredient(double quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = name;
    }


    // GETTERS

    public double getQuantity() { return quantity; }
    public String getMeasure() { return measure; }
    public String getIngredientName() { return ingredientName; }


    // SETTERS

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredientName(String name) {
        this.ingredientName = name;
    }
}
