package turkusoweaplikacje.dmm.zaspiewane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import turkusoweaplikacje.dmm.zaspiewane.adapters.LessonListAdapter;

public class MainActivity extends AppCompatActivity implements LessonListAdapter.ListItemClickListener{

    public static final String EPISODE_IMAGE_URL = "dmm.zaspiewane.EPISODE_IMAGE_URL";
    public static final String EPISODE_PLAY_URL = "dmm.zaspiewane.EPISODE_PLAY_URL";
    public static final String EPISODE_TITLE = "dmm.zaspiewane.EPISODE_TITLE";
    public static final String EPISODE_ROOM_TITLE = "dmm.zaspiewane.EPISODE_ROOM_TITLE";
    public static final String EPISODE_NUMBER = "dmm.zaspiewane.EPISODE_NUMBER";
    public static final String ARRAY_SIZE = "dmm.zaspiewane.ARRAY_SIZE";

    private RecyclerView recyclerView;
    private LessonListAdapter lessonListAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    private AppBarConfiguration mAppBarConfiguration;
    private int[] audioPath;
    private int arraySize = 4;
    private String[] audioTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieClass();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        prepareListOfVideos();
    }

    private void openMovieClass(){
        Intent audioIntent = preparePlaylistSettingsToDisplayList(audioPath,audioTitle,0);
        startActivity(audioIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onListItemClick(int[] path, String[] title, int episodeNumber){
        Intent audioIntent = preparePlaylistSettingsToDisplayList(path,title,episodeNumber);
        startActivity(audioIntent);
    }

    private Intent preparePlaylistSettingsToDisplayList(int[] path,  String[] title, int episodeNumber){
        Intent audioIntent = new Intent(this, MovieDisplayActivity.class);
        audioIntent.putExtra(EPISODE_PLAY_URL,path);
        audioIntent.putExtra(EPISODE_ROOM_TITLE,getResources().getString(R.string.app_name));
        audioIntent.putExtra(EPISODE_TITLE,title);
        audioIntent.putExtra(EPISODE_NUMBER,episodeNumber);
        audioIntent.putExtra(ARRAY_SIZE,arraySize);
        return audioIntent;
    }

    private void prepareListOfVideos(){
        audioPath = new int[arraySize];
        audioPath[0] = R.raw.kaczka_pstra;
        audioPath[1] = R.raw.chusteczka;
        audioPath[2] = R.raw.byl_sobie_krol;
        audioPath[3] = R.raw.papa;

        audioTitle = new String[arraySize];
        audioTitle[0] = "Kaczka pstra";
        audioTitle[1] = "Chusteczka";
        audioTitle[2] = "Był sobie król";
        audioTitle[3] = "Pa Pa";

        //time in miliseconds
        long[] videoTime = new long[arraySize];
        videoTime[0] = 59000;
        videoTime[1] = 125000;
        videoTime[2] = 132000;
        videoTime[3] = 54000;

        recyclerView = findViewById(R.id.episodes_rv_podcasts);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        lessonListAdapter = new LessonListAdapter(arraySize,this);

        recyclerView.clearOnScrollListeners();
        lessonListAdapter.setLessonParameters(audioTitle,audioPath,videoTime,arraySize);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lessonListAdapter);
    }

}
