Feature: Set a password for a playlist
  Background:
    Given I want to make a new playlist
    And I want to lock the playlist with a password
  Scenario: Valid password entered
    When I enter a 4 digit password
    And I click the confirmation button
    Then a playlist will be created with password protection
    And the playlist menu should be re-displayed
  Scenario: Invalid password entered
    When I enter a password that is anything other than 4 digits
    Then playlist creation fails
    And an error message is displayed