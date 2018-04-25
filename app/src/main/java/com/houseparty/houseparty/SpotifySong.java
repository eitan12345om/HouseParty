package com.houseparty.houseparty;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * @author hanzy created on 4/11/2018.
 */
public class SpotifySong extends Song
        implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    protected SpotifyPlayer spotifyPlayer;
    protected String accessToken;

    public SpotifySong(String title, String uri) {
        super(title, uri);
    }

    public SpotifySong(String title, String uri, String token, SpotifyPlayer sp) {

        super(title, uri);
        this.accessToken = token;
        this.spotifyPlayer = sp;

    }

    /* TODO */
    public int playSong() {
        spotifyPlayer.playUri(null, uri, 0, 0);
        return 1;
    }


    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }
}
