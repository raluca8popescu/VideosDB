package entertainment;

import java.util.ArrayList;

public final class GenreView {
    private final String nameGenre;
    private int numberVideos;
    final ArrayList<String> videos;

    public GenreView(final String nameGenre, final int numberVideos) {
        this.nameGenre = nameGenre;
        this.numberVideos = numberVideos;
        this.videos = new ArrayList<>();
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public int getNumberVideos() {
        return numberVideos;
    }

    public void setNumberVideos(final int numberVideos) {
        this.numberVideos = numberVideos;
    }

    public void addVideo(final String video) {
        this.videos.add(video);
    }

    @Override
    public String toString() {
        return "GenreView{"
                + "nameGenre='" + nameGenre + '\''
                + ", numberVideos=" + numberVideos
                + ", videos=" + videos
                + '}';
    }
}
