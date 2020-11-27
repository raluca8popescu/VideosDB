package entertainment;

public final class AllVideosDuration {
    private final String name;
    private final Integer duration;

    public AllVideosDuration(final String name, final Integer duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "AllMovies{"
                + "name='" + name + '\''
                + ", duration=" + duration
                + '}';
    }
}
