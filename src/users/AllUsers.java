package users;

public class AllUsers {
    private final String name;
    private final Integer ratings;

    public AllUsers(final String name, final Integer ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    public final String getName() {
        return name;
    }

    public final Integer getRatings() {
        return ratings;
    }

    @Override
    public final String toString() {
        return "AllUsers{"
                + "name='" + name + '\''
                + ", ratings=" + ratings
                + '}';
    }
}
