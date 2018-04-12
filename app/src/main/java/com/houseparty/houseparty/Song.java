package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song {
    protected String title;
    protected String artist;
    protected String uri;
    protected String api;

    public Song(String title, String uri) {
        this.title = title;
        this.uri = uri;
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

    @Override
    public String toString() {
        return this.title;
    }

    public abstract int playSong();
}
