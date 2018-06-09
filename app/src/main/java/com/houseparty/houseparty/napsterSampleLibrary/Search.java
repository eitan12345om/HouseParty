package com.houseparty.houseparty.napsterSampleLibrary;

import com.google.gson.annotations.SerializedName;
import com.napster.cedar.player.data.Track;

import java.util.List;

/**
 * Created by jacksonkurtz on 6/4/18.
 */

public class Search {

    @SerializedName("data")
    public final List<Track> search;

    public Search(List<Track> search) {
        this.search = search;
    }

    @Override
    public String toString() {

        String output = "";
        for( Track t : search  ) {
            output += t.name + " ";
        }
        return output;
    }

}
