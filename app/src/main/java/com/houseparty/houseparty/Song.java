package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public class Song {
    private String title;
    private String artist;
    private String uri;
    private String api;

    //public Song(String title) {
    //    this.title = title;
    //}

    public Song(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public Song() {
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

    //public String getName() {
    //    return this.title + " - " + this.artist;
    //}

}
