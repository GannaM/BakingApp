package com.example.android.bakingapp;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.IngredientsAdapterListView;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utilities.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    @BindView(R.id.ingredients_list_recycler_view) RecyclerView ingredientRecyclerView;
    @BindView(R.id.steps_list_recycler_view) RecyclerView stepsRecyclerView;
    private Unbinder unbinder;

    private Recipe mRecipe;

    OnStepClickListener mCallback;

    @Override
    public void onStepClick(Step step) {
        mCallback.onStepSelected(step);
    }

    public interface  OnStepClickListener {
        void onStepSelected(Step step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public RecipeDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Context context = getActivity().getApplicationContext();
        mRecipe = DetailActivity.getRecipe();

        List<Ingredient> ingredientList = mRecipe.getIngredients();
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);

        List<Step> stepList = mRecipe.getSteps();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        StepsAdapter stepsAdapter = new StepsAdapter(this, stepList);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(stepsAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }


}
