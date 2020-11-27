package entertainment;

import users.User;
import users.Users;
import actor.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Serials {
    private final List<Serial> serials;

    public Serials(final List<Serial> serials) {
        this.serials = serials;
    }

    public Double isSerial(final String title) {
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                if (serial.totalRatingSeasons() != 0) {
                    return serial.totalRatingSeasons();
                } else {
                    return 0.0;
                }
            }
        }
        return -1.0;
    }

    public boolean addRating(final String title, final String name,
                             final Double grade, final Integer season) {
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                ArrayList<SeasonSerial> s = serial.getSeasons();
                Map<Double, String> seasonRating = s.get(season - 1).getRatings();
                if (seasonRating.containsValue(name)) {
                    return false;
                } else {
                    seasonRating.put(grade, name);
                    return true;
                }
            }
        }
        return false;
    }

    public void favoriteSerials(final User name) {
        for (Serial serial : serials) {
            if (name.getFavoriteMovies().contains(serial.getTitle())) {
                serial.setAppearanceFav(serial.getAppearanceFav() + 1);
            }
        }

    }

    public void durationSerial(final ArrayList<AllVideosDuration> allVideos,
                               final List<String> genre, final List<String> year) {
        int ok;
        for (Serial serial : serials) {
            ok = 0;
            if (year.contains(String.valueOf(serial.getYear()))) {
                for (String s : genre) {
                    if (serial.getGenres().contains(s)) {
                        ok++;
                    }
                }
                if (ok == genre.size()) {
                    allVideos.add(new AllVideosDuration(serial.getTitle(), serial.getDuration()));
                }
            }

        }
    }

    public void favoriteSerials(final ArrayList<AllVideosFavorite> allVideos,
                                final List<String> genre, final List<String> year) {
        int ok = 0;
        for (Serial serial : serials) {
            ok = 0;
            if (serial.getAppearanceFav() != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(serial.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (serial.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                allVideos.add(new AllVideosFavorite(serial.getTitle(),
                                        serial.getAppearanceFav()));
                            }
                        } else {
                            allVideos.add(new AllVideosFavorite(serial.getTitle(),
                                    serial.getAppearanceFav()));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (serial.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            allVideos.add(new AllVideosFavorite(serial.getTitle(),
                                    serial.getAppearanceFav()));
                        }
                    } else {
                        allVideos.add(new AllVideosFavorite(serial.getTitle(),
                                serial.getAppearanceFav()));
                    }
                }
            }
        }
    }

    public void viewSerial(final ArrayList<AllVideosView> allVideos, final List<String> genre,
                           final List<String> year, final Users listUsers) {
        int ok = 0;
        int views;
        for (Serial serial : serials) {
            ok = 0;
            views = listUsers.getViews(serial.getTitle());
            if (views != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(serial.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (serial.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                allVideos.add(new AllVideosView(serial.getTitle(), views));
                            }
                        } else {
                            allVideos.add(new AllVideosView(serial.getTitle(), views));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (serial.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            allVideos.add(new AllVideosView(serial.getTitle(), views));
                        }
                    } else {
                        allVideos.add(new AllVideosView(serial.getTitle(), views));
                    }
                }
            }
        }
    }

    public void rateSerialFilters(final ArrayList<Rating> videos,
                                  final List<String> genre, final List<String> year) {
        int ok;
        for (Serial serial : serials) {
            ok = 0;
            if (serial.totalRatingSeasons() != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(serial.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (serial.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                videos.add(new Rating(serial.getTitle(),
                                        serial.totalRatingSeasons()));
                            }
                        } else {
                            videos.add(new Rating(serial.getTitle(),
                                    serial.totalRatingSeasons()));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (serial.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            videos.add(new Rating(serial.getTitle(), serial.totalRatingSeasons()));
                        }
                    } else {
                        videos.add(new Rating(serial.getTitle(), serial.totalRatingSeasons()));
                    }
                }
            }
        }
    }

    public void rateSerialGenre(final ArrayList<Rating> videos,
                                final String genre, final Map<String, Integer> history) {
        for (Serial serial : serials) {
            if (serial.getGenres().contains(genre)) {
                if (!history.containsKey(serial.getTitle())) {
                    videos.add(new Rating(serial.getTitle(), serial.totalRatingSeasons()));
                }
            }
        }
    }

    public void rateSerial(final ArrayList<Rating> videos, final Map<String, Integer> history) {
        for (int i = serials.size() - 1; i >= 0; i--) {
            if (!history.containsKey(serials.get(i).getTitle())) {
                videos.add(new Rating(serials.get(i).getTitle(),
                        serials.get(i).totalRatingSeasons()));
            }
        }
    }

    public void createGenreView(final ArrayList<GenreView> genreViews) {
        for (Serial serial : serials) {
            ArrayList<String> genre = serial.getGenres();
            for (GenreView genreView : genreViews) {
                if (genre.contains(genreView.getNameGenre())) {
                    genreView.setNumberVideos(genreView.getNumberVideos() + 1);
                    genreView.addVideo(serial.getTitle());
                }
            }
        }
    }
}
