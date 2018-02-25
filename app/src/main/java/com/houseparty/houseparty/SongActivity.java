package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ListAdapter adapter;
    private MediaPlayer mediaPlayer;
    private String title = "HouseParty - ";
    private static String song_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(title + MainActivity.selection());

        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for(int i = 0; i < fields.length; i++){
            list.add(fields[i].getName());
        }

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: Get current position stored in onPause
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer.isPlaying()) {
            // TODO: Store this somewhere
            mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
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
                } else {
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
