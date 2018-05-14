package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlaylistUnitTest {

    private Playlist playlist;

    @Before
    public void setUp() {
        playlist = new Playlist("Jackson", "12345", "example@example.com");
    }

    @Test
    public void testToStringOneSong() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertEquals("JacksonThriller", playlist.toString());
    }

    @Test
    public void testToStringTwoSongs() {
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        assertEquals("JacksonThriller", playlist.toString());
    }

    @Test
    public void testToStringTwoSongs2() {
        playlist.addSong(new SpotifySong("Beat It", "fake_uri2", "Michael Jackson"));
        playlist.addSong(new SpotifySong("Thriller", "fake_uri", "Michael Jackson"));
        assertEquals("JacksonBeat It", playlist.toString());
    }

    @Test
    public void testGetName() {
        assertEquals("Jackson", playlist.getName());
    }

    @Test
    public void testGetPasscode() {
        assertEquals("12345", playlist.getPasscode());
    }

    @Test
    public void testGetSongs() {
        assertEquals(new ArrayList<Song>(), playlist.getSongs());
    }

    @Test
    public void testGetHost() {
        assertEquals("example@example.com", playlist.getHost());
    }

    @Test
    public void testNoArgumentConstructor() {
        Playlist playlist = new Playlist();
        assertEquals(null, playlist.getName());
    }
}
