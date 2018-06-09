package com.houseparty.houseparty;

public class AuthenticateLoginIntegrationTest {
    private LoginActivity activity;

    @Before
    public void setup() {
        activity = new LoginActivity();
        activity.register("jkurtz678@gmail.com", "12345");
    }

    @Test
    public void testCorrect() {
        assertTrue(activity.authenticateLogin("jkurtz678@gmail.com", "12345"));
    }

    @Test
    public void testBothNull() {
        assertFalse(activity.authenticateLogin(null, null));
    }

    @Test
    public void testUsernameNull() {
        assertFalse(activity.authenticateLogin(null, "12345"));
    }

    @Test
    public void testUsernameNull_IncorrectPassword() {
        assertFalse(activity.authenticateLogin(null, "123456"));
    }

    @Test
    public void testPasswordNull() {
        assertFalse(activity.authenticateLogin("jkurtz678@gmail.com", null));
    }

    @Test
    public void testPasswordNull_IncorrectUsername() {
        assertFalse(activity.authenticateLogin("invalid@gmail.com", null));
    }

    @Test
    public void testBothIncorrect() {
        assertFalse(activity.authenticateLogin("invalid@gmail.com", "123456"));
    }

    @Test
    public void testIncorrectPassword() {
        assertFalse(activity.authenticateLogin("jkurtz678@gmail.com", "123456"));
    }

    @Test
    public void testIncorrectUsername() {
        assertFalse(activity.authenticateLogin("invalid@gmail.com", "1234"));
    }
}