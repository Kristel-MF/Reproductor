/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mode;

/**
 *
 * @author emaag
 */
public class NodoSong {
    private Song song;
    private NodoSong next;
    private NodoSong last;

    public NodoSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    public NodoSong getNext() {
        return next;
    }

    public NodoSong getLast() {
        return last;
    }

    public void setNext(NodoSong next) {
        this.next = next;
    }

    public void setLast(NodoSong last) {
        this.last = last;
    }
        
}
