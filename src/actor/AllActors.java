package actor;

public final class AllActors {
    private final String name;
    private final Integer awardsNumber;

    public AllActors(final String name, final Integer awardsNumber) {
        this.name = name;
        this.awardsNumber = awardsNumber;
    }

    public String getName() {
        return name;
    }

    public Integer getAwardsNumber() {
        return awardsNumber;
    }

    @Override
    public String toString() {
        return "AllActors{"
                + "name='" + name + '\''
                + ", awardsNumber=" + awardsNumber
                + '}';
    }
}
