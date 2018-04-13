package com.houseparty.houseparty;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlaylistUnitTest {

    private Playlist playlist;

    @Test
    public void test_toString_oneSong() throws Exception {
        playlist = new Playlist("Jackson", "12345");
        playlist.addSong(new SpotifySong("Thriller", "fake_uri"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void test_toString_twoSongs() throws Exception {
        playlist = new Playlist("Jackson", "12345");
        playlist.addSong(new SpotifySong("Thriller", "fake_uri"));
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void test_toString_twoSongs2() throws Exception {
        playlist = new Playlist("Jackson", "12345");
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2"));
        playlist.addSong(new SpotifySong("Thriller", "fake_uri"));
        assertTrue(playlist.toString().equals("JacksonBeat It"));
    }
}
