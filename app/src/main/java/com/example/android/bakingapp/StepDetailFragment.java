package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.stepView) ImageView mStepImageView;
    @BindView(R.id.step_description_tv) TextView mDescription;
    @BindView(R.id.button_next) Button mButtonNext;
    @BindView(R.id.button_previous) Button mButtonPrevious;
    private Unbinder unbinder;

    public static final String STEP_ID_LIST = "step_ids";
    public static final String LIST_INDEX = "list_index";

    private static final String TAG = "StepDetailFragment";

    private List<Step> mStepList;
    private Step mStep;
    private int mStepId;
    private int mStepIndex;
    //private int videoId;

    private boolean isTablet;
    private Context mContext;

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private boolean mPlayWhenReady;
    private long mPlaybackPosition;

    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        isTablet = getResources().getBoolean(R.bool.isTablet);


        if (savedInstanceState != null) {
            mStepId = savedInstanceState.getInt("stepId");
            Recipe recipe = DetailActivity.getRecipe();
            mStepList = recipe.getSteps();
            mStep = mStepList.get(mStepId);

            mPlaybackPosition = savedInstanceState.getLong("playerPosition");
            mPlayWhenReady = savedInstanceState.getBoolean("playerState");

        }
        else {
            mPlayWhenReady = true;
        }


        configure();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        deactivateMediaSession();

        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
        }

        if (Util.SDK_INT <= 23) {
            releasePlayer();
            deactivateMediaSession();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
            deactivateMediaSession();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            String videoUrl = mStep.getVideoUrl();
            if (!videoUrl.isEmpty()) {
                initializeMediaSession();
                initializePlayer(Uri.parse(videoUrl));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mExoPlayer != null) {
            long playerPosition = mExoPlayer.getCurrentPosition();
            boolean playerState = mExoPlayer.getPlayWhenReady();

            outState.putLong("playerPosition", playerPosition);
            outState.putBoolean("playerState", playerState);
        }

        outState.putInt("stepId", mStepId);

    }

    @OnClick(R.id.button_next)
    public void showNextRecipe() {
        if (mStepIndex < mStepList.size()-1) {
            mStepIndex++;
            mStep = mStepList.get(mStepIndex);

            releasePlayer();
            deactivateMediaSession();

            mPlaybackPosition = 0; // reset player position, in case it was saved earlier
            configure();
        }
        //mButtonPrevious.setEnabled(true);
    }

    @OnClick(R.id.button_previous)
    public void showPreviousRecipe() {
        if (mStepIndex > 0) {
            mStepIndex--;
            mStep = mStepList.get(mStepIndex);

            deactivateMediaSession();
            releasePlayer();

            mPlaybackPosition = 0; // reset player position, in case it was saved earlier
            configure();
        }

        //mButtonNext.setEnabled(true);
    }


    private void enableDisableButtons() {
        int firstStep = mStepList.get(0).getId();
        int lastStep = mStepList.get(mStepList.size()-1).getId();


        if (mStepId == firstStep) {
            mButtonPrevious.setEnabled(false);
            mButtonNext.setEnabled(true);
        }
        else if (mStepId == lastStep) {
            mButtonNext.setEnabled(false);
            mButtonPrevious.setEnabled(true);
        }
        else {
            mButtonNext.setEnabled(true);
            mButtonPrevious.setEnabled(true);
        }
    }

    public void configure() {
        mStepId = mStep.getId();

        if (!isTablet) {
            enableDisableButtons();
        }
        else {
            mButtonPrevious.setVisibility(View.GONE);
            mButtonNext.setVisibility(View.GONE);
        }


        String videoUrl = mStep.getVideoUrl();
        if (!videoUrl.isEmpty()) {
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));
            setPlayerView();
        }
        else {
            setStepImageView();
        }

        mDescription.setText(mStep.getLongDescription());
    }

    private void setStepImageView() {
        mPlayerView.setVisibility(View.INVISIBLE);

        String thumbnailUrl = mStep.getThumbnailUrl();
        if (!thumbnailUrl.isEmpty()) {
            Picasso.with(mContext)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.cooking)
                    .into(mStepImageView);
        }
        mStepImageView.setVisibility(View.VISIBLE);
    }

    private void setPlayerView() {
        mStepImageView.setVisibility(View.INVISIBLE);
        mPlayerView.setVisibility(View.VISIBLE);
    }


    public void setStepList(List<Step> stepList) {
        mStepList = stepList;
    }

    public void setStep(Step step) {
        mStep = step;
    }



    private void deactivateMediaSession() {
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    private void initializeMediaSession() {
        //Context context = getActivity().getApplicationContext();
        mMediaSession = new MediaSessionCompat(mContext, TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(mContext, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mPlaybackPosition);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }



    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        //showNotification(mStateBuilder.build());

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }



}
