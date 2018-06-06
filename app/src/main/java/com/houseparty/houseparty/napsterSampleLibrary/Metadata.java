package com.houseparty.houseparty.napsterSampleLibrary;

import retrofit.Callback;
import retrofit.RestAdapter;


public class Metadata {

    TrackService trackService;
    String apiKey;

    public Metadata(String apiKey) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://api.napster.com").build();
        trackService = adapter.create(TrackService.class);
        this.apiKey = apiKey;
    }

    public void getTopTracks(int limit, int offset, Callback<Tracks> callback) {
        trackService.getTopTracks(apiKey, limit, offset, callback);
    }

    public void queryTrack( String query,  Callback<Search> callback ) {
        trackService.queryTrack( apiKey, 1, query, callback);
    }

    public TrackService getTrackService() {
        return trackService;
    }

}
