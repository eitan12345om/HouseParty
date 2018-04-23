Feature: Search for Spotify songs
    Background:
        Given I want to add a Spotify song to a playlist
    Scenario: Song is not found on Spotify
        When I enter a search query for a song that is not available on Spotify
        And I click the search button
        Then a message should be displayed informing me that song is not available
        And the search bar should be cleared
    Scenario: Song is successfully found
        When I enter a search query for a song that is available on Spotify
        And I click the search button
        Then a list of matching songs should be shown to me