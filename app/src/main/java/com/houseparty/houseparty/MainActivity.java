package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ArrayAdapter adapter;
    private ActionBar actionBar;
    private String playlist_name = "";
    private String title = "HouseParty";
    private static String selected_list;

    private FirebaseDatabase pFirebaseDatabase;
    private DatabaseReference pPlaylistDatabaseReference;
    private ChildEventListener pChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pFirebaseDatabase = FirebaseDatabase.getInstance();
        pPlaylistDatabaseReference = pFirebaseDatabase.getReference().child("playlists");
        //pPlaylistDatabaseReference.keepSynced(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        actionBar.setTitle(title);

        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        System.out.print( "set adapter" );
        listView.setAdapter(adapter);
        actionBar.show();

        //for(int i = 0; i < 20; i++){
        //    list.add("Playlist_" + i);
        //}
        /*
         pPlaylistDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Playlist pList = dataSnapshot.getValue(Playlist.class);
                list.add(pList.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        System.out.println("adding listener...");
        pChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("on child added method run...");
                System.out.println("Size of list is: " + list.size());
                Playlist pList = dataSnapshot.getValue(Playlist.class);
                System.out.print("Add to list: " + pList.getName() );
                list.add(pList.getName());
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
                selected_list = list.get(i);
                Intent intent = new Intent(getBaseContext(), SongActivity.class);
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
        System.out.flush();
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
                    Playlist plist = new Playlist( playlist_name );
                    pPlaylistDatabaseReference.push().setValue(plist);
                    Intent intent = new Intent(getBaseContext(), SongActivity.class);
                    //list.add(playlist_name);
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
}
