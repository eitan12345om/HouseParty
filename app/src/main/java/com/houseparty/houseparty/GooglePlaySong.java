package com.houseparty.houseparty;

/**
 * Created by jacksonkurtz on 4/12/18.
 */

public class GooglePlaySong extends Song {
    public GooglePlaySong(String title, String uri, String artist) {
        super(title, uri, artist);
    }

    public void playSong() {
        throw new UnsupportedOperationException();
    }
}
