package com.example.android.bakingapp.utilities;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final public class JsonUtils {

    public static List<Recipe> parseRecipeJson(Context context) {

        String json = readJsonFile(context);
        List<Recipe> recipeList = new ArrayList<>();

        try {
            JSONArray recipeData = new JSONArray(json);

            if (recipeData != null) {
                for (int i = 0; i < recipeData.length(); i++) {
                    JSONObject recipe = recipeData.getJSONObject(i);

                    int id = recipe.getInt("id");
                    String name = recipe.getString("name");
                    int servings = recipe.getInt("servings");
                    String url = recipe.getString("image");

                    JSONArray ingredientsJson = recipe.getJSONArray("ingredients");
                    List<Ingredient> ingredients = parseIngredients(ingredientsJson);

                    JSONArray stepsJson = recipe.getJSONArray("steps");
                    List<Step> steps = parseSteps(stepsJson);

                    Recipe newRecipe = new Recipe(id, name, ingredients, steps, servings, url);
                    recipeList.add(newRecipe);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return  recipeList;
    }

    // Reads json data from Assets
    private static String readJsonFile(Context context) {
        String json = null;

        try {
            InputStream inputStream = context.getAssets().open("baking.json");
            int sizeOfJsonFile = inputStream.available();
            byte[] bytes = new byte[sizeOfJsonFile];
            inputStream.read(bytes);
            inputStream.close();

            json = new String(bytes, "UTF-8");
            //Log.d("JSON:", json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static List<Ingredient> parseIngredients(JSONArray ingredientList) {
        List<Ingredient> ingredients = new ArrayList<>();

        if (ingredientList != null) {

            try {
                for (int i = 0; i < ingredientList.length(); i++) {
                    JSONObject ingredient = ingredientList.getJSONObject(i);
                    double quantity = ingredient.getDouble("quantity");
                    String measure = ingredient.getString("measure");
                    String ingredientName = ingredient.getString("ingredient");

                    Ingredient newIngredient = new Ingredient(quantity, measure, ingredientName);
                    ingredients.add(newIngredient);
                }

            } catch (JSONException e) {
                e.printStackTrace();;
            }
        }
        return ingredients;
    }


    private static List<Step> parseSteps(JSONArray stepList) {
        List<Step> steps = new ArrayList<>();

        if (stepList != null) {
            try {
                for (int i = 0; i < stepList.length(); i++) {
                    JSONObject step = stepList.getJSONObject(i);
                    int id = step.getInt("id");
                    String shortDescription = step.getString("shortDescription");
                    String longDescription = step.getString("description");
                    String videoUrl = step.getString("videoURL");
                    String thumbnailUrl = step.getString("thumbnailURL");

                    Step newStep = new Step(id, shortDescription, longDescription, videoUrl, thumbnailUrl);
                    steps.add(newStep);
                }

            } catch (JSONException e) {
                e.printStackTrace();;
            }
        }
        return steps;
    }


}
