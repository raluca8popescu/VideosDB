package entertainment;

import java.util.Map;

public final class SeasonSerial {
    private final int currentSeason;
    private int duration;
    private Map<Double, String> ratings;

    /**
     * Transfera datele de intrare in clasa SeasonSerial
     */
    public SeasonSerial(final Season season, final int currentSeason,
                        final Map<Double, String> ratings) {
        this.currentSeason = currentSeason;
        this.duration = season.getDuration();
        this.ratings = ratings;
    }

    /**
     *  Calculeaza rating-ul total al unui sezon
     */
    public double totalRatingSeason() {
        if (ratings.size() == 0) {
            return 0;
        }
        double sumRatings = 0;
        for (Map.Entry<Double, String> entry : ratings.entrySet()) {
            sumRatings += entry.getKey();
        }
        return (sumRatings / (double) ratings.size());
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Map<Double, String> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<Double, String> ratings) {
        this.ratings = ratings;
    }

}
