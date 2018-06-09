package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jacksonkurtz on 4/4/18.
 */

public class RegistrationLoginIntegrationTest {

    private LoginActivity activity;

    @Before
    public void setup() {
        activity = new LoginActivity();
    }

    @Test
    public void createAndLoginUser_isCorrect() throws Exception {
        assertTrue(activity.testUserLogin("metsfan1600@gmail.com", "123456"));
    }

    @Test
    public void createAndLoginUser_incorrectPassword() throws Exception {
        assertFalse(activity.testUserLogin("metsfan1600@gmail.com", "asdfghjkl"));
    }

    @Test
    public void createAndLoginUser_nullLogin() throws Exception {
        assertFalse(activity.testUserLogin(null, "123456"));
    }

    @Test
    public void createAndLoginUser_nullPassword() throws Exception {
        assertFalse(activity.testUserLogin("metsfan1600@gmail.com", null));
    }

    @Test
    public void createAndLoginUser_invalidEmail() throws Exception {
        assertFalse(activity.testUserLogin("metsfan1600", "123456"));
    }

    @Test
    public void createAndLoginUser_badPasswordLength() throws Exception {
        assertFalse(activity.testUserLogin("metsfan1600@gmail.com", "123"));
    }

}
