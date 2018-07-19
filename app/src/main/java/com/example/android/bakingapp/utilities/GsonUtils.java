package com.example.android.bakingapp.utilities;

import android.util.Log;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

    // OBJECT TO STRING

    public static String objectToJsonString(Object object) {
        Gson gson = new Gson();
        Type type = null;

        if (object instanceof Recipe) {
            type = new TypeToken<Recipe>() {}.getType();
        }

        if (object instanceof Ingredient) {
            type = new TypeToken<Ingredient>() {}.getType();
        }

        if (object instanceof Step) {
            type = new TypeToken<Step>() {}.getType();
        }

        if (type == null) {
            Log.d("objectToJsonString", "Object of a wrong type: ");
            return null;
        }
        return gson.toJson(object, type);
    }


    // STRING TO OBJECT

    public static Recipe jsonStringToRecipe(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Recipe>() {}.getType();

        return gson.fromJson(json, type);
    }

    public static Ingredient jsonStringToIngredient(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Ingredient>() {}.getType();

        return gson.fromJson(json, type);
    }

    public static Step jsonStringToStep(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Step>() {}.getType();

        return gson.fromJson(json, type);
    }



    // STRING TO LIST

    public static List<Recipe> jsonStringToRecipeList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();

        return gson.fromJson(json, type);
    }

    public static List<Ingredient> jsonStringToIngredientList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();

        return gson.fromJson(json, type);
    }

    public static List<Step> jsonStringToStepList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {}.getType();

        return gson.fromJson(json, type);
    }


    // LIST TO STRING

    public static String recipeListToJsonString(List<Recipe> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();
        String json = gson.toJson(list, type);

        return json;
    }




}
