package entertainment;

import users.User;
import users.Users;
import actor.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Movies {
    private final List<Movie> movies;

    public Movies(final List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Verifica daca titlul dat ca parametru este in lista de filme
     */
    public Double isMovie(final String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                if (movie.totalRatingMovie() != 0) {
                    return movie.totalRatingMovie();
                } else {
                    return 0.0;
                }
            }
        }
        return -1.0;
    }

    /**
     * Pentru un film dat ca parametru, se cauta in lista de filme
     * Daca in lista sa de rating-uri se afla deja numele user-ului,
     * se returneaza false. In caz contrar, se adauga un nou rating
     * si se returneaza true.
     */
    public boolean addRating(final String title, final String name, final Double grade) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                Map<Double, String> movieRating = movie.getRatings();
                if (movieRating.containsValue(name)) {
                    return false;
                } else {
                    movieRating.put(grade, name);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Se returneaza primul film din lista de filme care nu se afla
     * in istoricul user-ului
     */
    public String firstMovie(final User name) {
        for (Movie movie : movies) {
            if (!name.getHistory().containsKey(movie.getTitle())) {
                return movie.getTitle();
            }
        }
        return null;
    }

    /**
     * Adauga pentru fiecare film numarul de aparitii in lista
     * de favorite a userului dat ca parametru
     */
    public void favoriteMovie(final User name) {
        for (Movie movie : movies) {
            if (name.getFavoriteMovies().contains(movie.getTitle())) {
                movie.setAppearanceFav(movie.getAppearanceFav() + 1);
            }
        }

    }

    /**
     * Se returneaza numele filmului cu cele mai multe aparitii care
     * nu se afla in istoricul user-ului dat ca parametru
     */
    public String getFavorite(final User name, final ArrayList<AllVideosFavorite> fav) {
        int max = -1;
        String title = null;
        for (AllVideosFavorite allVideosFavorite : fav) {
            if (allVideosFavorite.getAppearanceFav() > max
                    && (!name.getHistory().containsKey(allVideosFavorite.getName()))) {
                max = allVideosFavorite.getAppearanceFav();
                title = allVideosFavorite.getName();
            }
        }
        if (max == 0 || max == -1) {
            return null;
        }
        return title;
    }

    /**
     * Adauga in lista allVideos doar filmele care contin genurile si anii
     * din listele genre si year
     */
    public void durationMovie(final ArrayList<AllVideosDuration> allVideos,
                              final List<String> genre, final List<String> year) {
        int ok = 0;
        for (Movie movie : movies) {
            ok = 0;
            if (year.get(0) != null) {
                if (year.contains(String.valueOf(movie.getYear()))) {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (movie.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            allVideos.add(new AllVideosDuration(movie.getTitle(),
                                    movie.getDuration()));
                        }
                    } else {
                        allVideos.add(new AllVideosDuration(movie.getTitle(),
                                movie.getDuration()));
                    }
                }
            } else {
                if (genre.get(0) != null) {
                    for (String s : genre) {
                        if (movie.getGenres().contains(s)) {
                            ok++;
                        }
                    }
                    if (ok == genre.size()) {
                        allVideos.add(new AllVideosDuration(movie.getTitle(), movie.getDuration()));
                    }
                } else {
                    allVideos.add(new AllVideosDuration(movie.getTitle(), movie.getDuration()));
                }
            }
        }
    }
    /**
     * Adauga in lista allVideos doar filmele care contin genurile si anii
     * din listele genre si year si au numarul de aparitii la favorite
     * diferit de 0
     */
    public void favoriteMovie(final ArrayList<AllVideosFavorite> allVideos,
                              final List<String> genre, final List<String> year) {
        int ok = 0;
        for (Movie movie : movies) {
            ok = 0;
            if (movie.getAppearanceFav() != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(movie.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (movie.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                allVideos.add(new AllVideosFavorite(movie.getTitle(),
                                        movie.getAppearanceFav()));
                            }
                        } else {
                            allVideos.add(new AllVideosFavorite(movie.getTitle(),
                                    movie.getAppearanceFav()));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (movie.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            allVideos.add(new AllVideosFavorite(movie.getTitle(),
                                    movie.getAppearanceFav()));
                        }
                    } else {
                        allVideos.add(new AllVideosFavorite(movie.getTitle(),
                                movie.getAppearanceFav()));
                    }
                }
            }
        }
    }
    /**
     * Adauga in lista allVideos doar filmele care contin genurile si anii
     * din listele genre si year si au numarul de vizualizari diferit de 0
     */
    public void viewMovie(final ArrayList<AllVideosView> allVideos, final List<String> genre,
                          final List<String> year, final Users listUsers) {
        int ok = 0;
        int views;
        for (Movie movie : movies) {
            ok = 0;
            views = listUsers.getViews(movie.getTitle());
            if (views != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(movie.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (movie.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                allVideos.add(new AllVideosView(movie.getTitle(), views));
                            }
                        } else {
                            allVideos.add(new AllVideosView(movie.getTitle(), views));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (movie.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            allVideos.add(new AllVideosView(movie.getTitle(), views));
                        }
                    } else {
                        allVideos.add(new AllVideosView(movie.getTitle(), views));
                    }
                }
            }
        }
    }
    /**
     * Adauga in lista videos doar filmele care contin genurile si anii
     * din listele genre si year
     */
    public void rateMovieFilters(final ArrayList<Rating> videos,
                                 final List<String> genre, final List<String> year) {
        int ok;
        for (Movie movie : movies) {
            ok = 0;
            if (movie.totalRatingMovie() != 0) {
                if (year.get(0) != null) {
                    if (year.contains(String.valueOf(movie.getYear()))) {
                        if (genre.get(0) != null) {
                            for (String s : genre) {
                                if (movie.getGenres().contains(s)) {
                                    ok++;
                                }
                            }
                            if (ok == genre.size()) {
                                videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
                            }
                        } else {
                            videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
                        }
                    }
                } else {
                    if (genre.get(0) != null) {
                        for (String s : genre) {
                            if (movie.getGenres().contains(s)) {
                                ok++;
                            }
                        }
                        if (ok == genre.size()) {
                            videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
                        }
                    } else {
                        videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
                    }
                }
            }
        }
    }

    /**
     * Adauga in lista videos doar filmele care sunt genul dat si nu se afla
     * in istoricul dat ca parametru
     */
    public void rateMovieGenre(final ArrayList<Rating> videos,
                               final String genre, final Map<String, Integer> history) {
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                if (!history.containsKey(movie.getTitle())) {
                    videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
                }
            }
        }
    }

    /**
     * Adauga in lista videos doar filmele care nu se afla
     * in istoricul dat ca parametru
     */
    public void rateMovie(final ArrayList<Rating> videos, final Map<String, Integer> history) {
        for (Movie movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                videos.add(new Rating(movie.getTitle(), movie.totalRatingMovie()));
            }
        }
    }
    /**
     * Adauga in lista de video-uri a fiecarui obiect genreViews
     * doar video-urile care corespund genului obiectului
     */
    public void createGenreView(final ArrayList<GenreView> genreViews) {
        for (Movie movie : movies) {
            ArrayList<String> genre = movie.getGenres();
            for (GenreView genreView : genreViews) {
                if (genre.contains(genreView.getNameGenre())) {
                    genreView.setNumberVideos(genreView.getNumberVideos() + 1);
                    genreView.addVideo(movie.getTitle());
                }
            }
        }
    }
}
