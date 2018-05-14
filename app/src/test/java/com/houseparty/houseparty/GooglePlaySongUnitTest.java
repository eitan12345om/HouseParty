package com.houseparty.houseparty;

import org.junit.Test;

public class GooglePlaySongUnitTest {
    @Test(expected = UnsupportedOperationException.class)
    public void testPlaySong() {
        Song song = new GooglePlaySong("Beat It", "fake_uri");
        song.playSong();
    }
}