package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

public class MainActivityUnitTest {
    private MainActivity mainActivity;

    @Before
    public void setup() {
        mainActivity = new MainActivity();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLogInFailed() {
        mainActivity.onLoginFailed(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onLoggedOut() {
        mainActivity.onLoggedOut();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onTemporaryError() {
        mainActivity.onTemporaryError();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onConnectionMessage() {
        mainActivity.onConnectionMessage(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackEvent() {
        mainActivity.onPlaybackEvent(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_onPlaybackError() {
        mainActivity.onPlaybackError(null);
    }
}