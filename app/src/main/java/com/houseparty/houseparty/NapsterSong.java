package com.houseparty.houseparty;

import com.napster.cedar.player.data.Track;

/**
 * Created by jacksonkurtz on 6/5/18.
 */

public class NapsterSong extends Song {

    protected Track track;

    public NapsterSong() {
        super();
    }

    public NapsterSong(String title, String uri, String artist, Track track, String coverArtUrl) {
        super(title, uri, artist, coverArtUrl);
        this.track = track;
    }

    @Override
    public void playSong() {
        NewSongActivity.napsterPlayer.play(track);
    }

}
