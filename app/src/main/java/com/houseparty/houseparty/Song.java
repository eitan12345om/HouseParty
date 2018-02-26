package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public class Song {
    private String title;
    private String artist;
    private String uri;
    private String api;

    public Song() {
    }

    public Song(String title, String artist, String uri, String api) {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.api = api;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
