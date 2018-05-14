package com.houseparty.houseparty;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private String host;
    private String passcode;
    private List<Song> songs = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(String name, String passcode, String host) {
        this.name = name;
        this.passcode = passcode;
        this.host = host;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public String getName() {
        return this.name;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public String getHost() {
        return host;
    }

    public List<Song> getSongs() {
        return this.songs;
    }

    @Override
    public String toString() {
        return this.name + songs.get(0).toString();
    }
}
