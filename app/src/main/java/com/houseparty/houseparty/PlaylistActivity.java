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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistActivity extends AppCompatActivity implements
    SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private ListView listView;
    private List<String> list;
    private ArrayAdapter adapter;
    private ActionBar actionBar;
    private static HashMap<String, String> idTable;
    private String code = "";
    private View currentView;
    private FirebaseUser currentFirebaseUser;

    private String title = "HouseParty";
    private static String selectedList;
    private HashMap<String, String> dataTable;
    private String playlistName = "";
    private static String passcode;
    private FirebaseDatabase pFirebaseDatabase;
    private DatabaseReference pPlaylistDatabaseReference;
    private ChildEventListener pChildEventListener;

    // Required constants for Spotify API connection.
    static final String CLIENT_ID = "4c6b32bf19e4481abdcfbe77ab6e46c0";
    static final String REDIRECT_URI = "houseparty-android://callback";

    // Used to verify if Spotify results come from correct activity.
    static final int REQUEST_CODE = 777;

    public static String selection() {
        return selectedList;
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

    public static Map<String, String> getIdTable() {
        return idTable;
    }

    public void dialogueBoxInvalidPasscode(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid passcode");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void dialogueBoxPasscode(View v, final String valid_code) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter passcode:");
        final EditText inputPasscode = new EditText(this);
        inputPasscode.setHint("XXXX");
        layout.addView(inputPasscode);
        inputPasscode.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(layout);
        final View thisView = v;

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredCode = inputPasscode.getText().toString();
                if (!enteredCode.equals(valid_code)) {
                    dialogueBoxInvalidPasscode(thisView);
                    dialog.cancel();
                } else {
                    Intent intent = new Intent(PlaylistActivity.this, NewSongActivity.class);
                    intent.putExtra("CLIENT_ID", CLIENT_ID);
                    intent.putExtra("REDIRECT_URI", REDIRECT_URI);
                    intent.putExtra("REQUEST_CODE", REQUEST_CODE);
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

    public void dialogueBoxPlaylist(View v) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Playlist Name:");

        final EditText inputTitle = new EditText(this);
        inputTitle.setHint("Title");
        layout.addView(inputTitle);
        final EditText inputPasscode = new EditText(this);
        inputPasscode.setHint("Passcode (XXXX)");
        layout.addView(inputPasscode);

        inputPasscode.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playlistName = inputTitle.getText().toString();
                code = inputPasscode.getText().toString();
                Log.d("NONNUMBERCHECK", Boolean.toString(code.matches("\\d+")));
                if (playlistName.isEmpty() ||
                    !code.matches("\\d+")
                    || code.length() != 4) {
                    dialog.cancel();
                } else {
                    selectedList = playlistName;
                    passcode = code;
                    /* FIXME */
                    Playlist plist = new Playlist(selectedList, passcode, currentFirebaseUser.getUid());
                    pPlaylistDatabaseReference.push().setValue(plist);
                    Intent intent = new Intent(getBaseContext(), NewSongActivity.class);
                    intent.putExtra("CLIENT_ID", CLIENT_ID);
                    intent.putExtra("REDIRECT_URI", REDIRECT_URI);
                    intent.putExtra("REQUEST_CODE", REQUEST_CODE);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pFirebaseDatabase = FirebaseDatabase.getInstance();
        pFirebaseDatabase.setPersistenceEnabled(true);
        pPlaylistDatabaseReference = pFirebaseDatabase.getReference().child("playlists");
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser == null) {
            Log.d("PlaylistActivity", "User not authenticated");
        }

        idTable = new HashMap<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        actionBar.setTitle(title);

        Log.d("Main Activity: ", "On create");

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        actionBar.show();

        pChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataTable = (HashMap) dataSnapshot.getValue();
                idTable.put(dataTable.get("name"), dataSnapshot.getKey());
                list.add(dataTable.get("name"));
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
                Log.i("PlaylistActivity", "Child Changed!");
                Playlist playlist = dataSnapshot.getValue(Playlist.class);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataTable = (HashMap) dataSnapshot.getValue();
                list.remove(dataTable.get("name"));
                adapter.notifyDataSetChanged();
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
        pPlaylistDatabaseReference.addChildEventListener(pChildEventListener);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentView = view;
                selectedList = list.get(i);
                String id = idTable.get(selectedList);
                Log.d("ID FROM HASH", id);
                //dataTable.get("passcode")
                Query queryRef = pPlaylistDatabaseReference.child(id).child("passcode");
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            passcode = (String) dataSnapshot.getValue();
                            Log.d("Passcode of selected: ", passcode);
                            dialogueBoxPasscode(currentView, passcode);

                        } else {
                            Log.d("Snapshot", "does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw new UnsupportedOperationException();
                    }
                });
            }
        });
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Intent intent = new Intent(PlaylistActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(Error error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onTemporaryError() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onConnectionMessage(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onPlaybackError(Error error) {
        throw new UnsupportedOperationException();
    }
}
