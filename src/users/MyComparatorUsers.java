package users;

import java.util.Comparator;

public class MyComparatorUsers implements Comparator<AllUsers> {
    public final int compare(final AllUsers u0, final AllUsers u1) {
        int w0 = u0.getRatings();
        int w1 = u1.getRatings();

        String s0 = u0.getName();
        String s1 = u1.getName();

        if (w0 > w1) {
            return -1;
        }
        if (w0 == w1) {
            return Integer.compare(0, s0.compareTo(s1));
        }
        return 1;
    }
}
