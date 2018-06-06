Feature: Search for Napster songs
    Background:
        Given I want to add a Napster song to a playlist
    Scenario: Song is not found on Napster
        When I enter a search query for a song that is not available on Napster
        And I click the search button
        Then a message should be displayed informing me that song is not available
        And the search bar should be cleared
    Scenario: Song is successfully found
        When I enter a search query for a song that is available on Napster
        And I click the search button
        Then a list of matching songs should be shown to me