package example.com.itune.Network;

import com.google.gson.annotations.Expose;

/**
 * This POJO/Model class will contain all the Json data.
 */
public class LyricsPojo {

    @Expose
    private String artist;
    @Expose
    private String lyrics;
    @Expose
    private String song;
    @Expose
    private String url;

    /**
     *
     * @return
     * The artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     *
     * @return
     * The lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     *
     * @param lyrics
     * The lyrics
     */
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     *
     * @return
     * The song
     */
    public String getSong() {
        return song;
    }

    /**
     *
     * @param song
     * The song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}



