package entertainment;

import java.util.Comparator;

public final class MyComparatorDuration implements Comparator<AllVideosDuration> {
    /**
     * compara cele doua obiecte AllVideosDuration dupa durata sa,
     * iar in caz de egalitate se verifica ordinea alfabetica
     */
    public int compare(final AllVideosDuration v0, final AllVideosDuration v1) {
        double w0 = v0.getDuration();
        double w1 = v1.getDuration();

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
