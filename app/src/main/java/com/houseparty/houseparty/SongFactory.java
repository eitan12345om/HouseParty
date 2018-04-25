package com.houseparty.houseparty;

import android.util.Log;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * @author Nathan Boyd april 12 2018
 */


public class SongFactory {

    private static SongFactory instance;

    private Song song;

    private SongFactory() {
    }

    public static SongFactory getInstance() {
        if (instance == null) {
            instance = new SongFactory();
        }
        return instance;

    }

    public Song createSong(final String title, SpotifyService spotify, String api) {
        String songUri = "";

        if ("spotify".equals(api)) {

            spotifySearchForTrack(title, new AsyncCallback() {
                @Override
                public void onSuccess(String uri) {
                    Log.i("SongActivity", "this is the uri: " + uri);
                    song = new SpotifySong(title, uri);
                }
            }, spotify);
        }
        if ("soundcloud".equals(api)) {
            String uri = "";
            song = new SoundCloudSong(title, uri);
        }
        if ("tidal".equals(api)) {
            String uri = "";
            song = new TidalSong(title, uri);
        }
        if ("googleplay".equals(api)) {
            String uri = "";
            song = new GooglePlaySong(title, uri);
        }

        return song;
    }
}
