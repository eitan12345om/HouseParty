package com.houseparty.houseparty;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jacksonkurtz on 4/4/18.
 */

public class RegistrationLoginIntegrationTest {

    private LoginActivity activity;

    @Before
    public void setup() throws Exception {
        activity = new LoginActivity();
    }

    @Test
    public void createAndLoginUser_isCorrect() throws Exception {
        assertTrue( activity.createUserAndLogin("jkurtz678@gmail.com", "12345") );
    }
    @Test
    public void createAndLoginUser_incorrectPassword() throws Exception {
        assertFalse( activity.createUserAndLogin("jkurtz678@gmail.com", "12346") );
    }

    @Test
    public void createAndLoginUser_nullLogin() throws Exception {
        assertFalse( activity.createUserAndLogin(null, "12345") );
    }

    @Test
    public void createAndLoginUser_nullPassword() throws Exception {
        assertFalse( activity.createUserAndLogin("jkurtz678@gmail.com", null) );
    }

    @Test
    public void createAndLoginUser_invalidEmail() throws Exception {
        assertFalse( activity.createUserAndLogin("jkurtz678", null) );
    }

    @Test
    public void createAndLoginUser_badPasswordLength() throws Exception {
        assertFalse( activity.createUserAndLogin( "jkurtz678@gmail.com", "123"));
    }

}
