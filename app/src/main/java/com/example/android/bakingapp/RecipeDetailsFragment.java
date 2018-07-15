package com.example.android.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.IngredientsAdapterListView;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{

    //@BindView(R.id.ingredients_list_view) ListView ingredientsListView;
    @BindView(R.id.ingredients_list_recycler_view) RecyclerView ingredientRecyclerView;
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

        Context context = getActivity().getApplicationContext();

        List<Ingredient> ingredientList = DetailActivity.getRecipe().getIngredients();
        //IngredientsAdapterListView ingredientsAdapter = new IngredientsAdapterListView(ingredientList);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);
        //ingredientsListView.setAdapter(ingredientsAdapter);
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);

//        ingredientsListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//        setListViewHeightBasedOnChildren(ingredientsListView);

        List<Step> stepList = DetailActivity.getRecipe().getSteps();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        StepsAdapter stepsAdapter = new StepsAdapter(this, stepList);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(stepsAdapter);

        return rootView;
    }

}
