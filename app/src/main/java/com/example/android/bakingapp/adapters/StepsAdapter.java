package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private List<Step> mStepList;

    private final StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onStepClick(Step step);
    }

    public StepsAdapter(StepsAdapterOnClickHandler clickHandler, List<Step> stepList) {
        mClickHandler = clickHandler;
        mStepList = stepList;
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //@BindView(R.id.step_tv) TextView mStepShortDescription;
        public final TextView mStepShortDescription;

        public StepsAdapterViewHolder(View view) {
            super(view);
            //ButterKnife.bind(view);
            mStepShortDescription = view.findViewById(R.id.step_tv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step step = mStepList.get(adapterPosition);
            mClickHandler.onStepClick(step);
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

        holder.mStepShortDescription.setText(thisStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepList == null) { return 0; }
        return mStepList.size();
    }
}
