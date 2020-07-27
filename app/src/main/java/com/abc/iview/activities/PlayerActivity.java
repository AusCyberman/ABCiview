
package com.abc.iview.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.abc.iview.content.Content;
import com.abc.iview.R;

import com.abc.iview.WatchData;
import com.abc.iview.content.TVShow;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class PlayerActivity extends AppCompatActivity {
    private boolean tvshowdone = false;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Content.Video video;
    private boolean isTrailer = false;
    private TVShow tvshow;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    boolean alreadySetPosition = false;
    private Uri uri;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerView = findViewById(R.id.videoView);

        intent = getIntent();

            System.out.println("not null");
            if (intent.getExtras().get("tvextras")!=null) {
                tvshow = MainActivity.tvshows.get((Integer) ((Bundle) intent.getExtras().get("tvextras")).get("TVShowPosition"));
            }else{
                    tvshow = MainActivity.tvshows.get((Integer) intent.getExtras().get("TVShowPosition"));
                    System.out.println(tvshow.getId());
            }
            if(((Boolean)intent.getExtras().get("isTrailer"))){
                video=tvshow.getTrailer();
                System.out.println("Is Trailer");
                isTrailer=true;
            }else{

                video = ((TVShow)tvshow).getEpisode((Integer) intent.getExtras().get("episode"));
                System.out.println("Uri is"+video.getUri());
            }



        initializePlayer();





    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {

            releasePlayer();
            initializePlayer();


        }

    }
    public void exit(View v ){



        if(getIntent().getExtras().get("recent")!=null){

            intent= new Intent(this,MainActivity.class);
            intent.putExtra(ChannelActivity.returnfrom,R.id.navigation_home);
        }else{
            intent= new Intent(this,TVShowActivity.class);
            intent.putExtras((Bundle) getIntent().getExtras().get("tvextras"));
        }

        startActivity(intent);
        finish();

    }


    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23)||player==null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            WatchData.WatchSession session = new WatchData.WatchSession(((TVShow)video.getTVShow()).getEpisodes().indexOf(video), (TVShow) video.getTVShow());
            session.setTotalLength(player.getDuration());
            session.commit(player.getCurrentPosition());
            System.out.println(player.getCurrentPosition());
            releasePlayer();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if(!isTrailer) {
                System.out.println(player.getCurrentPosition());
                System.out.println(player.getDuration());
                float total = (player.getCurrentPosition() * 100) / player.getDuration();
                System.out.println(total);
                System.out.println("% through is " + (player.getCurrentPosition() / player.getDuration()) * 100);
                if(tvshowdone){
                WatchData.WatchSession session = new WatchData.WatchSession(((Content.Episode) video).getId(), tvshow);
                session.finish();

            } else if (player.getCurrentPosition()>10000){

                    WatchData.WatchSession session = new WatchData.WatchSession(((TVShow)video.getTVShow()).getEpisodes().indexOf(video), (TVShow) video.getTVShow());
                    session.setTotalLength(player.getDuration());
                    session.commit(player.getCurrentPosition());

            }
            }
            System.out.println(player.getCurrentPosition());
            releasePlayer();


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit(findViewById(R.id.tv_show_back));
    }
    public static Uri turi;
    Intent intent;
    private void initializePlayer() {

        //System.out.println(uri.toString());
        //System.out.println(video.getUri());
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            playerView.setUseArtwork(true);
            player.seekTo(currentWindow, playbackPosition);
            player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState==Player.STATE_ENDED) {
                        System.out.println("Next ep");
                        if(tvshow.CONTENT_TYPE=="TVSHOW"&isTrailer==false) {
                            System.out.println("Next ep");
                            if (!(((TVShow)tvshow).getEpisodes().size()==((Content.Episode)video).getId())){
                                System.out.println("Next ep");
                                releasePlayer();
                                video=((TVShow) tvshow).getEpisode(((Content.Episode)video).getId()+1);
                                initializePlayer();

                            }else{

                                exit(findViewById(R.id.imageView));
                                tvshowdone=true;
                            }
                        }

                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });

        }
        MediaSource mediaSource;



        mediaSource=buildMediaSource(turi);



        player.prepare(mediaSource, true, false);

        if(isTrailer==false){
            System.out.println("Not trailer");
            WatchData.WatchSession session = WatchData.getMostRecentSession(((TVShow.Episode)video).getId(),video.getTVShow().getId());
            if(session!=null&&!session.didFinish()) {

                player.seekTo(currentWindow, session.getProgress());
            }
        }


    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
    }


}