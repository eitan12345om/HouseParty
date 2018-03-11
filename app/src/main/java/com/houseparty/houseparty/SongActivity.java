package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SongActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ArrayAdapter adapter;
    private MediaPlayer mediaPlayer;
    private String title = "HouseParty - ";
    private static String song_name;

    private FirebaseDatabase sFirebaseDatabase;
    private DatabaseReference songDatabaseReference;
    private ChildEventListener sChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        sFirebaseDatabase = FirebaseDatabase.getInstance();
        Hashtable<String,String> t = MainActivity.getIdTable();
        String id = t.get(MainActivity.selection());
        songDatabaseReference = sFirebaseDatabase.getReference().child("playlists").child( id ).child("songs");

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(title + MainActivity.selection());

        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<>();
        /*
        Field[] fields = R.raw.class.getFields();
        for(int i = 0; i < fields.length; i++){
            list.add(fields[i].getName());
        }*/

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer != null){
                    mediaPlayer.release();
                }

                int resID = getResources().getIdentifier(list.get(i), "raw", getPackageName());
                mediaPlayer = MediaPlayer.create(SongActivity.this, resID);
                mediaPlayer.start();
            }
        });

        sChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Size of list is: " + list.size());
                Song sList = dataSnapshot.getValue(Song.class);
                list.add(sList.getTitle());
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
                if (song_name.isEmpty()) {
                    dialog.cancel();
                }
                else {
                    Song song = new Song(song_name);
                    songDatabaseReference.push().setValue(song);
                    //list.add(song_name);
                    //Intent intent = new Intent(getBaseContext(), SongActivity.class);
                    //startActivity(intent);
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
}
