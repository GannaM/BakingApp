package com.example.android.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Ingredient;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        }
        else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ingredient_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Ingredient ingredientItem = mIngredients.get(position);
        double quantity = ingredientItem.getQuantity();
        String quantityString = Double.toString(quantity);

        holder.quantity.setText(quantityString);
        holder.measure.setText(ingredientItem.getMeasure());
        holder.ingredient.setText(ingredientItem.getIngredientName());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.quantity_tv) TextView quantity;
        @BindView(R.id.measure_tv) TextView measure;
        @BindView(R.id.ingredient_tv) TextView ingredient;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
