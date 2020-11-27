package entertainment;

import java.util.Comparator;

public final class MyComparatorGenreView implements Comparator<GenreView> {
    public int compare(final GenreView v0, final GenreView v1) {
        double w0 = v0.getNumberVideos();
        double w1 = v1.getNumberVideos();

        String s0 = v0.getNameGenre();
        String s1 = v1.getNameGenre();

        if (w0 > w1) {
            return -1;
        }
        if (w0 == w1) {
            return 1;
        }
        return 1;
    }
}
