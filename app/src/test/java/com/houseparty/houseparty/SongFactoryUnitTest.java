package com.houseparty.houseparty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SongFactoryUnitTest {
    private SongFactory songFactory;

    @Test
    public void test_getInstance() {
        songFactory = SongFactory.getInstance();
        SongFactory songFactory1 = SongFactory.getInstance();
        assertEquals(songFactory1, songFactory);
    }
}