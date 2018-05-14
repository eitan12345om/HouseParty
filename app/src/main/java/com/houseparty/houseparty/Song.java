package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song {
    protected String title;
    protected String artist;
    protected String uri;

    public Song() {
    }

    public Song(String title, String uri, String artist) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Song song = (Song) obj;
        return title.equals(song.title)
            && artist.equals(song.artist)
            && uri.equals(song.uri);
    }

    public abstract void playSong();
}
