/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emaag
 */

/**
 * Represents a playlist of songs.
 */
public class PlayList {
    private List<Song> songs = new ArrayList<>();

    /**
     * Adds a new song to the playlist.
     * @param song The song to be added.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Returns the list of all songs.
     * @return List of songs.
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Returns the song at the given index.
     * @param index Index of the song.
     * @return Song at the given index, or null if out of bounds.
     */
    public Song getByIndex(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;
    }

    /**
     * Returns the total number of songs.
     * @return Size of the playlist.
     */
    public int getSize() {
        return songs.size();
        
    }
     public void clear() {
        songs.clear();  // Limpia todas las canciones de la lista
    }
     public boolean containsSong(String path) {
    for (Song song : songs) {
        if (song.getPath().equals(path)) {
            return true;  // Ya existe la canción
        }
    }
    return false;
}

}
