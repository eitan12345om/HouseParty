Feature: Register for a House Party account

    Background:
        Given I want to register for a House Party account
    Scenario: Successful sign up
        When I sign up with valid information
        Then the account should be added to the Firebase server
        Then the display will be updated to show the playlist page
    Scenario: Invalid email address entered
        When I enter an email_address with invalid characters or in an incorrect format
        And all the other details are correct
        And I click the register button
        Then I should see text that indicates the valid email format
        Then the registration form should be re-displayed
    Scenario: Invalid password entered
        When I enter a password that doesn’t contain a special character
        And password is of incorrect length (less than 8 or more than 14)
        And the email address is correct
        Then a message should appear indicating the correct password format
        And the registration form should be re-displayed
    Scenario: Account already exists
        When I enter an email address that has already been registered
        And I click the register button
        Then I should see text that indicates that the account already exists
        And the registration form should be re-displayed