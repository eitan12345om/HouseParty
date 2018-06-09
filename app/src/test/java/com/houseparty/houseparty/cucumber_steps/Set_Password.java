package com.houseparty.houseparty.cucumber_steps;

import com.houseparty.houseparty.LoginActivity;
import com.houseparty.houseparty.PlaylistActivity;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by jacksonkurtz on 6/8/18.
 */

public class Set_Password {
    PlaylistActivity playlistActivity;
    boolean createPlaylistSuccess;

    @Given("^I want to make a new playlist$")
    public void i_want_to_make_a_new_playlist() {
        playlistActivity = new PlaylistActivity();
    }

    @When("^I want to lock the playlist with a password$")
    public void i_want_to_lock_the_playlist_with_a_password() {
        assert(true);
    }

    @When("^I enter a 4 digit password$")
    public void i_enter_a_4_digit_password() {
        playlistActivity.createPlaylist("NewPlaylist", "1111");
    }

    @When("^I click the confirmation button$")
    public void i_click_the_confirmation_button() {
        assert(true);
    }

    @Then("^a playlist will be created with password protection$")
    public void a_playlist_will_be_be_created_with_password_protection() {
        assert(playlistActivity.selectPlaylist(playlistActivity.playlists.get(playlistActivity.playlists.size()), "1111") );
    }

    @Then("^the playlist menu should be re-displayed$")
    public void the_playlist_menu_should_be_redisplayed() {
        assert(true);
    }

    @When("^I enter a password that is anything other than 4 digits$")
    public void i_enter_a_password_that_is_anything_other_than_4_digits() {
        createPlaylistSuccess = playlistActivity.createPlaylist("NewPlaylist", "11111");
    }

    @Then("^playlist creation fails$")
    public void playlist_creation_fails() {
        assert(createPlaylistSuccess);
    }

    @Then("^an error message is displayed$")
    public void an_error_message_is_displayed() {
        assert(true);
    }

}
