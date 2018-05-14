package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SpotifySongUnitTest {
    private Song song;

    @Before
    public void setUp() {
        song = new SpotifySong("Thriller", "fake_uri");
    }

    @Test
    public void testToString() {
        assertTrue(song.toString().equals("Thriller"));
    }

    @Test
    public void testGetTitle() {
        assertTrue(song.getTitle().equals("Thriller"));
    }

    @Test
    public void testGetUri() {
        assertTrue(song.getUri().equals("fake_uri"));
    }

    @Test
    public void testGetArtist() {
        assertTrue(song.getArtist() == null);
    }
}