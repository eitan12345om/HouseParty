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
    RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> mSongs;

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SongAdapter(List<Song> songs) {
        mSongs = songs;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View songView = inflater.inflate(R.layout.song_item, parent, false);

        // Return a new holder instance
        return new SongViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(SongAdapter.SongViewHolder holder, int position) {
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

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songTextView;

        public SongViewHolder(final View itemView) {
            super(itemView);

            songTextView = itemView.findViewById(R.id.song_title);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        public TextView getSongTextView() {
            return songTextView;
        }

        public void setSongTextView(TextView songTextView) {
            this.songTextView = songTextView;
        }
    }
}
