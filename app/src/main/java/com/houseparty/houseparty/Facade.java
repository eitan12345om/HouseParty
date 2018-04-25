package com.houseparty.houseparty;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Eitan created on 4/25/2018.
 */
public class Facade {
    private interface AsyncCallback {
        void onSuccess(String uri);
    }

    public boolean spotifySearchForTrack(String query, final SongFactory.AsyncCallback callback, SpotifyService spotify) {

        query = query.replaceAll(" ", "+");


        Map<String, Object> options = new HashMap<String, Object>();
        //options.put("Authorization", accessToken);
        options.put("market", "US");
        options.put("limit", 20);
        try {
            spotify.searchTracks(query, options, new Callback<TracksPager>() {
                @Override
                public void success(TracksPager tracksPager, Response response) {
                    String songUri = "";
                    try {


                        List<Track> searchResults = tracksPager.tracks.items;
                        songUri = searchResults.get(0).uri;
                    } catch (Exception e) {
                        Log.d("SearchTracksInner: ", "No results");
                    }
                    Log.d("FetchSongTask", "1st song uri = " + songUri);
                    callback.onSuccess(songUri);
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });

        } catch (IndexOutOfBoundsException e) {
            Log.d("SEARCHTRACKS", "No results");
        }

        return true;
    }
}
