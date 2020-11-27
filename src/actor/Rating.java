package actor;


public final class Rating {
    private String name;
    private Double rating;

    public Rating(final String name, final Double rating) {
        this.name = name;
        this.rating = rating;
    }
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ActorRating{"
                + "name='" + name + '\''
                + ", rating=" + rating
                + '}';
    }
}
