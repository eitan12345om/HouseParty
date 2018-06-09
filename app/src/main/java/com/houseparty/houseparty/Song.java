package com.houseparty.houseparty;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song implements Comparable<Song> {
    protected String title;
    protected String artist;
    protected String uri;
    protected String coverArtUrl;
    private List<String> thumbs = new ArrayList<>();
    private Long timeAdded;
    private String uid;

    public Song() {
        timeAdded = System.currentTimeMillis();
    }

    @Deprecated
    public Song(String title, String uri, String artist) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
        timeAdded = System.currentTimeMillis();
    }

    public Song(String title, String uri, String artist, String coverArtUrl) {
        this.title = title;
        this.uri = uri;
        this.artist = artist;
        this.coverArtUrl = coverArtUrl;
        timeAdded = System.currentTimeMillis();
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

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    public int retrieveThumbsSize() {
        return thumbs.size();
    }

    public void addThumbsUp(String uid) {
        thumbs.add(uid);
    }

    public void removeThumbsUp(String uid) {
        thumbs.remove(uid);
    }

    public boolean thumbsContains(String uid) {
        return thumbs.contains(uid);
    }

    public Long getTimeAdded() {
        return this.timeAdded;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

        result = other.retrieveThumbsSize() - retrieveThumbsSize();
        if (result != 0) {
            return result;
        }

        result = (int) (getTimeAdded() - other.getTimeAdded());
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
