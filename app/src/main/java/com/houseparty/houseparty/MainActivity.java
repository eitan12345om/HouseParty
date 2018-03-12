package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private ListView listView;
    private List<String> list;
    private ListAdapter adapter;
    private String playlist_name = "";
    private String title = "HouseParty";
    private static String selected_list;

    // Required constants for Spotify API connection.
    static final String CLIENT_ID = "4c6b32bf19e4481abdcfbe77ab6e46c0";
    static final String REDIRECT_URI = "houseparty-android://callback";

    // Used to verify if Spotify results come from correct activity.
    static final int REQUEST_CODE = 777;

    // Access token to be retrieved for the current Spotify connection session.
//    private String accessToken;

    // Object through which we access the Spotify Web API Wrapper.
//    private SpotifyService spotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(title);

        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<>();

//        authenticateUser();

        for(int i = 0; i < 20; i++){
            list.add("Playlist_" + i);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_list = list.get(i);
                Intent intent = new Intent(MainActivity.this, SongActivity.class);
                intent.putExtra("CLIENT_ID", CLIENT_ID);
                intent.putExtra("REDIRECT_URI", REDIRECT_URI);
                intent.putExtra("REQUEST_CODE", REQUEST_CODE);
//                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialogueBox_Playlist(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Playlist Name:");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playlist_name = input.getText().toString();
                if (playlist_name.isEmpty()) {
                    dialog.cancel();
                } else {
                    selected_list = playlist_name;
                    Intent intent = new Intent(getBaseContext(), SongActivity.class);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public static String selection() {
        return selected_list;
    }

//    void authenticateUser() {
//
//        // ---------USER AUTHENTICATION----------
//
//        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
//                AuthenticationResponse.Type.TOKEN,
//                REDIRECT_URI);
//        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-modify-public",
//                "playlist-modify-private", "playlist-read-collaborative", "user-library-read",
//                "user-library-modify", "user-read-playback-state", "user-modify-playback-state",
//                "user-read-currently-playing"});
//        AuthenticationRequest request = builder.build();
//
//        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

//        // Check if result comes from the correct activity
//        if (requestCode == REQUEST_CODE) {
//            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
//            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
//                accessToken = response.getAccessToken();
//            }
//        }
    }

    @Override
    public void onLoggedIn() {

        Log.d("MainActivity", "User logged in");

//        SpotifyApi wrapper = new SpotifyApi();
//        wrapper.setAccessToken(accessToken);
//
//        spotify = wrapper.getService();

    }

    @Override
    public void onLoggedOut() {}

    @Override
    public void onLoginFailed(Error error) {}

    @Override
    public void onTemporaryError() {}

    @Override
    public void onConnectionMessage(String s) {}

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {}

    @Override
    public void onPlaybackError(Error error) {}

}
