package turkusoweaplikacje.dmm.zaspiewane;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import turkusoweaplikacje.dmm.zaspiewane.adapters.LessonListAdapter;

public class ListOfExcercises extends AppCompatActivity
        //implements LessonListAdapter.ListItemClickListener
        {

    public static final String EPISODE_IMAGE_URL = "dmm.zaspiewane.EPISODE_IMAGE_URL";
    public static final String EPISODE_PLAY_URL = "dmm.zaspiewane.EPISODE_PLAY_URL";
    public static final String EPISODE_TITLE = "dmm.zaspiewane.EPISODE_TITLE";
    public static final String EPISODE_ROOM_TITLE = "dmm.zaspiewane.EPISODE_ROOM_TITLE";
    public static final String EPISODE_NUMBER = "dmm.zaspiewane.EPISODE_NUMBER";
    public static final String ARRAY_SIZE = "dmm.zaspiewane.ARRAY_SIZE";

    private RecyclerView recyclerView;
    private LessonListAdapter lessonListAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private static String roomName;

    private String[] names;
    private String[] paths;
    private String[] images;
    private String[] episodeNumbers;
    private long[] time;

    private int numberOfResultsToDisplay;
    private int roomNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episodes_activity_layout);

        Intent episodeIntent = getIntent();
        if(episodeIntent.hasExtra(Intent.EXTRA_TEXT)){
            roomNumber = episodeIntent.getIntExtra(Intent.EXTRA_TEXT ,6);
        }
        roomNumber++;
      //  episodeNumbers = SpreakerEpisodes.getRoomTable(roomNumber);
        //text below is just for testing 2 movies from Asia
        episodeNumbers = new String[2];
        episodeNumbers[0] = "1";
        episodeNumbers[1] = "2";


        numberOfResultsToDisplay = episodeNumbers.length;

        names = new String[episodeNumbers.length];
        paths = new String[episodeNumbers.length];
        images = new String[episodeNumbers.length];
        time = new long[episodeNumbers.length];


        recyclerView = (RecyclerView) findViewById(R.id.episodes_rv_podcasts);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
//        lessonListAdapter = new LessonListAdapter(numberOfResultsToDisplay,this);

//        setPlayArray(roomNumber);
//
//        ConnectivityManager cm =
//                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if(activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting()){
//            new SpreakerWebConnector().execute(episodeNumbers);
//        }
//        else {
//      //      Toast.makeText(EpisodesActivity.this,getString(R.string.no_connection),Toast.LENGTH_SHORT).show();
//        }


        roomName = getString(getResources().getIdentifier("room" + Integer.toString(roomNumber),"string",getPackageName()));
        TextView roomTitle = (TextView) findViewById(R.id.general_title);
        String roomTextTitle = getString(R.string.app_name) + "\n" + roomName;
        roomTitle.setText(roomTextTitle);

        ImageView roomImage = (ImageView) findViewById(R.id.episodes_iv_room);
        roomImage.setImageResource(getResources().getIdentifier("k" + Integer.toString(roomNumber),"mipmap",getPackageName()));

        TextView roomDescription = (TextView) findViewById(R.id.episodes_iv_description);
        roomDescription.setText(getString(getResources().getIdentifier("room_description_" + Integer.toString(roomNumber),"string",getPackageName())));

    }


//    private void setPlayArray(int roomNumber){
//        for(int position =0; position <episodeNumbers.length; position++) {
//            paths[position] = NetworkUtils.buildUrlForPlay(roomNumber, position);
//        }
//    }

 //   @Override
    public void onListItemClick(String[] path, String[] image, String[] title, int episodeNumber){
        Intent audioIntent = new Intent(this, MovieDisplayActivity.class);
        audioIntent.putExtra(EPISODE_PLAY_URL,path);
        audioIntent.putExtra(EPISODE_IMAGE_URL,image);
        audioIntent.putExtra(EPISODE_ROOM_TITLE,roomName);
        audioIntent.putExtra(EPISODE_TITLE,title);
        audioIntent.putExtra(EPISODE_NUMBER,episodeNumber);
        audioIntent.putExtra(ARRAY_SIZE,numberOfResultsToDisplay);
        startActivity(audioIntent);
    }

//    public class SpreakerWebConnector extends AsyncTask<String[], Void, String> {
//
//        @Override
//        protected String doInBackground(String[]... params) {
//            String[] episodes = params[0];
//            String searchResults = null;
//            HttpURLConnection urlConnection = null;
//            URL searchUrl;
//
//            int arrayPosition = 0;
//            while (arrayPosition<episodes.length){
//                searchUrl = NetworkUtils.buildUrlForSearch(roomNumber,arrayPosition);
//                try {
//                    urlConnection = (HttpURLConnection) searchUrl.openConnection();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    searchResults = NetworkUtils.getResponseFromHttpUrl(urlConnection);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                names[arrayPosition] = JsonParser.getEpisodeTitle(searchResults);
//                images[arrayPosition] = JsonParser.getEpisodeImage(searchResults);
//                time[arrayPosition] = JsonParser.getEpisodeTime(searchResults);
//                arrayPosition ++;
//            }
//            return searchResults;
//        }
//
//        @Override
//        protected void onPostExecute(String searchResults){
//            recyclerView.clearOnScrollListeners();
//            lessonListAdapter.setLessonParameters(names,paths,images,time,numberOfResultsToDisplay);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerView.setAdapter(lessonListAdapter);
//        }
    }