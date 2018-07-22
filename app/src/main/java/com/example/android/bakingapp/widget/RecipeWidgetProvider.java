package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.GsonUtils;

import java.io.Console;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.desired_recipe_key);
        String recipeJson = sharedPreferences.getString(key, "");

        if (!recipeJson.isEmpty()) {
            Recipe recipe = GsonUtils.jsonStringToRecipe(recipeJson);

            String recipeName = recipe.getName();
            views.setTextViewText(R.id.appwidget_recipe_name, recipeName);

            Intent listIntent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(R.id.appwidget_ingredient_list, listIntent);

            intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.RECIPE_EXTRA, recipeJson);
        }
        else {
            intent = new Intent(context, MainActivity.class);


        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipe_widget, pendingIntent);

        views.setEmptyView(R.id.appwidget_ingredient_list, R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.d("omg", "onUpdate: " + appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

