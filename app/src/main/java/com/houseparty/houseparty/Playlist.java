package com.example.jacksonkurtz.housepartytest;

/**
 * Created by jacksonkurtz on 2/23/18.
 */
import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist() {
    }

    public Playlist( String name ){
        this.name = name;
        this.songs = new ArrayList<Song>();
    }

    public void addSong( Song song ){
        this.songs.add(song);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Song> getSongs(){
        return this.songs;
    }
}
