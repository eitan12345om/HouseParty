package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

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

public class NewSongActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    private boolean pause = true;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private String selectedList;
    private ArrayList<Song> songs;
    private static String songName;

    private static final String CLIENT_ID = "4c6b32bf19e4481abdcfbe77ab6e46c0";
    private static final String REDIRECT_URI = "houseparty-android://callback";
    private static final int REQUEST_CODE = 777;

    private String accessToken;
    private SpotifyPlayer spotifyPlayer;
    private SpotifyService spotify;

    private String host;
    private FirebaseUser iam;  /* Variable to keep track of who I is */

    private FirebaseDatabase sFirebaseDatabase;
    private DatabaseReference songDatabaseReference;
    private ChildEventListener sChildEventListener;

    private interface AsyncCallback {
        void onSuccess(String uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_song);

        setUpButton();
        setUpAdapter();

        sFirebaseDatabase = FirebaseDatabase.getInstance();
        iam = FirebaseAuth.getInstance().getCurrentUser();
        host = getIntent().getExtras().getString("HOST");
        Map<String, String> t = PlaylistActivity.getIdTable();
        String id = t.get(PlaylistActivity.selection());
        songDatabaseReference = sFirebaseDatabase.getReference().child("playlists").child(id).child("songs");

        authenticateUser();

        sChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> dataTable = (HashMap) dataSnapshot.getValue();

                /* TODO: Use SongFactory */
                songs.add(new SpotifySong(
                    dataTable.get("title"),
                    dataTable.get("uri"),
                    dataTable.get("artist"), accessToken, spotifyPlayer
                ));

                /* TODO: This is bad practice. Be more specific */
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                /* TODO:
                 *   I'm not sure how to approach this method...
                 *   We can either store an ID along with every playlist and
                 *   loop through each playlist in the list and determine which
                 *   it was.
                 *   OR
                 *   We can reload the entire list from Firebase... But, I'm not
                 *   sure Firebase even lets you do that.
                 *   Any ideas?
                 */
                Log.i("SongActivity", "Child Changed!");

                /* TODO: This is bad practice. Be more specific */
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //HashMap<String, String> dataTable = (HashMap) dataSnapshot.getValue();

                /* TODO: Use SongFactory */
                /*
                songs.remove(new SpotifySong(
                    dataTable.get("title"),
                    dataTable.get("uri"),
                    dataTable.get("artist")
                ));*/

                /* TODO: This is bad practice. Be more specific */
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new UnsupportedOperationException();
            }
        };
        songDatabaseReference.addChildEventListener(sChildEventListener);

        String message = "You are not the host of this playlist";
        if (iam.getUid().equals(host)) {
            message = "You are the host of this playlist";
        }

        Log.d("TAG", host + ", iam: " + iam.getUid());

        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void setUpButton() {
        pause = false;
        final Button myButton = findViewById(R.id.button3);
        myButton.setText("Play");
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pause) {
                    myButton.setText("Pause");
                    pause = true;
                } else {
                    myButton.setText("Play");
                    pause = false;
                }
            }
        });
    }

    public void dialogueBoxSong(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Song Name:");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                songName = input.getText().toString();
                Log.d("SongActivity: ", songName);
                if (songName.isEmpty()) {
                    dialog.cancel();
                } else {
                    spotifySearchForTrack(songName, new NewSongActivity.AsyncCallback() {
                        @Override
                        public void onSuccess(String uri) {
                            Log.i("SongActivity", "this is the uri: " + uri);
                            Song song = new SpotifySong(songName, uri, null, accessToken, spotifyPlayer);
                            //Song song = songFactory.createSong(songName, spotify, "spotify");
                            songDatabaseReference.push().setValue(song);
                            Log.i("SongActivity", "This is the song name: " + song.title);

                        }
                    });
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

    boolean spotifySearchForTrack(String query, final NewSongActivity.AsyncCallback callback) {

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

    boolean authenticateUser() {
        // ---------USER AUTHENTICATION----------

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-modify-public",
                "playlist-modify-private", "playlist-read-collaborative", "user-library-read",
                "user-library-modify", "user-read-playback-state", "user-modify-playback-state",
                "user-read-currently-playing"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(NewSongActivity.this, REQUEST_CODE, request);

        return true;

    }

    public void setUpAdapter() {
        recyclerView = findViewById(R.id.recyclerView);
        songs = new ArrayList<>();

        adapter = new SongAdapter(songs);
        adapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Song song = songs.get(position);
                Log.d( "NewSongActivity", song.getUri());
                authenticateUser();
                song.playSong();
                Snackbar.make(view, "Now playing: " + song.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getParent(), 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                accessToken = response.getAccessToken();
                Config playerConfig = new Config(NewSongActivity.this, accessToken, CLIENT_ID);
                Spotify.getPlayer(playerConfig, NewSongActivity.this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        NewSongActivity.this.spotifyPlayer = spotifyPlayer;
                        Log.d("SongActivity", "spotifyPlayer initialized.");
                        NewSongActivity.this.spotifyPlayer.addConnectionStateCallback(NewSongActivity.this);
                        NewSongActivity.this.spotifyPlayer.addNotificationCallback(NewSongActivity.this);
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

    boolean testSearchForTrack() {
        try {
            spotifySearchForTrack("Headlines", new NewSongActivity.AsyncCallback() {
                @Override
                public void onSuccess(String uri) {
                    Log.d("SearchTest", "Received uri = " + uri);
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean testOnLoggedIn() {
        try {
            onLoggedIn();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean testOnLoggedOut() {
        try {
            onLoggedOut();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean testOnDestroy() {
        try {
            onDestroy();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("SongActivity", "User authenticated.");
        SpotifyApi wrapper = new SpotifyApi();
        wrapper.setAccessToken(accessToken);
        spotify = wrapper.getService();
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
        Spotify.destroyPlayer(NewSongActivity.this);

        if (spotifyPlayer != null) {
            spotifyPlayer.destroy();
        }

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