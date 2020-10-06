package turkusoweaplikacje.dmm.zaspiewane;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, ExoPlayer.EventListener{
protected SimpleExoPlayer exoPlayer;
private SimpleExoPlayerView playerView;
private static MediaSessionCompat mediaSession;
private PlaybackStateCompat.Builder stateBuilder;
protected TextView roomTitle;
protected TextView episodeTitle;
private int[] playerList;
protected int currentEpisode;
private NotificationManager notificationManager;
private int NOTIFICATION_ID = 234;
private boolean audioStarted = true;


@Override
protected void onResume(){
        super.onResume();
        if(audioStarted){
        playAudio();
        }
        }


@Override
protected void onDestroy() {
        super.onDestroy();
        pauseAudio();
        audioStarted = false;
        releasePlayer();
        deactivateSession();
        }

public void setPlayerView(SimpleExoPlayerView mPlayerView) {
        this.playerView = mPlayerView;
        }


public void setRoomTitle(TextView roomTitle, String title) {
        this.roomTitle = roomTitle;
        this.roomTitle.setText(title);
        }

public void setEpisodeTitle(TextView episodeTitle, String title) {
        this.episodeTitle = episodeTitle;
//        this.episodeTitle.setText(title);
        }

public void setPlayerList(int[] list, int episode){
        this.playerList = list;
        this.currentEpisode = episode;
        }

public void initializeMediaSession() {

        playerView.setPlayer(exoPlayer);
        mediaSession = new MediaSessionCompat(this, this.getClass().getSimpleName());
        mediaSession.setFlags(
        MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
        .setActions(
        PlaybackStateCompat.ACTION_PLAY |
        PlaybackStateCompat.ACTION_PAUSE |
        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
        PlaybackStateCompat.ACTION_PLAY_PAUSE |
        PlaybackStateCompat.ACTION_SKIP_TO_NEXT);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new PlayerActivity.MySessionCallback());
        mediaSession.setActive(true);

        }

public void initializePlayer() {
        if (exoPlayer == null) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        playerView.setPlayer(exoPlayer);

        exoPlayer.addListener(this);

        }


    String userAgent = Util.getUserAgent(this, String.valueOf(R.string.app_name));

    //    MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
    //    this, userAgent), new DefaultExtractorsFactory(), null, null);
    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"));

    MediaSource firstSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(RawResourceDataSource.buildRawResourceUri(playerList[currentEpisode]));

    exoPlayer.prepare(firstSource);
        exoPlayer.setPlayWhenReady(false);
        //  }
        }

private void showNotification(PlaybackStateCompat state) {
        int icon;
        String play_pause;
        if(state.getState() == PlaybackStateCompat.STATE_PLAYING){
        icon = R.drawable.exo_controls_pause;
        play_pause = getString(R.string.audio_pause);
        } else {
        icon = R.drawable.exo_controls_play;
        play_pause = getString(R.string.audio_play);
        }

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String CHANNEL_ID = getString(R.string.app_name);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        CharSequence name = getString(R.string.app_name);
        String Description = getString(R.string.main_subtitle);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        mChannel.setDescription(Description);
        mChannel.setShowBadge(false);
        mChannel.setSound(null,null);
        notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.mipmap.headphones)
        .setContentTitle(getResources().getString(R.string.app_name))
        .setContentText(getResources().getString(R.string.main_subtitle));


        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
        icon, play_pause,
        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action previousAction = new NotificationCompat
        .Action(R.drawable.exo_controls_previous, getString(R.string.audio_previous),
        MediaButtonReceiver.buildMediaButtonPendingIntent
        (this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        NotificationCompat.Action nextAction = new NotificationCompat
        .Action(R.drawable.exo_controls_next, getString(R.string.audio_next),
        MediaButtonReceiver.buildMediaButtonPendingIntent
        (this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
        (this, 0, new Intent(this, PlayerActivity.class), 0);

        builder
                //.setContentTitle(roomTitle.getText())
       // .setContentText(episodeTitle.getText())
        .setContentIntent(contentPendingIntent)
        .setSmallIcon(R.mipmap.headphones)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .addAction(previousAction)
        .addAction(playPauseAction)
        .addAction(nextAction)
        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.getSessionToken())
        .setShowActionsInCompactView(0,1,2));


        notificationManager.notify(NOTIFICATION_ID, builder.build());
        }


public void pauseAudio(){
        exoPlayer.setPlayWhenReady(false);
        audioStarted = false;
        }

public void playAudio(){
        exoPlayer.setPlayWhenReady(true);
        audioStarted = true;
        }

public void deactivateSession(){
        mediaSession.setActive(false);
        }

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
        if (playbackState == ExoPlayer.STATE_ENDED){
        if(currentEpisode<(playerList.length-1)) {
        currentEpisode++;
        initializePlayer();
        Log.d("audio", "state changed");
        }
        }
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
        stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
        exoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
        stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
        exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
        showNotification(stateBuilder.build());
        }

@Override
public void onRepeatModeChanged(int repeatMode) {

        }

@Override
public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

@Override
public void onPlayerError(ExoPlaybackException error) {
        switch (error.type) {
        case ExoPlaybackException.TYPE_SOURCE:
        Toast.makeText(this,"Wrong type source",Toast.LENGTH_LONG).show();
        Log.e("hmm", "TYPE_SOURCE: " + error.getSourceException().getMessage());
        break;

        case ExoPlaybackException.TYPE_RENDERER:
        Log.e("hmm", "TYPE_RENDERER: " + error.getRendererException().getMessage());
        break;

        case ExoPlaybackException.TYPE_UNEXPECTED:
        Log.e("hmm", "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
        break;
        }
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

@Override
public void onClick(View view) {

        }

private class MySessionCallback extends MediaSessionCompat.Callback {
    @Override
    public void onPlay() {
        playAudio();
    }

    @Override
    public void onPause() {
        pauseAudio();
    }

    @Override
    public void onSkipToPrevious() {
        if(currentEpisode>0) {
            currentEpisode--;
            initializePlayer();
        }
    }

    @Override
    public void onSkipToNext(){
        if(currentEpisode<(playerList.length-1)) {
            currentEpisode++;
            initializePlayer();
        }
    }
}

    public void releasePlayer() {
        notificationManager.cancelAll();
        exoPlayer.stop();
        exoPlayer.release();
        //   exoPlayer = null;
    }

public static class MediaReceiver extends BroadcastReceiver {
    public MediaReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
    }
}
}
