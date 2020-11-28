package entertainment;

import java.util.Comparator;

public final class MyComparatorGenreView implements Comparator<GenreView> {

    /**
     * compara cei doi GenreView dupa numarul de video-uri
     */
    public int compare(final GenreView v0, final GenreView v1) {
        double w0 = v0.getNumberVideos();
        double w1 = v1.getNumberVideos();

        String s0 = v0.getNameGenre();
        String s1 = v1.getNameGenre();

        return Double.compare(w1, w0);
    }
}
