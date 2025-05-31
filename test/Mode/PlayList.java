/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mode;

import java.io.Serializable;

/**
 *
 * @author emaag
 */
public class PlayList implements Serializable {

    private NodoSong head;
    private NodoSong current;

    public void addSong(Song song) {
        NodoSong recent = new NodoSong(song);
        if (head == null) {
            head = recent;
            current = recent;
        } else {
            NodoSong temp = head;

            while (temp.getNext() != null) {
                temp = temp.getLast();
            }
            temp.setNext(recent);
            recent.setLast(temp);
        }
    }

    public void deleteCurrent() {
        if (current == null) {
            return;
        }

        if (current.getLast() != null) {
            current.getLast().setNext(current.getNext());
        } else {
            head = current.getNext();
        }
        if (current.getNext() != null) {
            current.getNext().setLast(current.getLast());
            current = current.getLast() != null ? current.getNext() : head;
        }
    }

    public void next() {
        if (current != null && current.getNext() != null) {
            current = current.getNext();
        }
    }

    public void last() {
        if (current != null && current.getLast() != null) {
            current = current.getLast();
        }
    }

}
