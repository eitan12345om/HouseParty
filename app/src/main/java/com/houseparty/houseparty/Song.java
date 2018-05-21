package com.houseparty.houseparty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by jacksonkurtz on 2/23/18.
 */

public abstract class Song {
    protected String title;
    protected String artist;
    protected String uri;
    protected String coverArtUrl;

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

    public abstract void playSong();
}
