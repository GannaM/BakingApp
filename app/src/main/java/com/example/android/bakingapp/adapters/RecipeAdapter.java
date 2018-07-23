package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private List<Recipe> mRecipeList;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Context mContext;


    public interface RecipeAdapterOnClickHandler {
        void onClickRecipe(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mRecipeImageView;
        public final TextView mRecipeNameTextView;
        public final TextView mRecipeServings;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeImageView = view.findViewById(R.id.recipe_image);
            mRecipeNameTextView = view.findViewById(R.id.recipe_name);
            mRecipeServings = view.findViewById(R.id.servings_value);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(adapterPosition);
            mClickHandler.onClickRecipe(recipe);
        }
    }


    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder viewHolder, int position) {

        Recipe recipe = mRecipeList.get(position);

        String recipeName = recipe.getName();
        viewHolder.mRecipeNameTextView.setText(recipeName);

        int servings = recipe.getServings();
        viewHolder.mRecipeServings.setText(Integer.toString(servings));

        String imageUrl = recipe.getImageUrl();
        if (!imageUrl.isEmpty()) {
            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(200, 200)
                    .onlyScaleDown()
                    .centerCrop()

                    .placeholder(R.drawable.ic_recipe)
                    .into(viewHolder.mRecipeImageView);
        }
        else {
            viewHolder.mRecipeImageView.setImageResource(R.drawable.ic_chef);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) { return 0; }
        return mRecipeList.size();
    }

    public void setRecipeData(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }
}
