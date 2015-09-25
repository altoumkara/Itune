package example.com.itune;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import example.com.itune.Network.LyricsPojo;
import example.com.itune.Network.LyricsService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Activity to display the lyrics song details and the lyrics.
 * <p/>
 */
public class LyricsActivity extends AppCompatActivity {
    public static final String TAG = "LyricsActivity";
    private static final String SAVE_INSTANCE_STATE = "SAVE_INSTANCE_STATE";//key for saveInstanceState
    // http://lyrics.wikia.com/api.php?func=getSong&artist=Tom+Waits&song=new+coat+of+paint&fmt=json
    private final String SONG_LYRIC_URL = "http://lyrics.wikia.com/"; //base lyric url
    /**
     * creating the constants needed in the query string
     */
    private final String PARAM_FUNC = "func";
    private final String PARAM_ARTIST = "artist";
    private final String PARAM_SONG = "song";
    private final String PARAM_FORMAT = "fmt";
    public ImageView mAlbumImage;
    public TextView mTrackName, mArtistName, mAlbumName, mLyricTV;
    public ViewGroup mRow;
    private String mLyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lyrics);

        mAlbumImage = (ImageView) findViewById(R.id.albumIV);
        mTrackName = (TextView) findViewById(R.id.trackNameTV);
        mAlbumName = (TextView) findViewById(R.id.albumNameTV);
        mArtistName = (TextView) findViewById(R.id.artistNameTV);
        mLyricTV = (TextView) findViewById(R.id.lyricTxt);

        Intent intent = getIntent();
        String artist = intent.getStringExtra(Constants.ARTIST_NAME);
        String songName = intent.getStringExtra(Constants.SONG_NAME);
        String imageUrl = intent.getStringExtra(Constants.IMAGE_URL);
        String albumName = intent.getStringExtra(Constants.ALBUM_NAME);

        //checking if this activity is creating for the first time or not
        if (savedInstanceState != null) {
            mLyric = savedInstanceState.getString(SAVE_INSTANCE_STATE);
            mLyricTV.setText(mLyric );
        } else {
            showLyric(artist, songName);//getting the lyrics and display it
        }

        mTrackName.setText(songName);
        mArtistName.setText(artist);
        mAlbumName.setText(albumName);
        //using Picasso to set of image
        Picasso.with(this).load(imageUrl).into(mAlbumImage);
    }

    /**
     * This is used to start the Http call to the wikia REST api with the specific artist name(s),
     * and song name using retrofit.
     *
     * @param artist is the artist name
     * @param artist is the song lyric we are interested in
     */
    public void showLyric(final String artist, final String songName) {
        /**
         * The lyric api gives us a non-Valid json file.
         */
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(SONG_LYRIC_URL)
                .setConverter(new CusConverter())
                .build();

        //creating as service for our rest adapter with LyricsService
        LyricsService lyricsServices = restAdapter.create(LyricsService.class);
        /**
         * let create a map of key value pairs for our query string.
         */
        Map<String, String> queryString = new HashMap<>();

        queryString.put(PARAM_FORMAT, "json");

        queryString.put(PARAM_SONG, songName);
        queryString.put(PARAM_ARTIST, artist);

        queryString.put(PARAM_FUNC, "getSong");


        // Now we are calling the function to get an response for the server
        lyricsServices.getLyric(queryString, new Callback<LyricsPojo>() {

            @Override
            public void success(LyricsPojo lyricsModel, Response response) {
                mLyric = lyricsModel.getLyrics();
                mLyricTV.setText(mLyric);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "" + error);
                Log.d(TAG, "" + error.getUrl());
            }
        });
    }


    /**
     * Saving the state of the Lyric
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((mLyric != null) && !mLyric.equals(""))) {
            outState.putString(SAVE_INSTANCE_STATE, mLyric);
        }
    }


}
