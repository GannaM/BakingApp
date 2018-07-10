package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.RecipeAdapter.RecipeAdapterOnClickHandler;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.JsonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Recipe> recipeList = JsonUtils.parseRecipeJson(this);

        RecyclerView recipeRecyclerView = findViewById(R.id.recipes_recyclerview);
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, getApplicationContext());
        recipeRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setRecipeData(recipeList);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            GridLayoutManager gridLayoutManager;

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                gridLayoutManager = new GridLayoutManager(this, 2);
            }
            else {
                gridLayoutManager = new GridLayoutManager(this, 3);
            }
            recipeRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recipeRecyclerView.setLayoutManager(linearLayoutManager);
        }


        //RecipeAdapterOnClickHandler clickHandler = (RecipeAdapterOnClickHandler) this;









    }

    @Override
    public void onClickRecipe(Recipe recipe) {

    }
}
