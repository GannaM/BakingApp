package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.GsonUtils;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}


class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> mIngredientList;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
        mIngredientList = getIngredientListFromSharedPreferences();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mIngredientList = getIngredientListFromSharedPreferences();
    }

    private List<Ingredient> getIngredientListFromSharedPreferences() {
        List<Ingredient> ingredientList = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String key = mContext.getString(R.string.desired_recipe_key);
        String recipeJson = sharedPreferences.getString(key, "");

        if (!recipeJson.isEmpty()) {
            Recipe recipe = GsonUtils.jsonStringToRecipe(recipeJson);
            ingredientList = recipe.getIngredients();
        }
        return ingredientList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientList == null) return 0;
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);

        Ingredient ingredient = mIngredientList.get(position);
        String quantity = Double.toString(ingredient.getQuantity());
        views.setTextViewText(R.id.quantity_tv, quantity);
        views.setTextViewText(R.id.measure_tv, ingredient.getMeasure());
        views.setTextViewText(R.id.ingredient_tv, ingredient.getIngredientName());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
