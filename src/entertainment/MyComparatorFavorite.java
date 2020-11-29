package entertainment;

import java.util.Comparator;

public final class MyComparatorFavorite implements Comparator<AllVideosFavorite> {
    /**
     * compara cele doua obiecte AllVideosFavorite dupa numarul de aparitii
     * la favorite, iar in caz de egalitate se verifica ordinea alfabetica
     */
    public int compare(final AllVideosFavorite v0, final AllVideosFavorite v1) {
        double w0 = v0.getAppearanceFav();
        double w1 = v1.getAppearanceFav();

        String s0 = v0.getName();
        String s1 = v1.getName();

        if (w0 > w1) {
            return -1;
        }
        if (w0 == w1) {
            if ((s0.compareTo(s1)) > 0) {
                return -1;
            } else {
                return 1;
            }
        }
        return 1;
    }
}
