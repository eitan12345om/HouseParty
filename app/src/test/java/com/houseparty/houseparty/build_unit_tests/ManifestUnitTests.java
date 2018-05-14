package com.houseparty.houseparty.build_unit_tests;

import com.houseparty.houseparty.Manifest;

import org.junit.Test;

/**
 * @author Eitan created on 5/14/2018.
 */
public class ManifestUnitTests {
    @Test
    public void testDEBUG() {
        Manifest m = new Manifest();
        Manifest.permission p = new Manifest.permission();
        assertEquals("com.houseparty.houseparty.permission.C2D_MESSAGE", Manifest.permission.C2D_MESSAGE);
    }
}
