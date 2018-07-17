package com.example.android.bakingapp;

import android.support.v4.app.Fragment;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaButtonReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_description_tv) TextView mDescription;
    @BindView(R.id.button_next) Button mButtonNext;
    @BindView(R.id.button_previous) Button mButtonPrevious;
    private Unbinder unbinder;

    public static final String STEP_ID_LIST = "step_ids";
    public static final String LIST_INDEX = "list_index";

    private static final String TAG = "StepDetailFragment";

    private List<Step> mStepList;
    private Step mStep;

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;

    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        //TextView mDescription = rootView.findViewById(R.id.step_description_tv);


//        if (mStep.getId() == 0) {
//            mButtonPrevious.setEnabled(false);
//        }
//        if (mStep.getId() == mStepList.size()-1) {
//            mButtonNext.setEnabled(false);
//        }

//        initializeMediaSession();
//        initializePlayer(Uri.parse(mStep.getVideoUrl()));


//        mDescription.setText(mStep.getLongDescription());

        return rootView;
    }

    public void configure() {
        if (mStep.getId() == 0) {
            mButtonPrevious.setEnabled(false);
        }
        if (mStep.getId() == mStepList.size()-1) {
            mButtonNext.setEnabled(false);
        }

        initializeMediaSession();
        initializePlayer(Uri.parse(mStep.getVideoUrl()));


        mDescription.setText(mStep.getLongDescription());
    }


    public void setStepList(List<Step> stepList) {
        mStepList = stepList;
    }

    public void setStep(Step step) {
        mStep = step;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializeMediaSession() {
        Context context = getActivity().getApplicationContext();
        mMediaSession = new MediaSessionCompat(context, TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);


        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        Context context = getActivity().getApplicationContext();

        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
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

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
