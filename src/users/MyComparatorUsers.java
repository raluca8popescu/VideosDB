package users;

import java.util.Comparator;

public class MyComparatorUsers implements Comparator<AllUsers> {
    /**
     * compara cei doi AllUsers dupa rating, iar in caz de egalitate
     * se verifica ordinea alfabetica
     */
    public final int compare(final AllUsers u0, final AllUsers u1) {
        int w0 = u0.getRatings();
        int w1 = u1.getRatings();

        String s0 = u0.getName();
        String s1 = u1.getName();

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
