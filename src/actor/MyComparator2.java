package actor;

import java.util.Comparator;

public final class MyComparator2 implements Comparator<AllActors> {
    @Override
    public int compare(final AllActors act0, final AllActors act1) {
        double w0 = act0.getAwardsNumber();
        double w1 = act1.getAwardsNumber();

        String s0 = act0.getName();
        String s1 = act1.getName();
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
