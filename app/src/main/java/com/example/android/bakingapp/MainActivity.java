package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.adapters.RecipeAdapter.RecipeAdapterOnClickHandler;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.APIClient;
import com.example.android.bakingapp.utilities.APIInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler {

    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.recipes_recyclerview) RecyclerView mRecipeRecyclerView;

    private List<Recipe> mRecipeList;
    private RecipeAdapter mRecipeAdapter;

    private static final String LIST_KEY = "recipeList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecipeAdapter = new RecipeAdapter(this, getApplicationContext());
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState != null) {
            String recipeJson = savedInstanceState.getString(LIST_KEY);
            mRecipeList = parseStringJson(recipeJson);
            mRecipeAdapter.setRecipeData(mRecipeList);
        }

        else {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecipeRecyclerView.setVisibility(View.INVISIBLE);
            fetchDataAndPopulateUI();
        }

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
            mRecipeRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();
        String json = gson.toJson(mRecipeList, type);

        Log.d("onSaveInstanceState", json);

        savedInstanceState.putString(LIST_KEY, json);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String recipeJson = savedInstanceState.getString(LIST_KEY);
        mRecipeList = parseStringJson(recipeJson);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        Gson gson = new Gson();
        Type type = new TypeToken<Recipe>() {}.getType();
        String json = gson.toJson(recipe, type);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.RECIPE_EXTRA, json);
        startActivity(intent);
    }

    private List<Recipe> parseStringJson(String json) {
        List<Recipe> recipeList = null;
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Recipe>>() {}.getType();
            recipeList = gson.fromJson(json, type);
        }
        return recipeList;
    }

    private void fetchDataAndPopulateUI() {
        mRecipeList = new ArrayList<>();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Recipe>> call = apiInterface.fetchRecipeList();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d("RETROFIT", response.code()+"");

                mRecipeList = response.body();
                mRecipeAdapter.setRecipeData(mRecipeList);

                showRecipeView();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                call.cancel();
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecipeRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showRecipeView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipeRecyclerView.setVisibility(View.VISIBLE);

    }


}
