package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

public class SoundCloudSongUnitTest {
    Song song;

    @Before
    public void setUp() {
        song = new SoundCloudSong("Beat It", "fake_uri");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPlaySong() {
        song.playSong();
    }
}