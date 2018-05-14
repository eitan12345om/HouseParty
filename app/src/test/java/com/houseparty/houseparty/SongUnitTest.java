package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SongUnitTest {
    private Song song;

    @Before
    public void setUp() {
        song = new SpotifySong("Thriller", "fake_uri", "Michael Jackson");
    }

    @Test
    public void testToString() {
        assertEquals(song.toString(), "Thriller");
    }

    @Test
    public void testGetTitle() {
        assertEquals(song.getTitle(), "Thriller");
    }

    @Test
    public void testGetUri() {
        assertEquals(song.getUri(), "fake_uri");
    }

    @Test
    public void testGetArtist() {
        assertEquals(song.getArtist(), "Michael Jackson");
    }

    @Test
    public void testEquals() {
        Song song2 = new SpotifySong("Thriller", "fake_uri", "Michael Jackson");
        assertEquals(song, song2);
    }

    @Test
    public void testNotEquals() {
        Song song2 = new SpotifySong("Thrill", "fake_uri", "Michael Jackson");
        assertNotEquals(song, song2);
    }

    @Test
    public void testHashCode() {
        Song song2 = new SpotifySong("Thriller", "fake_uri", "Michael Jackson");
        assertEquals(song.hashCode(), song2.hashCode());
    }
}