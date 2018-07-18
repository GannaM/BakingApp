package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    public static final String RECIPE_EXTRA = "recipe_extra";

    private static Recipe mRecipe;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if (intent == null) {
            finish();
            Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }

        String json = intent.getStringExtra(RECIPE_EXTRA);
        if (json != null) {
            if (!json.isEmpty()) {
                Gson gson = new Gson();
                Type type = new TypeToken<Recipe>() {}.getType();
                mRecipe = gson.fromJson(json, type);
            }
        }

        setContentView(R.layout.detail_activity);

        setTitle(mRecipe.getName());
        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet & savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setStepList(mRecipe.getSteps());
            fragment.setStep(mRecipe.getSteps().get(0));
            //fragment.configure();

            fragmentManager.beginTransaction()
                    .add(R.id.step_fragment, fragment)
                    .commit();
        }

    }

    public static Recipe getRecipe() {
        return mRecipe;
    }


    @Override
    public void onStepSelected(Step step) {

        if (isTablet) {
            StepDetailFragment newFragment = new StepDetailFragment();
            newFragment.setStepList(mRecipe.getSteps());
            newFragment.setStep(step);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_fragment, newFragment)
                    .commit();

        }
        else {
            Gson gson = new Gson();
            Type type = new TypeToken<Step>() {}.getType();
            String json = gson.toJson(step, type);

            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.STEP_EXTRA, json);
            startActivity(intent);
        }




    }
}
