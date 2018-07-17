package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_EXTRA = "step_extra";
    private Step mStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_activity);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }

        String json = intent.getStringExtra(STEP_EXTRA);
        if (json != null) {
            if (!json.isEmpty()) {
                Gson gson = new Gson();
                Type type = new TypeToken<Step>() {}.getType();
                mStep = gson.fromJson(json, type);
            }
        }



        Recipe recipe = DetailActivity.getRecipe();
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepDetailFragment fragment = (StepDetailFragment) fragmentManager.findFragmentById(R.id.step_detail_fragment);
        fragment.setStep(mStep);
        fragment.setStepList(recipe.getSteps());

        fragment.configure();

//        StepDetailFragment stepDetailFragment = new StepDetailFragment();
//

//
//        stepDetailFragment.setStepList(recipe.getSteps());
//        stepDetailFragment.setStep(mStep);
//
//        setContentView(R.layout.step_detail_activity);
//
//        fragmentManager.beginTransaction()
//                .add(stepDetailFragment, "stepDetails")
//                .commit();



    }
}
