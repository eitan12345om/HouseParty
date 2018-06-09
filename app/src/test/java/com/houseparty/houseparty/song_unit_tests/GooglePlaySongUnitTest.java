package com.houseparty.houseparty.song_unit_tests;

import com.houseparty.houseparty.GooglePlaySong;
import com.houseparty.houseparty.Song;

import org.junit.Before;
import org.junit.Test;

public class GooglePlaySongUnitTest {
    private Song song;

    @Before
    public void setUp() {
        song = new GooglePlaySong("Beat It", "fake_uri", "Michael Jackson");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPlaySong() {
        song.playSong();
    }
}