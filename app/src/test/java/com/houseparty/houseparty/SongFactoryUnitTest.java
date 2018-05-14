package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SongFactoryUnitTest {
    private SongFactory songFactory;

    @Before
    public void setUp() {
        songFactory = SongFactory.getInstance();
    }

    @Test
    public void test_getInstance() {
        SongFactory songFactory1 = SongFactory.getInstance();
        assertEquals(songFactory1, songFactory);
    }

    @Test
    public void test_createSongTidal() {
        Song song = songFactory.createSong("Beat It", null, "tidal");
        assertEquals(TidalSong.class, song.getClass());
    }

    @Test
    public void test_createSongGooglePlay() {
        Song song = songFactory.createSong("Beat It", null, "googleplay");
        assertEquals(GooglePlaySong.class, song.getClass());
    }

    @Test
    public void test_createSongSoundCloud() {
        Song song = songFactory.createSong("Beat It", null, "soundcloud");
        assertEquals(SoundCloudSong.class, song.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_createSongSpotify() {
        Song song = songFactory.createSong("Beat It", null, "spotify");
        assertEquals(SpotifySong.class, song.getClass());
    }
}