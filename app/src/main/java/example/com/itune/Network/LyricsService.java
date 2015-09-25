package example.com.itune.Network;

import java.util.Map;

import example.com.itune.Network.LyricsPojo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;


public interface LyricsService {

    //using the GET method
    @GET("/api.php")

    /**
     * @param songDetails is a map of key(param name in query string)-value(corresponding
     *                    param name's value in query string).
     *
     * @param lyricsResponse is POJO class that will have our json data
     */
    public void getLyric(@QueryMap(encodeValues = true) Map<String, String> songInfo, Callback<LyricsPojo> response);

}
