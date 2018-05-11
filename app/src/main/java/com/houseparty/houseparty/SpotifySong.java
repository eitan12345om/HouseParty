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

    protected String accessToken;
    protected SpotifyPlayer spotifyPlayer;

    public SpotifySong() {
        super();
    }

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

    public String getAccessToken() {
        return accessToken;
    }

    public SpotifyPlayer getSpotifyPlayer() {
        return spotifyPlayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        SpotifySong spotifySong = (SpotifySong) obj;
        return super.equals(obj)
            && accessToken.equals(spotifySong.accessToken);
    }

    @Override
    public void onLoggedIn() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onLoggedOut() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onLoginFailed(Error error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onTemporaryError() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onConnectionMessage(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onPlaybackError(Error error) {
        throw new UnsupportedOperationException();
    }
}
