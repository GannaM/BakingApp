package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private List<Ingredient> mIngredientList;

    public IngredientsAdapter(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.quantity_tv) TextView quantity;
//        @BindView(R.id.measure_tv) TextView measure;
//        @BindView(R.id.ingredient_tv) TextView ingredient;
        public final TextView quantity;
        public final TextView measure;
        public final TextView ingredient;


        public IngredientsAdapterViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            quantity = view.findViewById(R.id.quantity_tv);
            measure = view.findViewById(R.id.measure_tv);
            ingredient = view.findViewById(R.id.ingredient_tv);
        }
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredientItem = mIngredientList.get(position);

        double quantity = ingredientItem.getQuantity();
        String quantityString = Double.toString(quantity);

        holder.quantity.setText(quantityString);
        holder.measure.setText(ingredientItem.getMeasure());
        holder.ingredient.setText(ingredientItem.getIngredientName());
    }

    @Override
    public int getItemCount() {
        if (mIngredientList == null) { return 0; }
        return mIngredientList.size();
    }
}
