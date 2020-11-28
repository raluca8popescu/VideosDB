package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.HashMap;

public final class Serial extends Video {
    private final int numberOfSeasons;
    private final ArrayList<SeasonSerial> seasons;
    private int duration;
    private int appearanceFav;

    /**
     * transfera datele de intrare din SerialInputData in clasa Serial
     */
    public Serial(final SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = new ArrayList<>();
        for (int i = 0; i < serial.getNumberSeason(); i++) {
            this.seasons.add(new SeasonSerial(serial.getSeasons().get(i), i + 1, new HashMap<>()));
            this.duration += serial.getSeasons().get(i).getDuration();
        }
        this.appearanceFav = 0;
    }

    /**
     * calculeaza rating-ul total al unui serial
     */
    public double totalRatingSeasons() {
        if (seasons.size() == 0) {
            return 0;
        }
        double sumRatingsSeasons = 0;
        for (SeasonSerial season : seasons) {
            sumRatingsSeasons += season.totalRatingSeason();
        }
        return (sumRatingsSeasons / (double) seasons.size());
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAppearanceFav() {
        return appearanceFav;
    }

    public void setAppearanceFav(int appearanceFav) {
        this.appearanceFav = appearanceFav;
    }

    public ArrayList<SeasonSerial> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
