package com.houseparty.houseparty.napsterSampleLibrary;

import com.google.gson.annotations.SerializedName;
import com.napster.cedar.player.data.Track;

import java.util.List;

/**
 * Created by jacksonkurtz on 6/3/18.
 */

public class Tracks {

    @SerializedName("tracks")
    public final List<Track> tracks;

    public Tracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {

        String output = "";
        for( Track t : tracks  ) {
            output += t.name + " ";
        }
        return output;
    }

}
