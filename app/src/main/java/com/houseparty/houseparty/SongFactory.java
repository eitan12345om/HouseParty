package com.houseparty.houseparty;

/**
 * @author Nathan Boyd april 12 2018
 */


public class SongFactory {
    private static SongFactory instance;

    private Song song;

    private SongFactory() {
    }

    public static SongFactory getInstance() {
        if (instance == null) {
            instance = new SongFactory();
        }
        return instance;

    }

    public Song createSong(final String title, String artist, String api) {
        if ("spotify".equals(api)) {
            String uri = "";
            song = new SpotifySong(title, uri, artist);
        } else if ("soundcloud".equals(api)) {
            String uri = "";
            song = new SoundCloudSong(title, uri, artist);
        } else if ("tidal".equals(api)) {
            String uri = "";
            song = new TidalSong(title, uri, artist);
        } else if ("googleplay".equals(api)) {
            String uri = "";
            song = new GooglePlaySong(title, uri, artist);
        }
        return song;
    }
}
