package actor;

import java.util.Comparator;

public final class MyComparator implements Comparator<Rating> {
    @Override
    public int compare(final Rating act0, final Rating act1) {
        double w0 = act0.getRating();
        double w1 = act1.getRating();

        String s0 = act0.getName();
        String s1 = act1.getName();

        if (w0 > w1) {
            return -1;
        }
        if (w0 == w1) {
            if ((s0.compareTo(s1)) > 0) {
                return -1;
            } else {
                return 0;
            }
        }
        return 1;
    }
}
