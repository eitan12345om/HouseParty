package com.houseparty.houseparty;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setTitle("Song Menu");

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
}
