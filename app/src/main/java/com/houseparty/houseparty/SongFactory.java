package com.houseparty.houseparty;

import android.util.Log;

import kaaes.spotify.webapi.android.SpotifyService;
import com.spotify.sdk.android.player.SpotifyPlayer;

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

    public Song createSong(final String title, SpotifyService spotify) {
        String songUri = "";

            Facade.searchForTrack(title, new Facade.AsyncCallback() {
                @Override
                public void onSuccess(String uri) {
                    Log.i("SongActivity", "this is the uri: " + uri);
                    song = new SpotifySong(title, uri);
                }
            }, spotify);
            return song;
    }

   /* public Song createSong(final String title, SoundcloudService soundcloud) {
            String uri = Facade.searchForTrack(title, soundcloud);
            song = new SoundCloudSong(title, uri);
            return song;
        }

    public Song createSong(final String title, TidalService tidal) {

        String uri = Facade.searchForTrack(title, tidal);;
        song = new TidalSong(title, uri);
        return song;
    }

    public Song createSong(final String title, GooglePlayService googleplay) {

            String uri = Facade.searchForTrack(title, googleplay);;
            song = new GooglePlaySong(title, uri);
            return song;
    }*/

}
