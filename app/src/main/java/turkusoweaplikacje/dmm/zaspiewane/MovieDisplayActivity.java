package turkusoweaplikacje.dmm.zaspiewane;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.io.InputStream;

import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.ARRAY_SIZE;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_IMAGE_URL;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_NUMBER;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_PLAY_URL;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_ROOM_TITLE;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_TITLE;

public class MovieDisplayActivity  extends PlayerActivity{

    private static final int DEFAULT_INT_VALUE = 1;
    private static int episodeNumber = DEFAULT_INT_VALUE;
    private ImageView episodeImage;
    private static String[] audioTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_activity_layout);
        Intent audioIntent = getIntent();

        int arraySize = DEFAULT_INT_VALUE;
        if(audioIntent.hasExtra(ARRAY_SIZE)){
            arraySize = audioIntent.getIntExtra(ARRAY_SIZE, DEFAULT_INT_VALUE);
        }
        int[] audioPath = new int[arraySize];
        String roomName = "";
        audioTitle = new String[arraySize];
        if(audioIntent.hasExtra(EPISODE_PLAY_URL)){
            audioPath = audioIntent.getIntArrayExtra(EPISODE_PLAY_URL);
        }
        if(audioIntent.hasExtra(EPISODE_TITLE)){
            audioTitle = audioIntent.getStringArrayExtra(EPISODE_TITLE);
        }
        if(audioIntent.hasExtra(EPISODE_ROOM_TITLE)){
            roomName = audioIntent.getStringExtra(EPISODE_ROOM_TITLE);
        }
        if(audioIntent.hasExtra(EPISODE_NUMBER)){
            currentEpisode = audioIntent.getIntExtra(EPISODE_NUMBER, DEFAULT_INT_VALUE);
        }


        TextView roomTitle = findViewById(R.id.general_title);
        roomTitle.setText(getString(R.string.app_name) + "\n" + roomName);

        episodeImage = findViewById(R.id.audio_iv_episode);
 //       getImageForEpisode();
//        setRoomTitle((TextView) findViewById(R.id.exo_room_name),roomName);
//        episodeSubtitle = findViewById(R.id.exo_episode_name);
//        setEpisodeTitle(episodeSubtitle ,audioTitle[currentEpisode]);
        setPlayerView((SimpleExoPlayerView) findViewById(R.id.general_playerView));
        initializeMediaSession();

        setPlayerList(audioPath,currentEpisode);
        initializePlayer();

    }


    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
       // getImageForEpisode();
    //    setEpisodeTitle(episodeSubtitle ,audioTitle[currentEpisode]);
        exoPlayer.setPlayWhenReady(true);
    }



}