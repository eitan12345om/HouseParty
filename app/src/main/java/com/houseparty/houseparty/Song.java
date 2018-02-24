package com.example.jacksonkurtz.housepartytest;

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

    public Song( String title, String artist, String uri, String api) {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.api = api;
    }

}
