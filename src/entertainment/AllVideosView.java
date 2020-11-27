package entertainment;

public final class AllVideosView {
    private final String name;
    private final int views;

    public AllVideosView(final String name, final int views) {
        this.name = name;
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public int getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "AllVideosView{"
                + "name='" + name + '\''
                + ", views=" + views
                + '}';
    }
}
