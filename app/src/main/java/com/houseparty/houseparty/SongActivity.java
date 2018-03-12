package com.houseparty.houseparty;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SongActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private ListView listView;
    private List<String> list;
    private ListAdapter adapter;
    private MediaPlayer mediaPlayer;

    private String CLIENT_ID;
    private String REDIRECT_URI;
    private int REQUEST_CODE;
    private String accessToken;
    private SpotifyPlayer spotifyPlayer;
    private SpotifyService spotify;

//    private class FetchSongTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Log.d("FetchSongTask", "AsyncTask launched, fetching song.");
//            return spotifySearchForTrack(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.d("FetchSongTask", "onPostExecute: result = " + result);
//            if (!spotifyPlayer.isLoggedIn()) {
//                Log.d("FetchSongTask", "Logging in spotifyPlayer.");
//                spotifyPlayer.login(accessToken);
//            }
//            Log.d("FetchSongTask", "Streaming URI " + result);
//            spotifyPlayer.playUri(null, result, 0, 0);
//        }
//
//        private String spotifySearchForTrack(String query) {
//
//            query = query.replaceAll(" ", "+");
//
//            Map<String,Object> options = new HashMap<String,Object>();
//            //options.put("Authorization", accessToken);
//            options.put("market", "US");
//            options.put("limit", 20);
//
//            final String[] songUri = {""};
//            spotify.searchTracks(query, options, new Callback<TracksPager>() {
//                @Override
//                public void success(TracksPager tracksPager, Response response) {
//                    List<Track> searchResults = tracksPager.tracks.items;
//                    songUri[0] = searchResults.get(0).uri;
//                    Log.d("FetchSongTask", "1st song uri = " + songUri[0]);
//                }
//                @Override
//                public void failure(RetrofitError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            return songUri[0];
//
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle("Song Menu");

        Bundle extras = getIntent().getExtras();
        CLIENT_ID = extras.getString("CLIENT_ID");
        Log.d("SongActivity", "CLIENT_ID = " + CLIENT_ID);
        REDIRECT_URI = extras.getString("REDIRECT_URI");
        Log.d("SongActivity", "REDIRECT_URI = " + REDIRECT_URI);
        REQUEST_CODE = extras.getInt("REQUEST_CODE");
        Log.d("SongActivity", "REQUEST_CODE = " + REQUEST_CODE);

        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for (Field f : fields)
            list.add(f.getName());
        list.add("Headlines");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mediaPlayer != null)
                    mediaPlayer.release();
                if (list.get(i).equals("Headlines"))
                    authenticateUser();
                else {
                    int resID = getResources().getIdentifier(list.get(i), "raw", getPackageName());
                    mediaPlayer = MediaPlayer.create(SongActivity.this, resID);
                    mediaPlayer.start();
                }
            }
        });
    }

    String spotifySearchForTrack(String query) {

        query = query.replaceAll(" ", "+");

        Map<String,Object> options = new HashMap<String,Object>();
        //options.put("Authorization", accessToken);
        options.put("market", "US");
        options.put("limit", 20);

        final String[] songUri = {""};
        spotify.searchTracks(query, options, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                List<Track> searchResults = tracksPager.tracks.items;
                songUri[0] = searchResults.get(0).uri;
                Log.d("FetchSongTask", "1st song uri = " + songUri[0]);
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        return songUri[0];

    }

    void authenticateUser() {

        // ---------USER AUTHENTICATION----------

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-modify-public",
                "playlist-modify-private", "playlist-read-collaborative", "user-library-read",
                "user-library-modify", "user-read-playback-state", "user-modify-playback-state",
                "user-read-currently-playing"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(SongActivity.this, REQUEST_CODE, request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                accessToken = response.getAccessToken();
                Config playerConfig = new Config(SongActivity.this, accessToken, CLIENT_ID);
                Spotify.getPlayer(playerConfig, SongActivity.this, new SpotifyPlayer.InitializationObserver(){
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        SongActivity.this.spotifyPlayer = spotifyPlayer;
                        Log.d("SongActivity", "spotifyPlayer initialized.");
                        SongActivity.this.spotifyPlayer.addConnectionStateCallback(SongActivity.this);
                        SongActivity.this.spotifyPlayer.addNotificationCallback(SongActivity.this);
                        Log.d("SongActivity", "spotifyPlayer: callbacks added.");
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("SongActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("SongActivity", "User authenticated.");
        SpotifyApi wrapper = new SpotifyApi();
        wrapper.setAccessToken(accessToken);
        spotify = wrapper.getService();
        final String songUri = spotifySearchForTrack("Headlines");
        spotifyPlayer.playUri(null, songUri, 0, 0);
    }

    @Override
    public void onLoggedOut() {
        Log.d("SongActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.e("SongActivity", "Login failed");
    }

    @Override
    protected void onDestroy() {
        // --------SUPER IMPORTANT TO AVOID LEAKAGE----------
        Spotify.destroyPlayer(SongActivity.this);
        spotifyPlayer.destroy();
        super.onDestroy();
    }

    @Override
    public void onTemporaryError() {
        Log.d("SongActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("SongActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("SongActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("SongActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

}
