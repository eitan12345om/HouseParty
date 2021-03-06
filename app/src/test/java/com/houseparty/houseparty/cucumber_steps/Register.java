package com.houseparty.houseparty.cucumber_steps;

import android.view.View;

import com.houseparty.houseparty.LoginActivity;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Eitan created on 4/23/2018.
 */
public class Register {
    LoginActivity loginActivity;
    @Given("^I want to register for a House Party account$")
    public void i_want_to_register_for_a_House_Party_account() {
        loginActivity = new LoginActivity();
        //throw new PendingException();
    }

    @When("^I sign up with valid information$")
    public void i_sign_up_with_valid_information() {
        loginActivity.dialogueBoxRegister(loginActivity.findViewById(android.R.id.content));
        //loginActivity.onCreate(null);
        //throw new PendingException();
    }

    @Then("^the account should be added to the Firebase server$")
    public void the_account_should_be_added_to_the_Firebase_server() {
        // Write code here that turns the phrase above into concrete actions
        FirebaseReference.getReference().contains(userLogin, "eitan.simler@gmail.com);
    }

    @Then("^the display will be updated to show the playlist page$")
    public void the_display_will_be_updated_to_show_the_playlist_page() {
        assertEquals(getContext(PlaylistPage.this).getRecyclerViewAdapter().count(), 1);
    }

    @When("^I enter an email_address with invalid characters or in an incorrect format$")
    public void i_enter_an_email_address_with_invalid_characters_or_in_an_incorrect_format() {
        assertNotEquals(getContext(PlaylistPage.this).getErrorBuffer(), null);
    }

    @When("^all the other details are correct$")
    public void all_the_other_details_are_correct() {
        assertEquals(getContext(PlaylistPage.this).getThreadActivity(), NO_ACTIVITY);
    }

    @When("^I click the register button$")
    public void i_click_the_register_button() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(getContext(PlaylistPage.this).getDialogBuilder().exists(), true);
    }

    @Then("^I should see text that indicates the valid email format$")
    public void i_should_see_text_that_indicates_the_valid_email_format() {
        assertEquals(getContext(PlaylistPage.this).getDialogBuilder().getText().toString(), "example@email.com");
    }

    @Then("^the registration form should be re-displayed$")
    public void the_registration_form_should_be_re_displayed() {
        assertEquals(getContext(LoginActivity.this).getDialogBuilder().getVisibility(), VISIBLE);
    }

    @When("^I enter a password that doesn’t contain a special character$")
    public void i_enter_a_password_that_doesn_t_contain_a_special_character() {
        assertEquals(getContext(PlaylistPage.this).getErrorBuffer(), "Wrong Password. Try Again");
    }

    @When("^password is of incorrect length \\(less than (\\d+) or more than (\\d+)\\)$")
    public void password_is_of_incorrect_length_less_than_or_more_than(int arg1, int arg2) {
        assertEquals(getContext(PlaylistPage.this).getErrorBuffer(), "Wrong Password. Try Again");
    }

    @When("^the email address is correct$")
    public void the_email_address_is_correct() {
        assertNotEquals(getContext(PlaylistPage.this).getErrorBuffer(), null);
    }

    @Then("^a message should appear indicating the correct password format$")
    public void a_message_should_appear_indicating_the_correct_password_format() {
        assertEquals(getContext(PlaylistPage.this).getErrorBuffer(), "Wrong Password. Try Again");
    }

    @When("^I enter an email address that has already been registered$")
    public void i_enter_an_email_address_that_has_already_been_registered() {
        assertEquals(getContext(PlaylistPage.this).getErrorBuffer(), "Username already taken. Try again");
    }

    @Then("^I should see text that indicates that the account already exists$")
    public void i_should_see_text_that_indicates_that_the_account_already_exists() {
        assertEquals(getContext(PlaylistPage.this).getErrorBuffer().isDisplayed(), true);
    }
}
