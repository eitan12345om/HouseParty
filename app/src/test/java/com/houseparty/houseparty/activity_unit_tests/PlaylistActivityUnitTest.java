package com.houseparty.houseparty.activity_unit_tests;

import com.houseparty.houseparty.PlaylistActivity;

import org.junit.Before;
import org.junit.Test;

public class PlaylistActivityUnitTest {
    private PlaylistActivity playlistActivity;

    @Before
    public void setup() {
        playlistActivity = new PlaylistActivity();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLogInFailed() {
        playlistActivity.onLoginFailed(null);
    }

    @Test
    public void test_onLoggedOut() {
        playlistActivity.onLoggedOut();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onTemporaryError() {
        playlistActivity.onTemporaryError();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onConnectionMessage() {
        playlistActivity.onConnectionMessage(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackEvent() {
        playlistActivity.onPlaybackEvent(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackError() {
        playlistActivity.onPlaybackError(null);
    }
}