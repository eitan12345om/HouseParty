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
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private ListView listView;
    private List<String> list;
    private ArrayAdapter adapter;
    private ActionBar actionBar;
    private String playlist_name = "";
    private String code = "";
    private View currentView;

    private String title = "HouseParty";
    private static Hashtable<String, String> idTable;
    private HashMap<String, String> dataTable;
    //private static String selected_list;
    private static String selected_list;
    private static String passcode;
    private FirebaseDatabase pFirebaseDatabase;
    private DatabaseReference pPlaylistDatabaseReference;
    private ChildEventListener pChildEventListener;

    private DatabaseReference passcodeDatabaseReference;

    private static MainActivity instance;

    // Required constants for Spotify API connection.
    static final String CLIENT_ID = "4c6b32bf19e4481abdcfbe77ab6e46c0";
    static final String REDIRECT_URI = "houseparty-android://callback";

    // Used to verify if Spotify results come from correct activity.
    static final int REQUEST_CODE = 777;

    /*
    private MainActivity() {}

    public static MainActivity getMainInstance(){
        if( instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pFirebaseDatabase = FirebaseDatabase.getInstance();
        pPlaylistDatabaseReference = pFirebaseDatabase.getReference().child("playlists");
        //pPlaylistDatabaseReference.keepSynced(true);
        idTable = new Hashtable<String, String>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        actionBar.setTitle(title);

        Log.d( "Main Activity: ", "On create");

        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<String>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        actionBar.show();

        pChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                dataTable = (HashMap) dataSnapshot.getValue();
                idTable.put(dataTable.get("name"), dataSnapshot.getKey());
                //System.out.println("Add to list: " + name);
                list.add(dataTable.get("name"));
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
        pPlaylistDatabaseReference.addChildEventListener(pChildEventListener);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentView = view;
                selected_list = list.get(i);
                String id = idTable.get(selected_list);
                Log.d("ID FROM HASH", id);
                //dataTable.get("passcode")
                Query queryRef = pPlaylistDatabaseReference.child(id).child("passcode");
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Log.d("Inside listener", (String) dataSnapshot.child("passcode").getValue());

                        if( dataSnapshot.exists()) {
                            passcode = (String)dataSnapshot.getValue();
                            Log.d("Passcode of selected: ", passcode);
                            dialogueBox_Passcode(currentView, passcode );

                        }
                        else {
                            Log.d("Snapshot", "does not exist");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //dialogueBox_Passcode(view, passcode );

                //passcodeDatabaseReference = pFirebaseDatabase.getReference().child("playlists").child(id);
                //Log.d( "ELEMENT FROM DATABASE", dataTable.get("passcode"));

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

    public void dialogueBox_InvalidPasscode(View v) {
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

    public void dialogueBox_Passcode(View v, final String valid_code) {
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
                String entered_code = inputPasscode.getText().toString();
                if (!entered_code.equals(valid_code)) {
                    dialogueBox_InvalidPasscode(thisView);
                    dialog.cancel();
                } else {
                    Intent intent = new Intent(MainActivity.this, NewSongActivity.class);
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

    public void dialogueBox_Playlist(View v) {
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


        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        inputPasscode.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playlist_name = inputTitle.getText().toString();
                code = inputPasscode.getText().toString();
                Log.d("NONNUMBERCHECK", Boolean.toString(code.matches("\\d+")));
                if (playlist_name.isEmpty() ||
                        !code.matches("\\d+")
                        || code.length() != 4) {
                    dialog.cancel();
                } else {
                    selected_list = playlist_name;
                    passcode = code;
                    Playlist plist = new Playlist(selected_list, passcode);
                    Log.d("PLAYLIST", plist.getPasscode());
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

    public static String selection() {
        return selected_list;
    }

    public static Hashtable getIdTable() {
        return idTable;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
    }

    @Override
    public void onLoginFailed(Error error) {
    }

    @Override
    public void onTemporaryError() {
    }

    @Override
    public void onConnectionMessage(String s) {
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
    }

    @Override
    public void onPlaybackError(Error error) {
    }
}
