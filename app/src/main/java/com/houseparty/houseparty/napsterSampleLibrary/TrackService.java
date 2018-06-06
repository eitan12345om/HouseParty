package com.houseparty.houseparty.napsterSampleLibrary;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TrackService {

    @GET("/v2.0/tracks/top")
    public void getTopTracks(
            @Query(Constants.APIKEY) String apikey,
            @Query(Constants.LIMIT) int limit,
            @Query(Constants.OFFSET) int offset,
            Callback<Tracks> callback);


    @GET("/v2.0/search" )
    public void queryTrack(
            @Query(Constants.APIKEY) String apikey,
            @Query("limit") int limit,
            @Query("q") String q,
            //@Query("type") String type,
            Callback<Search> callback);

    @GET("/v2.0/me/listens")
    public void getListeningHistory(
            @Header(Constants.AUTHORIZAION) String authorization,
            Callback<Tracks> callback);
}
