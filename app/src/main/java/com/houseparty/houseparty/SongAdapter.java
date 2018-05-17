package com.houseparty.houseparty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Eitan created on 5/17/2018.
 */
public class SongAdapter extends
    RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<Song> mSongs;

    public SongAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View songView = inflater.inflate(R.layout.song_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(songView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Song song = mSongs.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.songTextView;
        textView.setText(song.getTitle());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView songTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            songTextView = itemView.findViewById(R.id.song_title);
        }
    }
}
