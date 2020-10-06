package turkusoweaplikacje.dmm.zaspiewane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.ARRAY_SIZE;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_IMAGE_URL;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_NUMBER;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_PLAY_URL;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_ROOM_TITLE;
import static turkusoweaplikacje.dmm.zaspiewane.ListOfExcercises.EPISODE_TITLE;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String[] audioPath;
    private int arraySize = 2;
    private String[] audioTitle;
    private String[] imagePaths;

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
    }

    private void openMovieClass(){
        audioPath = new String[arraySize];
        audioPath[0] = "android.resource://" + getPackageName() + "/" + R.raw.byl_sobie_krol;
        audioPath[1] = "android.resource://" + getPackageName() + "/" + R.raw.byl_sobie_krol;
        audioTitle = new String[arraySize];
        audioTitle[0] = "Idzie wąż";
        audioTitle[1] = "Rozpoczynajka";
        imagePaths = new String[arraySize];
        imagePaths[0] = "android.resource://" + getPackageName() + "/" + R.mipmap.headphones;
        imagePaths[1] = "android.resource://" + getPackageName() + "/" + R.mipmap.ic_launcher;

        Intent audioIntent = new Intent(this, MovieDisplayActivity.class);
        audioIntent.putExtra(EPISODE_PLAY_URL,audioPath);
        audioIntent.putExtra(EPISODE_IMAGE_URL,imagePaths);
        audioIntent.putExtra(EPISODE_ROOM_TITLE,getResources().getString(R.string.app_name));
        audioIntent.putExtra(EPISODE_TITLE,audioTitle);
        audioIntent.putExtra(EPISODE_NUMBER,1);
        audioIntent.putExtra(ARRAY_SIZE,arraySize);
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


}
