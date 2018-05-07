package com.houseparty.houseparty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewSongActivity extends AppCompatActivity {
    private boolean pause = true;
    private ListView listV;
    private List<String> list;
    private ArrayAdapter adapter;
    private String selected_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_song);

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

        list = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            list.add("Song " + i);
        }

        listV = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listV.setAdapter(adapter);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_list = list.get(i);
            }
        });
    }
}