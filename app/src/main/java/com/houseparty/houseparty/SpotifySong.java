package com.houseparty.houseparty;

import android.util.Log;

import static com.houseparty.houseparty.NewSongActivity.spotifyPlayer;

/**
 * @author hanzy created on 4/11/2018.
 */
public class SpotifySong extends Song {

    protected String accessToken;

    public SpotifySong() {
        super();
    }

    public SpotifySong(String title, String uri, String artist) {
        super(title, uri, artist);
    }

    public SpotifySong(String title, String uri, String artist, String token, String coverArtUrl) {
        super(title, uri, artist, coverArtUrl);
        this.accessToken = token;
    }

    @Override
    public void playSong() {
        if (!NewSongActivity.spotifyPlayer.isLoggedIn()) {
            Log.d("playSong", "Logging in spotifyPlayer.");
            NewSongActivity.spotifyPlayer.login(accessToken);
        }
        Log.d("playSong", "spotifyPlayer is logged in.");
        NewSongActivity.spotifyPlayer.playUri(null, super.uri, 0, 0);
    }

    public String getAccessToken() {
        return accessToken;
    }
}
