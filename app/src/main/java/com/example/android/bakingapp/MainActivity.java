package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);

        //RecipeAdapterOnClickHandler clickHandler = (RecipeAdapterOnClickHandler) this;
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, getApplicationContext());
        recipeRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setRecipeData(recipeList);








    }

    @Override
    public void onClickRecipe(Recipe recipe) {

    }
}
