package com.houseparty.houseparty.features;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Eitan created on 4/23/2018.
 */
public class Register {
    @When("^I enter a password that doesn’t contain a special character$")
    public void I_entered_a_password_not_containing_a_special_character() throws Throwable {
        throw new PendingException();
    }

    @And("^password is of incorrect length (less than 8 or more than 14)$")
    public void password_is_of_incorrect_length() throws Throwable {
        throw new PendingException();
    }

    @And("^the email address is correct$")
    public void email_address_is_correct() throws Throwable {
        throw new PendingException();
    }

    @Then("^a message should appear indicating the correct password format$")
    public void a_message_should_appear_indicating_correct_password_format() throws Throwable {
        throw new PendingException();
    }

    @And("^the registration form should be re-displayed$")
    public void registration_form_should_be_redisplayed() throws Throwable {
        throw new PendingException();
    }
}
