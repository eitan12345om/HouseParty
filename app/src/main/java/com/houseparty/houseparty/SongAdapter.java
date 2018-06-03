package com.houseparty.houseparty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * @author Eitan created on 5/17/2018.
 */
public class SongAdapter extends
    RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> mSongs;
    private FirebaseUser currentUser;

    // Define listener member variable
    private OnItemClickListener listener;
    private OnItemClickListener thumbListener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnThumbClickListener(OnItemClickListener listener) {
        this.thumbListener = listener;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

        ImageButton thumbImageButton = holder.thumbImageButton;
        thumbImageButton.setSelected(song.thumbsContains(currentUser.getUid()));

        holder.songLikesTextView.setText(String.valueOf(song.getThumbsSize()));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songTextView;
        private TextView songLikesTextView;
        private ImageButton thumbImageButton;

        public SongViewHolder(final View itemView) {
            super(itemView);

            songTextView = itemView.findViewById(R.id.song_title);
            songLikesTextView = itemView.findViewById(R.id.song_likes);
            thumbImageButton = itemView.findViewById(R.id.thumb);

            // Setup the click listener
            songTextView.setOnClickListener(new View.OnClickListener() {
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

            thumbImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (thumbListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            thumbListener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
