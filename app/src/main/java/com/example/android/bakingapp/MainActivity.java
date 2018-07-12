package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.RecipeAdapter.RecipeAdapterOnClickHandler;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.APIClient;
import com.example.android.bakingapp.utilities.APIInterface;
import com.example.android.bakingapp.utilities.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler {

    private List<Recipe> mRecipeList;

    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final List<Recipe> recipeList = JsonUtils.parseRecipeJson(this);



        RecyclerView recipeRecyclerView = findViewById(R.id.recipes_recyclerview);
        mRecipeAdapter = new RecipeAdapter(this, getApplicationContext());
        recipeRecyclerView.setAdapter(mRecipeAdapter);


        if (savedInstanceState != null) {
            String recipeJson = savedInstanceState.getString("recipeList");
            if (!recipeJson.isEmpty()) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Recipe>>() {}.getType();
                mRecipeList = gson.fromJson(recipeJson, type);
                mRecipeAdapter.setRecipeData(mRecipeList);
            }
        }

        else {
            mRecipeList = new ArrayList<>();

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<Recipe>> call = apiInterface.fetchRecipeList();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    Log.d("RETROFIT", response.code()+"");

                    mRecipeList = response.body();
                    mRecipeAdapter.setRecipeData(mRecipeList);

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    call.cancel();
                }
            });

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
            recipeRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recipeRecyclerView.setLayoutManager(linearLayoutManager);
        }


        //RecipeAdapterOnClickHandler clickHandler = (RecipeAdapterOnClickHandler) this;




    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>() {}.getType();
        String json = gson.toJson(mRecipeList, type);

        Log.d("TAonSaveInstanceState", json);

        savedInstanceState.putString("recipeList", json);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Gson gson = new Gson();
        String recipeJson = savedInstanceState.getString("recipeList");
        if (!recipeJson.isEmpty()) {
            Type type = new TypeToken<List<Recipe>>() {}.getType();
            mRecipeList = gson.fromJson(recipeJson, type);
            Log.d("OnRestoreInstanceState:", mRecipeList.size() + "");
        }

        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onClickRecipe(Recipe recipe) {

    }
}
