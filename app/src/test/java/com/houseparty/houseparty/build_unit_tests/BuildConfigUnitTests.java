package com.houseparty.houseparty.build_unit_tests;

import com.houseparty.houseparty.BuildConfig;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eitan created on 5/14/2018.
 */
public class BuildConfigUnitTests {
    @Test
    public void testDEBUG() {
        assertEquals(Boolean.parseBoolean("true"), BuildConfig.DEBUG);
    }
}
