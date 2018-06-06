Feature: Delete playlist from playlist activity

    Background:
        Given I want to delete a playlist from playlist activity
    Scenario: Successfully delete playlist
        When I swipe to delete a playlist
        And I am the creator of the playlist
        Then the playlist will turn red
        And disappear from the list of playlists
        And the playlist will be deleted from firebase
    Scenario: Cannot delete playlist because not a host
        When I swipe to delete a playlist
        And I am not the creator of the playlist
        Then a dialogue box will appear informing me that I am not the host