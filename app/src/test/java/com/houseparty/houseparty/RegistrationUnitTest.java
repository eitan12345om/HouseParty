package com.houseparty.houseparty;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jacksonkurtz on 4/4/18.
 */

public class RegistrationUnitTest {

    private LoginActivity activity = new LoginActivity();

    @Test
    public void register_correctInputs() throws Exception {
        assertTrue(activity.register("jkurtz678@gmail.com", "12345"));
    }

    @Test
    public void register_existingUsername() throws Exception {
        assertFalse(activity.register("matt@gmail.com", "12345"));
    }

    @Test
    public void register_nullUsername() throws Exception {
        assertFalse(activity.register(null, "12345"));
    }

    @Test
    public void register_nullPassword() throws Exception {
        assertFalse(activity.register("jkurtz678@gmail.com", null));
    }

    @Test
    public void register_badEmail() throws Exception {
        assertFalse(activity.register("jkurtz678", "12345"));
    }

    // longer than 4, shorter than 16
    @Test
    public void register_badPasswordLength() throws Exception {
        assertTrue(activity.register("jkurtz678@gmail.com", "12345"));
        assertFalse(activity.register("jkurtz678@gmail.com", "123"));
        assertFalse(activity.register("jkurtz678@gmail.com", "123423123134235111"));
    }

    @Test
    public void register_invalidCharacters() throws Exception {
        assertFalse(activity.register("jac##  kson kurtz@gmail.com", "12345"));

    }
}
