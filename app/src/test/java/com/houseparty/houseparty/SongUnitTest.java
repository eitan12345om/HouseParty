package com.houseparty.houseparty;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SongUnitTest {
    private Song song;

    @Test
    public void test_toString() throws Exception {
        song = new Song("Thriller", "fake_uri");
        assertTrue(song.toString().equals("Thriller"));
    }

    @Test
    public void test_toString2() throws Exception {
        song = new Song("Beat It", "fake_uri");
        assertTrue(song.toString().equals("Beat It"));
    }
}