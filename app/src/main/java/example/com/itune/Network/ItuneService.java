package example.com.itune.Network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface ItuneService {

    @GET("/search")

    public void searchTems(@Query("term") String searchTerm, Callback<ItunePojo> response);

}
