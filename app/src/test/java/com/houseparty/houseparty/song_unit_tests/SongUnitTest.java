package com.houseparty.houseparty.song_unit_tests;

import com.houseparty.houseparty.Song;
import com.houseparty.houseparty.SpotifySong;
import com.houseparty.houseparty.TidalSong;

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
        assertEquals("Thriller", song.toString());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Thriller", song.getTitle());
    }

    @Test
    public void testGetUri() {
        assertEquals("fake_uri", song.getUri());
    }

    @Test
    public void testGetArtist() {
        assertEquals("Michael Jackson", song.getArtist());
    }

    @Test
    public void testEquals() {
        Song song2 = new SpotifySong("Thriller", "fake_uri", "Michael Jackson");
        assertEquals(song, song2);
    }

    @Test
    public void testEquals2() {
        assertEquals(song, song);
    }

    @Test
    public void testNotEquals() {
        Song song2 = new SpotifySong("Thrill", "fake_uri", "Michael Jackson");
        assertNotEquals(song, song2);
    }

    @Test
    public void testNotEquals2() {
        assertNotEquals(null, song);
    }

    @Test
    public void testNotEquals3() {
        Song song2 = new TidalSong("Thrill", "fake_uri", "Michael Jackson");
        assertNotEquals(song, song2);
    }

    @Test
    public void testHashCode() {
        Song song2 = new SpotifySong("Thriller", "fake_uri", "Michael Jackson");
        assertEquals(song.hashCode(), song2.hashCode());
    }

    @Test
    public void testNoArgumentConstructor() {
        Song song2 = new SpotifySong();
        assertNotEquals(song, song2);
    }
}