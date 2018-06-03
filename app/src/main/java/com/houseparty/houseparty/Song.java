package com.houseparty.houseparty;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song implements Comparable<Song> {
    protected String title;
    protected String artist;
    protected String uri;
    protected String coverArtUrl;
    protected Set<String> thumbs = new HashSet<>();
    /* Add a songAdded variable? */

    public Song() {
    }

    @Deprecated
    public Song(String title, String uri, String artist) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
    }

    public Song(String title, String uri, String artist, String coverArtUrl) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
        this.coverArtUrl = coverArtUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUri() {
        return this.uri;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public int getThumbsSize() {
        return thumbs.size();
    }

    public boolean addThumbsUp(String uid) {
        return thumbs.add(uid);
    }

    public boolean removeThumbsUp(String uid) {
        return thumbs.remove(uid);
    }

    public boolean thumbsContains(String uid) {
        return thumbs.contains(uid);
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Song song = (Song) obj;
        return new EqualsBuilder()
            .append(title, song.title)
            .append(artist, song.artist)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(title)
            .append(artist)
            .append(uri)
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull Song other) {
        int result;

        result = other.getThumbsSize() - getThumbsSize();
        if (result != 0) {
            return result;
        }

        result = other.getTitle().compareTo(getTitle());
        if (result != 0) {
            return result;
        }

        result = other.getArtist().compareTo(getArtist());
        if (result != 0) {
            return result;
        }

        return 0;
    }

    public abstract void playSong();
}
