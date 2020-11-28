package entertainment;

import fileio.MovieInputData;

import java.util.Map;

public final class Movie extends Video {
    private final int duration;
    private final Map<Double, String> ratings;
    private int appearanceFav;

    /**
     * Transfera datele de intrare din MovieInputData in clasa Movie
     */
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

    /**
     * Calculeaza rating-ul total al unui film
     */
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
}
