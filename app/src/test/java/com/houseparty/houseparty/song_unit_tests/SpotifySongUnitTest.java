package com.houseparty.houseparty.song_unit_tests;

import com.houseparty.houseparty.SpotifySong;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SpotifySongUnitTest {
    private SpotifySong song;

    @Before
    public void setUp() {
        song = new SpotifySong("Beat It", "fake_uri", "Michael Jackson");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLogInFailed() {
        song.onLoginFailed(null);
    }

    @Test
    public void testGetAccessToken() {
        assertEquals(song.getAccessToken(), null);
    }

    @Test
    public void testGetAccessToken2() {
        SpotifySong song2 = new SpotifySong("Beat It", "fake_uri", "Michael Jackson", "fake_access_token", null);
        assertEquals(song2.getAccessToken(), "fake_access_token");
    }

    @Test(expected = NullPointerException.class)
    public void testPlaySong() {
        song.playSong();
    }

    @Test
    public void testGetSpotifyPlayer() {
        assertEquals(song.getSpotifyPlayer(), null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLoggedIn() {
        song.onLoggedIn();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLoggedOut() {
        song.onLoggedOut();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onTemporaryError() {
        song.onTemporaryError();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onConnectionMessage() {
        song.onConnectionMessage(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackEvent() {
        song.onPlaybackEvent(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackError() {
        song.onPlaybackError(null);
    }
}