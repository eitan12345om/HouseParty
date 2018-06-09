package com.houseparty.houseparty.activity_unit_tests;

import com.houseparty.houseparty.NewSongActivity;

import org.junit.Before;
import org.junit.Test;

public class NewSongActivityUnitTest {
    private NewSongActivity sa;

    @Before
    public void setup() {
        sa = new NewSongActivity();
    }

    @Test(expected = NullPointerException.class)
    public void testSetUpButton() {
        sa.setUpButton();
    }

    @Test(expected = NullPointerException.class)
    public void testSetUpAdapter() {
        sa.setUpAdapter();
    }
}
