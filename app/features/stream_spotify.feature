Feature: Stream songs from Spotify
    Background:
        Given I want to play a particular song in a playlist
        And the song is available on Spotify
        And I am authenticated with Spotify
    Scenario: Successfully stream song
        When I click to start playing a song
        Then the song will start streaming from Spotify
    Scenario: Internet connection is interrupted
        When the internet connection is interrupted
        Then the stream will freeze
        And you will be returned to the previous screen
        And an error message will be displayed
    Scenario: The song no longer exists in the playlist
        When I choose to play a song that has been deleted from the playlist
        Then an error message will be shown