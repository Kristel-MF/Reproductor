package Model;

import javazoom.jlgui.basicplayer.*;

import Model.PlayList;
import Model.Song;
import View.MusicPlayer;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.Map;

public class MusicPlayerEngine implements BasicPlayerListener {

    private BasicPlayer player;
    private PlayList playList;
    private int currentIndex = -1;
    private MusicPlayer gui; // Interface or class responsible for GUI updates

    private double totalSeconds = 0;
    private double currentSeconds = 0;

    public MusicPlayerEngine(PlayList playList) {
        this.playList = playList;
        this.player = new BasicPlayer();
        this.player.addBasicPlayerListener(this);
    }

    // Optional: attach GUI for updates
    public void setGUI(MusicPlayer gui) {
        this.gui = gui;
    }

    public void setCurrentIndex(int index) {
        if (index >= 0 && index < playList.getSize()) {
            this.currentIndex = index;
        }
    }

    public void playCurrentSong() {
        Song song = playList.getByIndex(currentIndex);
        if (song != null) {
            playSong(song);
        } else if (gui != null) {
            gui.updateStatus("No song selected or invalid index.");
        }
    }

    private void playSong(Song song) {
        try {
            stopMusic(); // stop any previous playback
            player.open(new File(song.getPath()));
            player.play();
            if (gui != null) {
                gui.updateStatus("Playing: " + song.getTitle());
            }
        } catch (Exception e) {
            if (gui != null) {
                gui.updateStatus("Error playing song: " + e.getMessage());
            }
        }
    }

    public void pauseMusic() {
        try {
            player.pause();
            if (gui != null) {
                gui.updateStatus("Paused");
            }
        } catch (Exception e) {
            if (gui != null) {
                gui.updateStatus("Error pausing: " + e.getMessage());
            }
        }
    }

    public void stopMusic() {
        try {
            player.stop();
            if (gui != null) {
                gui.updateStatus("Stopped");
            }
        } catch (Exception e) {
            if (gui != null) {
                gui.updateStatus("Error stopping: " + e.getMessage());
            }
        }
    }

    public void nextSong() {
        if (currentIndex < playList.getSize() - 1) {
            currentIndex++;
            playCurrentSong();
        }
    }

    public void previousSong() {
        if (currentIndex > 0) {
            currentIndex--;
            playCurrentSong();
        }
    }

    public void setVolume(double volume) {
        try {
            player.setGain(volume); // from 0.0 to 1.0
        } catch (BasicPlayerException e) {
            if (gui != null) {
                gui.updateStatus("Volume error: " + e.getMessage());
            }
        }
    }

    // === BasicPlayerListener methods ===
    @Override
    public void opened(Object stream, Map properties) {
        if (properties.containsKey("duration")) {
            totalSeconds = ((Long) properties.get("duration")) / 1_000_000.0;
        }
    }

    @Override
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
        currentSeconds = microseconds / 1_000_000.0;
        if (gui != null) {
            SwingUtilities.invokeLater(()
                    -> gui.updateTime((int) currentSeconds, (int) totalSeconds)
            );
        }
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        // You could auto-play next track here if desired
    }

    @Override
    public void setController(BasicController controller) {
        // Not used here
    }
}
