package com.houseparty.houseparty;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Eitan created on 6/8/2018.
 */
@RunWith(AndroidJUnit4.class)
public class DeletePlaylistTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void deletePlaylist() {
        onView(withId(R.id.playlist1)).perform(swipeLeft()).check(View.visibility("Gone"));
    }

    @Test
    public void clickPlaylist() {
        onView(withId(R.id.playlist2)).click().checkContext(matches(BuilderDialog.this));
    }

    @Test
    public void logOut() {
        onView(withId(R.id.action_settings)).click(R.id.logout).checkContext(matches(LoginActivity.this));
    }

    @Test
    public void addPlaylist() {
        int original = onView(playlists.count);
        onView(withId(R.id.fab)).click.submit().countPlaylists(matches(original + 1))
    }
}
