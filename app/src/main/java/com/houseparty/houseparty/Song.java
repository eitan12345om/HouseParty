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

    public Song(String title) {
        this.title = title;
    }

    public Song(String title, String artist, String uri, String api) {
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.api = api;
    }

    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getName() {
        return this.title + " - " + this.artist;
    }

}
