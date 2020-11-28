package entertainment;

import actor.Rating;

import java.util.Comparator;

public final class MyComparatorUnseen implements Comparator<Rating> {
    /**
     * compara cele doua obiecte Rating dupa rating-ul lor
     */
    public int compare(final Rating act0, final Rating act1) {
        double w0 = act0.getRating();
        double w1 = act1.getRating();

        String s0 = act0.getName();
        String s1 = act1.getName();

        return Double.compare(w1, w0);
    }
}
