package example.com.itune;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import example.com.itune.Network.LyricsPojo;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Retrofit uses Gson as default, but because the json that we are
 * getting from the above api is not valid, we will have exception.
 */
public class CusConverter implements Converter {
    public static final String TAG = "CustomConverter";


    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {

        //Our POJO class that will contain the Json data
        LyricsPojo lyricsPojo = new LyricsPojo();

        BufferedReader bufferedReader = null;
        StringBuffer lyricStringdata = null;
        JSONObject jsonObject = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(body.in()));
            lyricStringdata = new StringBuffer();
            String read;
            while ((read = bufferedReader.readLine()) != null) {
                lyricStringdata.append(read);
            }


            jsonObject = new JSONObject(lyricStringdata.substring(lyricStringdata.indexOf("{"),
                    lyricStringdata.indexOf("}") + 1));

            //setting our POJO fields
            lyricsPojo.setArtist(jsonObject.getString("artist"));
            lyricsPojo.setSong(jsonObject.getString("song"));
            lyricsPojo.setLyrics(jsonObject.getString("lyrics"));
            lyricsPojo.setUrl(jsonObject.getString("url"));

        } catch (IOException e) {
            Log.d(TAG, "" + e + "\n");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d(TAG, "" + e + "\n");
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {//closing the bufferedReader
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return lyricsPojo;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
