package com.houseparty.houseparty;

import org.junit.*;
import static org.junit.Assert.*;

public class SongActivityUnitTest {

    private SongActivity sa = new SongActivity();

    @Test
    public void testUserAuthentication() throws Exception { assertTrue(sa.authenticateUser()); }

    @Test
    public void testLoggedIn() throws Exception { assertTrue(sa.testOnLoggedIn()); }

    @Test
    public void testLoggedOut() throws Exception { assertTrue(sa.testOnLoggedOut()); }

    @Test
    public void testOnDestroy() throws Exception { assertTrue(sa.testOnDestroy()); }

    @Test
    public void testSpotifySearch() throws Exception { assertTrue(sa.testSearchForTrack()); }

}
