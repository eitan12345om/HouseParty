package com.houseparty.houseparty;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewSongActivity extends AppCompatActivity {
    private boolean pause = true;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private String selectedList;
    private ArrayList<Song> songs;

    private String host;
    private FirebaseUser iam;  /* Variable to keep track of who I is */

    private FirebaseDatabase sFirebaseDatabase;
    private DatabaseReference songDatabaseReference;
    private ChildEventListener sChildEventListener;

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

        sChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> dataTable = (HashMap) dataSnapshot.getValue();

                /* TODO: Use SongFactory */
                songs.add(new SpotifySong(
                    dataTable.get("title"),
                    dataTable.get("uri"),
                    dataTable.get("artist")
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
                Log.i("NewSongActivity", "Child Changed!");

                /* TODO: This is bad practice. Be more specific */
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap<String, String> dataTable = (HashMap) dataSnapshot.getValue();

                /* TODO: Use SongFactory */
                songs.remove(new SpotifySong(
                    dataTable.get("title"),
                    dataTable.get("uri"),
                    dataTable.get("artist")
                ));

                /* TODO: This is bad practice. Be more specific */
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

    public void setUpAdapter() {
        recyclerView = findViewById(R.id.recyclerView);
        songs = new ArrayList<>();

        adapter = new SongAdapter(songs);
        adapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Song song = songs.get(position);
                Snackbar.make(view, song.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getParent(), 1));
    }
}