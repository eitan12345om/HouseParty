package com.houseparty.houseparty.song_unit_tests;

import com.houseparty.houseparty.Song;
import com.houseparty.houseparty.TidalSong;

import org.junit.Before;
import org.junit.Test;

public class TidalSongUnitTest {
    private Song song;

    @Before
    public void setUp() {
        song = new TidalSong("Beat It", "fake_uri", "Michael Jackson");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPlaySong() {
        song.playSong();
    }
}