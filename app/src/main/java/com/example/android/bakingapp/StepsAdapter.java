package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private List<Step> mStepList;

    private final StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onStepClick(String videoUrl, String stepDescription);
    }

    public StepsAdapter(StepsAdapterOnClickHandler clickHandler, List<Step> stepList) {
        mClickHandler = clickHandler;
        mStepList = stepList;
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_tv) TextView mStepShortDescripton;

        public StepsAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step step = mStepList.get(adapterPosition);
            String videourl = step.getVideoUrl();
            String description = step.getLongDescription();
            mClickHandler.onStepClick(videourl, description);
        }
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        Step thisStep = mStepList.get(position);

        holder.mStepShortDescripton.setText(thisStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepList == null) { return 0; }
        return mStepList.size();
    }
}
