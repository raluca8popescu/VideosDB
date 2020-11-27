package entertainment;

public final class AllVideosFavorite {
    private final String name;
    private final int appearanceFav;

    public AllVideosFavorite(final String name, final int appearanceFav) {
        this.name = name;
        this.appearanceFav = appearanceFav;
    }

    public String getName() {
        return name;
    }

    public int getAppearanceFav() {
        return appearanceFav;
    }
    @Override
    public String toString() {
        return "AllVideosFavorite{"
                + "name='" + name + '\''
                + ", appearanceFav=" + appearanceFav
                + '}';
    }
}

