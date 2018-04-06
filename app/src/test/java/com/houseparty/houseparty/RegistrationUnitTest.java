package com.houseparty.houseparty;

import android.util.Log;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jacksonkurtz on 4/4/18.
 */

public class RegistrationUnitTest {

    private LoginActivity activity = new LoginActivity();
/*
    public void setUp() throws Exception {
        activity = new LoginActivity();
        //Log.d( "SpotifyUnitTest", "test");
        System.out.println( "test");
    }
*/

    /*
    @Test
    public void authenticate_isIncorrect() throws Exception {
        assertFalse( activity.register("invalidusername@gmail.com", "1234") );
    }*/
    @Test
    public void register_correctUsername() throws Exception {
        assertTrue( activity.register("jkurtz678@gmail.com", "12345") );
    }

    @Test
    public void register_nullUsername() throws Exception {
        assertFalse( activity.register( null, "12345"));
    }

    @Test
    public void register_nullPassword() throws Exception {
        assertFalse( activity.register( "jkurtz678@gmail.com", null));
    }
    @Test
    public void register_badEmail() throws Exception {
        assertFalse( activity.register( "jkurtz678", "12345"));
    }
    @Test
    public void register_shortPassword() throws Exception {
        assertFalse( activity.register( "jkurtz678@gmail.com", "1234"));
    }
    @Test
    public void register_invalidCharacters() throws Exception {
        assertFalse( activity.register( "jackson kurtz@gmail.com", "12345"));

    }
}
