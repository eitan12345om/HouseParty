package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlaylistUnitTest {

    private Playlist playlist;

    @Before
    public void setUp() {
        playlist = new Playlist("Jackson", "12345");
    }

    @Test
    public void test_toString_oneSong() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void test_toString_twoSongs() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void test_toString_twoSongs2() {
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonBeat It"));
    }
}
