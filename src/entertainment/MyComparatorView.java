package entertainment;

import java.util.Comparator;

public final class MyComparatorView implements Comparator<AllVideosView> {
    public int compare(final AllVideosView v0, final AllVideosView v1) {
        double w0 = v0.getViews();
        double w1 = v1.getViews();

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
