package com.houseparty.houseparty;

//import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Eitan created on 6/1/2018.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTextTest {
//    @Rule
//    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void validateEmailAddress() {
        onView(withId(R.id.editText)).perform(typeText("example@email.com")).check(matches(withText("example@email.com")));
    }

    @Test
    public void validatePassword() {
        onView(withId(R.id.editText2)).perform(typeText("password")).check(matches(withText("password")));
    }
}
