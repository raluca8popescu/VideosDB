package entertainment;

import fileio.MovieInputData;

import java.util.Map;

public final class Movie extends Video {
    private final int duration;
    private final Map<Double, String> ratings;
    private int appearanceFav;

    public Movie(final MovieInputData movie, final Map<Double, String> rating) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.ratings = rating;
        this.appearanceFav = 0;
    }

    public int getDuration() {
        return duration;
    }

    public Map<Double, String> getRatings() {
        return ratings;
    }

    public int getAppearanceFav() {
        return appearanceFav;
    }

    public void setAppearanceFav(int appearanceFav) {
        this.appearanceFav = appearanceFav;
    }

    public double totalRatingMovie() {
        if (ratings.size() == 0) {
            return 0;
        }
        double sumRatingMovie = 0;
        for (Map.Entry<Double, String> entry : ratings.entrySet()) {
            sumRatingMovie += entry.getKey();
        }
        return (sumRatingMovie / (double) ratings.size());
    }

    @Override
    public String toString() {
        return "Movie{" + "name" + getTitle()
                + "duration=" + duration
                + ", ratings=" + ratings
                + ", appearanceFav=" + appearanceFav
                + '}';
    }
}
