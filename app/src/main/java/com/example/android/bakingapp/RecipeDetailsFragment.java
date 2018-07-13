package com.example.android.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{

    @BindView(R.id.ingredients_list_view) ListView ingredientsListView;
    @BindView(R.id.steps_list_recycler_view) RecyclerView stepsRecyclerView;
    private Unbinder unbinder;

    //OnStepClickListener mCallback;

    @Override
    public void onStepClick(String videoUrl, String stepDescription) {

    }

    public interface  OnStepClickListener {
        void onStepSelected(int position);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            mCallback = (OnStepClickListener) context;
//        }
//        catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnStepClickListener");
//        }
//    }

    public RecipeDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        List<Ingredient> ingredientList = DetailActivity.getRecipe().getIngredients();
        Context context = getActivity().getApplicationContext();
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(context, ingredientList);
        ingredientsListView.setAdapter(ingredientsAdapter);

        List<Step> stepList = DetailActivity.getRecipe().getSteps();
        StepsAdapter stepsAdapter = new StepsAdapter(this, stepList);
        stepsRecyclerView.setAdapter(stepsAdapter);

        return rootView;
    }
}
