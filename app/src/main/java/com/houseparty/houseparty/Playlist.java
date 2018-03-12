package com.houseparty.houseparty;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist() {
    }

    public Playlist(String name){
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song){
        this.songs.add(song);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Song> getSongs(){
        return this.songs;
    }

    @Override
    public String toString(){
        return this.name + songs.get(0).getTitle();
    }
}
