package com.houseparty.houseparty.activity_unit_tests;

import android.view.View;
import android.widget.Button;

import com.houseparty.houseparty.LoginActivity;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Eitan created on 5/14/2018.
 */
public class LoginActivityUnitTests {
    private LoginActivity loginActivity;

    @Before
    public void setUp() {
        loginActivity = new LoginActivity();
    }

    @Test(expected = NullPointerException.class)
    public void testOnClick() {
        View view = new Button(null);
        loginActivity.onClick(null);
    }
}
