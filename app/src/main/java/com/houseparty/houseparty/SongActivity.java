package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.Hashtable;
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
    private List<String> displayList;
    private ArrayAdapter adapter;
    private MediaPlayer mediaPlayer;
    private String title = "HouseParty - ";
    private static String song_name;

    private FirebaseDatabase sFirebaseDatabase;
    private DatabaseReference songDatabaseReference;
    private ChildEventListener sChildEventListener;

    private final String CLIENT_ID = "4c6b32bf19e4481abdcfbe77ab6e46c0";
    private final String REDIRECT_URI = "houseparty-android://callback";
    private final int REQUEST_CODE = 777;
    private String accessToken;
    private SpotifyPlayer spotifyPlayer;
    private SpotifyService spotify;
    private static Hashtable<String, String> uriTable;
    private ArrayList<Song> songs;

    private SongFactory songFactory;

    private interface AsyncCallback {
        void onSuccess(String uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        songs = new ArrayList<Song>();

        sFirebaseDatabase = FirebaseDatabase.getInstance();
        Hashtable<String, String> t = MainActivity.getIdTable();
        String id = t.get(MainActivity.selection());
        songDatabaseReference = sFirebaseDatabase.getReference().child("playlists").child(id).child("songs");
        uriTable = new Hashtable<>();

        authenticateUser();
        songFactory = SongFactory.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(title + MainActivity.selection());

        // https://stackoverflow.com/questions/10674388/nullpointerexception-from-getextras
        Bundle extras = getIntent().getExtras();

        listView = findViewById(R.id.listView);

        displayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //authenticateUser();
                if (mediaPlayer != null)
                    mediaPlayer.release();
                else {
                    //int resID = getResources().getIdentifier(list.get(i), "raw", getPackageName());
                    //mediaPlayer = MediaPlayer.create(SongActivity.this, resID);
                    //mediaPlayer.start();
                    String uri = uriTable.get(displayList.get(i));
                    Log.d("GETURI: ", uri + "END of URI");
                    //songs.get(i).playSong();
                    spotifyPlayer.playUri(null, songs.get(i).uri, 0, 0);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogueBox_Song(view);
            }
        });

        sChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Size of list is: " + displayList.size());
                SpotifySong song = dataSnapshot.getValue(SpotifySong.class);
                songs.add(song);
                uriTable.put(song.getTitle(), song.getUri());
                displayList.add(song.getTitle());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        songDatabaseReference.addChildEventListener(sChildEventListener);

    }

    public void dialogueBox_Song(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Song Name:");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                song_name = input.getText().toString();
                Log.d("SongActivity: ", song_name);
                if (song_name.isEmpty()) {
                    dialog.cancel();
                } else {
                    spotifySearchForTrack(song_name, new AsyncCallback() {
                        @Override
                        public void onSuccess(String uri) {
                            Log.i("SongActivity", "this is the uri: " + uri);
                            Song song = new SpotifySong(song_name, uri);
                            //Song song = songFactory.createSong(song_name, spotify, "spotify");
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

    boolean spotifySearchForTrack(String query, final AsyncCallback callback) {

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

        AuthenticationClient.openLoginActivity(SongActivity.this, REQUEST_CODE, request);

        return true;

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
                Spotify.getPlayer(playerConfig, SongActivity.this, new SpotifyPlayer.InitializationObserver() {
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

    boolean testSearchForTrack() {
        try {
            spotifySearchForTrack("Headlines", new AsyncCallback() {
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
        Spotify.destroyPlayer(SongActivity.this);

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
