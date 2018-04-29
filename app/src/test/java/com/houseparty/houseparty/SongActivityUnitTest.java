package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SongActivityUnitTest {

    private SongActivity sa = new SongActivity();

    @Before
    public void setup() {
        sa.authenticateUser();
    }

    @Test
    public void testUserAuthentication() throws Exception {
        assertTrue(sa.authenticateUser());
    }

    @Test
    public void testLoggedIn() throws Exception {
        assertTrue(sa.testOnLoggedIn());
    }

    @Test
    public void testLoggedOut() throws Exception {
        assertTrue(sa.testOnLoggedOut());
    }

    @Test
    public void testOnDestroy() throws Exception {
        assertTrue(sa.testOnDestroy());
    }

    @Test
    public void testSpotifySearch() throws Exception {
        assertTrue(sa.testSearchForTrack());
    }

}
