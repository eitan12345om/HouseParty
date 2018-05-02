package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song {
    protected String title;
    protected String artist;
    protected String uri;
    protected String api;

    public Song() {}

    public Song(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public Song(String title, String artist, String uri, String api ) {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.api = api;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUri() {
        return this.uri;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getApi() {
        return this.api;
    }


    @Override
    public String toString() {
        return this.title;
    }

    public abstract int playSong();
}
