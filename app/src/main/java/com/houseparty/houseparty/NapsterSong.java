package com.houseparty.houseparty;

import android.util.Log;

import com.napster.cedar.player.data.Track;

/**
 * Created by jacksonkurtz on 6/5/18.
 */

public class NapsterSong extends Song {

    protected Track track;

    public NapsterSong() {
        super();
    }

    public NapsterSong(String title, String uri, String artist, Track track, String coverArtUrl) {
        super(title, uri, artist, coverArtUrl);
        this.track = track;
    }

    @Override
    public void playSong() {
        /*if (!NewSongActivity.spotifyPlayer.isLoggedIn()) {
            Log.d("playSong", "Logging in spotifyPlayer.");
            NewSongActivity.napsterPlayer.login(accessToken);
        }*/
        //Log.d("playSong", "spotifyPlayer is logged in.");
        NewSongActivity.napsterPlayer.play(track);
    }

}
