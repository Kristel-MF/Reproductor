package Model;

import java.io.File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class Song {

    private String title;
    private String artist;
    private String gender;
    private int duration; // en segundos
    private String path;
    private byte[] coverImage;

    public Song() {
    }

    public Song(String title, String artist, String gender, int duration, String path, byte[] coverImage) {
        this.title = title;
        this.artist = artist;
        this.gender = gender;
        this.duration = duration;
        this.path = path;
        this.coverImage = coverImage;
    }

    // Método para cargar metadatos desde archivo mp3
    public static Song fromFile(String filePath) {
        try {
            File file = new File(filePath);
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();
            AudioHeader header = audioFile.getAudioHeader();

            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String genre = tag.getFirst(FieldKey.GENRE);
            int duration = header.getTrackLength();

            byte[] cover = null;
            if (tag.getFirstArtwork() != null) {
                cover = tag.getFirstArtwork().getBinaryData();
            }

            return new Song(title, artist, genre, duration, filePath, cover);

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, retorna un objeto Song con datos mínimos
            return new Song("", "", "", 0, filePath, null);
        }
    }

    // Getters y setters
    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", gender='" + gender + '\'' +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                ", coverImage=" + (coverImage != null ? "Sí" : "No") +
                '}';
    }
     public String getDurationFormatted() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
