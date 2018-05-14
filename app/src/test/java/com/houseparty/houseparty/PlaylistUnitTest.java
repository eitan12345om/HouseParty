package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlaylistUnitTest {

    private Playlist playlist;

    @Before
    public void setUp() {
        playlist = new Playlist("Jackson", "12345");
    }

    @Test
    public void testToStringOneSong() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void testToStringTwoSongs() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonThriller"));
    }

    @Test
    public void testToStringTwoSongs2() {
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertTrue(playlist.toString().equals("JacksonBeat It"));
    }

    @Test
    public void testGetName() {
        assertEquals(playlist.getName(), "Jackson");
    }

    @Test
    public void testGetPasscode() {
        assertEquals(playlist.getPasscode(), "12345");
    }

    @Test
    public void testGetSongs() {
        assertEquals(playlist.getSongs(), new ArrayList<Song>());
    }

    @Test
    public void testNoArgumentConstructor() {
        Playlist playlist = new Playlist();
        assertEquals(playlist.getName(), null);
    }
}
