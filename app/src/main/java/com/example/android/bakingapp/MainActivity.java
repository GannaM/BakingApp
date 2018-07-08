package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Recipe> recipeList = JsonUtils.parseRecipeJson(this);

        if (recipeList != null) {
            int length = recipeList.size();
            Log.d("JSON", "Found recipes: " + length);

            for (int i = 0; i < length; i++) {
                Recipe recipe = recipeList.get(i);
                Log.d("RECIPE " + i, recipe.getName());
            }
        }




    }
}
