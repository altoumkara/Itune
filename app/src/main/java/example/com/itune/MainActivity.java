package example.com.itune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import example.com.itune.Network.ItunePojo;
import example.com.itune.Network.ItuneService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Activity to display the list of song result getting from the REST api
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final String SAVE_INSTANCE_STATE = "SAVE_INSTANCE_STATE";//key for saveInstanceState

    private String mSearchTerm;


    private final String ITUNE_URL = "https://itunes.apple.com";
    @InjectView(R.id.searchEditText)
    public EditText mSearchEditTxt;

    @InjectView(R.id.recyclerView)
    public android.support.v7.widget.RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);//injecting our xml views
        //checking if this activity is creating for the first time or not
        if(savedInstanceState != null) {

            initRestAdapter(savedInstanceState.getString(SAVE_INSTANCE_STATE));
        }
    }

    /**
     * This is the search button to start searching
     * @param view
     */
    public void search(View view) {
        String searchTerm = mSearchEditTxt.getText().toString();
        if((!searchTerm.equals(""))&&(searchTerm != null)) {
            initRestAdapter(searchTerm);
        }
    }


    /**
     * This is used to start the Http call to the Itune REST api with the specific search
     * term  using retrofit.
     * @param term is the search term user might enter in the edittext
     */
    public void initRestAdapter(String term) {
        mSearchTerm = term;
        /**
         * building a RestAdapter Object with end point our base Url.
         * I want to log the headers, body and the metadata while sending requests and getting response.
         */
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ITUNE_URL)
                .build();

        //service for the rest adapter with ItuneService
        ItuneService ituneService = restAdapter.create(ItuneService.class);

        //Now we are calling the function to get an response for the server
        ituneService.searchTems(term, new Callback<ItunePojo>() {

            @Override
            public void success(ItunePojo itunePojo, Response response) {
                //display the data in my recycler view
                mRecyclerView.setAdapter(new RecyclerAdapter(itunePojo.getResults(), getApplicationContext()));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, ""+error);
            }
        });
    }




    /**
     * Saving the state of the search
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(((mSearchTerm != null)&&!mSearchTerm.equals(""))) {
            outState.putString(SAVE_INSTANCE_STATE, mSearchTerm);
        }
    }



}
