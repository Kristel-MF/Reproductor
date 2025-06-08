package Controller;

import Model.MusicPlayerEngine;
import Model.PlayList;
import Model.Song;
import View.MusicPlayer;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.images.Artwork;

public class MusicPlayerController {

    private MusicPlayerEngine player;
    private PlayList playList;
    private DefaultListModel<String> listModel;
    private final MusicPlayer view;

    public MusicPlayerController(MusicPlayer view, DefaultListModel<String> listModel) {
        this.view = view;
        this.playList = new PlayList();
        this.listModel = listModel;
        this.player = new MusicPlayerEngine(playList);

      
    }

    private void preloadSongs() {
    listModel.clear();      // Limpia la vista (JList)
playList.clear();
    try {
        File[] files = {
            new File("src/Songs/Los Cafres - Tus ojos (AUDIO) [mwty9NqTtSc] (1).mp3"),
            new File("src/Songs/Vicente García - Carmesí (Cover Audio) [1MpDovOX5zM].mp3")
        };

        for (File file : files) {
            if (file.exists()) {
                addSongFromFile(file);
            } else {
                System.out.println("File not found: " + file.getAbsolutePath());
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error preloading songs: " + e.getMessage());
    }
}

    public void addSongFromFile(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();
            AudioHeader header = audioFile.getAudioHeader();

            String title = (tag != null && tag.getFirst(FieldKey.TITLE) != null && !tag.getFirst(FieldKey.TITLE).isEmpty())
                    ? tag.getFirst(FieldKey.TITLE)
                    : file.getName();

            String artist = (tag != null && tag.getFirst(FieldKey.ARTIST) != null && !tag.getFirst(FieldKey.ARTIST).isEmpty())
                    ? tag.getFirst(FieldKey.ARTIST)
                    : "Unknown";

            String genre = (tag != null && tag.getFirst(FieldKey.GENRE) != null && !tag.getFirst(FieldKey.GENRE).isEmpty())
                    ? tag.getFirst(FieldKey.GENRE)
                    : "Unknown";

            int duration = (header != null) ? header.getTrackLength() : 0;

            byte[] coverImageBytes = null;
            if (tag != null) {
                Artwork artwork = tag.getFirstArtwork();
                if (artwork != null) {
                    coverImageBytes = artwork.getBinaryData();
                }
            }

            Song song = new Song(title, artist, genre, duration, file.getAbsolutePath(), coverImageBytes);
            playList.addSong(song);

            String listEntry = artist + " - " + title;
            view.addSongToList(listEntry);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading song metadata: " + e.getMessage());
        }
    }

    // Optional public method to add songs dynamically during runtime
    public void addSong(File file) {
        addSongFromFile(file);
    }

    // Playback control methods
    public void play(int index) {
        player.setCurrentIndex(index);
        player.playCurrentSong();
    }

    public void pause() {
        player.pauseMusic();
    }

    public void stop() {
        player.stopMusic();
    }

    public void next() {
        player.nextSong();
    }

    public void previous() {
        player.previousSong();
    }
}
