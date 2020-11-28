package main;

import users.AllUsers;
import actor.Actor;
import actor.Actors;
import actor.AllActors;
import actor.MyComparator;
import actor.MyComparator2;
import actor.Rating;

import entertainment.AllGenreViews;
import entertainment.AllVideosDuration;
import entertainment.AllVideosFavorite;
import entertainment.AllVideosView;
import entertainment.GenreView;
import entertainment.Movie;
import entertainment.Movies;
import entertainment.MyComparatorDuration;
import entertainment.MyComparatorFavorite;
import entertainment.MyComparatorGenreView;
import entertainment.MyComparatorUnseen;
import entertainment.MyComparatorView;
import entertainment.Serial;
import entertainment.Serials;

import users.*;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        List<Actor> actors = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<Serial> serials = new ArrayList<>();

        for (int i = 0; i < input.getActors().size(); i++) {
            actors.add(new Actor(input.getActors().get(i)));
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            users.add(new User(input.getUsers().get(i)));
        }
        for (int i = 0; i < input.getCommands().size(); i++) {
            actions.add(new Action(input.getCommands().get(i)));
            if (input.getCommands().get(i).getActionType().equals("query")) {
                actions.get(i).setFilters(input.getCommands().get(i).getFilters());
            }
        }
        for (int i = 0; i < input.getMovies().size(); i++) {
            movies.add(new Movie(input.getMovies().get(i), new HashMap<>()));
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            serials.add(new Serial(input.getSerials().get(i)));
        }

        Users listUsers = new Users(users);
        Movies listMovies = new Movies(movies);
        Serials listSerials = new Serials(serials);
        Actors listActors = new Actors(actors);

        String username;
        String title;
        Map<String, Integer> k;
        ArrayList<String> favorite;
        int id;

        int n = actions.size();
        for (int i = 0; i < n; i++) {
            switch (actions.get(i).getActionType()) {
                case "command":
                    switch (actions.get(i).getType()) {
                        case "favorite":
                            id = actions.get(i).getActionId();
                            username = actions.get(i).getUsername();
                            title = actions.get(i).getTitle();
                            k = listUsers.getHistory(username);
                            favorite = listUsers.getFavoriteMovies(username);

                            if (k != null) {
                                if (k.containsKey(title)) {
                                    if (listUsers.addFavorite(title, k, favorite)) {
                                        arrayResult.add(fileWriter.writeFile(id, title,
                                                "success -> " + title + " was added as favourite"));
                                    } else {
                                        arrayResult.add(fileWriter.writeFile(id, title, "error -> "
                                                + title + " is already in favourite list"));
                                    }
                                } else {
                                    arrayResult.add(fileWriter.writeFile(id, title,
                                            "error -> " + title + " is not seen"));
                                }
                            }
                            break;
                        case "view":
                            id = actions.get(i).getActionId();
                            username = actions.get(i).getUsername();
                            k = listUsers.getHistory(username);
                            title = actions.get(i).getTitle();
                            if (k != null) {
                                Integer views = listUsers.addViews(title, k);
                                if (views != 1) {
                                    arrayResult.add(fileWriter.writeFile(id, title, "success -> "
                                            + title + " was viewed with total views of " + views));
                                } else {
                                    arrayResult.add(fileWriter.writeFile(id, title, "success -> "
                                            + title + " was viewed with total views of " + views));
                                }
                            }
                            break;
                        case "rating":
                            id = actions.get(i).getActionId();
                            username = actions.get(i).getUsername();
                            k = listUsers.getHistory(username);
                            title = actions.get(i).getTitle();
                            Double grade = actions.get(i).getGrade();

                            if (k.containsKey(title)) {
                                if (actions.get(i).getSeasonNumber() == 0) {
                                    if (listMovies.addRating(title, username, grade)) {
                                        arrayResult.add(fileWriter.writeFile(id, title,
                                                "success -> " + title + " was rated with "
                                                        + grade + " by " + username));
                                        listUsers.addRating(username);
                                    } else {
                                        arrayResult.add(fileWriter.writeFile(id, title,
                                                "error -> " + title + " has been already rated"));
                                    }
                                } else {
                                    if (listSerials.addRating(title, username,
                                            grade, actions.get(i).getSeasonNumber())) {
                                        arrayResult.add(fileWriter.writeFile(id, title,
                                                "success -> " + title + " was rated with "
                                                        + grade + " by " + username));
                                        listUsers.addRating(username);
                                    } else {
                                        arrayResult.add(fileWriter.writeFile(id, title,
                                                "error -> " + title + " has been already rated"));
                                    }
                                }
                            } else {
                                arrayResult.add(fileWriter.writeFile(id, title,
                                        "error -> " + title + " is not seen"));
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "query":
                    switch (actions.get(i).getObjectType()) {
                        case "actors":
                            switch (actions.get(i).getCriteria()) {
                                case "average":
                                    id = actions.get(i).getActionId();
                                    StringBuilder message;
                                    ArrayList<Rating> allR;
                                    allR = listActors.getRating(listMovies, listSerials);
                                    int poz = 0;
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        allR.sort(Collections.reverseOrder(new MyComparator()));
                                    } else {
                                        allR.sort(new MyComparator());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), allR.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(allR.get(j).getName());
                                        } else {
                                            message.append(", ").append(allR.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            allR.toString(), message.toString()));
                                    break;

                                case "awards":
                                    ArrayList<AllActors> allAct;
                                    List<String> filter;
                                    filter = actions.get(i).getFilters().get(3);
                                    allAct = listActors.getAwards(filter);
                                    id = actions.get(i).getActionId();
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        allAct.sort(Collections.reverseOrder(new MyComparator2()));
                                    } else {
                                        allAct.sort(new MyComparator2());
                                    }
                                    if (allAct.size() != 0) {
                                        message = new StringBuilder("Query result: ["
                                                + allAct.get(0).getName());
                                        for (int j = 1; j < allAct.size(); j++) {
                                            message.append(", ").append(allAct.get(j).getName());
                                        }
                                    } else {
                                        message = new StringBuilder("Query result: [");
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;

                                case "filter_description":
                                    ArrayList<String> filterActors;
                                    filter = actions.get(i).getFilters().get(2);
                                    filterActors = listActors.getFilters(filter);
                                    id = actions.get(i).getActionId();
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        Collections.sort(filterActors);
                                    } else {
                                        filterActors.sort(Collections.reverseOrder());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), filterActors.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(filterActors.get(j));
                                        } else {
                                            message.append(", ").append(filterActors.get(j));
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));

                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "movies":
                            List<String> year;
                            List<String> genre;
                            StringBuilder message;
                            int poz;
                            switch (actions.get(i).getCriteria()) {
                                case "ratings":
                                    id = actions.get(i).getActionId();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    ArrayList<Rating> ratings = new ArrayList<>();
                                    listMovies.rateMovieFilters(ratings, genre, year);
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        ratings.sort(Collections.
                                                reverseOrder(new MyComparatorUnseen()));
                                    } else {
                                        ratings.sort(new MyComparatorUnseen());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), ratings.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(ratings.get(j).getName());
                                        } else {
                                            message.append(", ").append(ratings.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;
                                case "favorite":
                                    ArrayList<AllVideosFavorite> vFav;
                                    vFav = new ArrayList<>();
                                    id = actions.get(i).getActionId();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    int actNum = actions.get(i).getNumber();
                                    for (User user : users) {
                                        listMovies.favoriteMovie(user);
                                    }
                                    listMovies.favoriteMovie(vFav, genre, year);
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        vFav.sort(Collections.
                                                reverseOrder(new MyComparatorFavorite()));
                                    } else {
                                        vFav.sort(new MyComparatorFavorite());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actNum, vFav.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(vFav.get(j).getName());
                                        } else {
                                            message.append(", ").append(vFav.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));

                                    break;
                                case "longest":
                                    poz = 0;
                                    ArrayList<AllVideosDuration> allVideos = new ArrayList<>();
                                    id = actions.get(i).getActionId();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    listMovies.durationMovie(allVideos, genre, year);

                                    if (actions.get(i).getSortType().equals("asc")) {
                                        allVideos.sort(Collections.
                                                reverseOrder(new MyComparatorDuration()));
                                    } else {
                                        allVideos.sort(new MyComparatorDuration());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), allVideos.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(allVideos.get(j).getName());
                                        } else {
                                            message.append(", ").append(allVideos.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));

                                    break;
                                case "most_viewed":
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    id = actions.get(i).getActionId();
                                    ArrayList<AllVideosView> vView = new ArrayList<>();
                                    listMovies.viewMovie(vView, genre, year, listUsers);
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        vView.sort(Collections.
                                                reverseOrder(new MyComparatorView()));
                                    } else {
                                        vView.sort(new MyComparatorView());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), vView.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(vView.get(j).getName());
                                        } else {
                                            message.append(", ").append(vView.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "shows":
                            switch (actions.get(i).getCriteria()) {
                                case "ratings":
                                    id = actions.get(i).getActionId();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);

                                    ArrayList<Rating> ratings = new ArrayList<>();
                                    listSerials.rateSerialFilters(ratings, genre, year);
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        ratings.sort(Collections.
                                                reverseOrder(new MyComparatorUnseen()));
                                    } else {
                                        ratings.sort(new MyComparatorUnseen());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), ratings.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(ratings.get(j).getName());
                                        } else {
                                            message.append(", ").append(ratings.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;
                                case "favorite":
                                    ArrayList<AllVideosFavorite> vFav = new ArrayList<>();
                                    id = actions.get(i).getActionId();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);

                                    for (User user : users) {
                                        listSerials.favoriteSerials(user);
                                    }
                                    listSerials.favoriteSerials(vFav, genre, year);
                                    if (actions.get(i).getSortType().equals("asc")) {
                                        vFav.sort(Collections.
                                                reverseOrder(new MyComparatorFavorite()));
                                    } else {
                                        vFav.sort(new MyComparatorFavorite());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), vFav.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(vFav.get(j).getName());
                                        } else {
                                            message.append(", ").append(vFav.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;
                                case "longest":
                                    poz = 0;
                                    ArrayList<AllVideosDuration> allV = new ArrayList<>();
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    id = actions.get(i).getActionId();
                                    listSerials.durationSerial(allV, genre, year);

                                    if (actions.get(i).getSortType().equals("asc")) {
                                        allV.sort(Collections.
                                                reverseOrder(new MyComparatorDuration()));
                                    } else {
                                        allV.sort(new MyComparatorDuration());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), allV.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(allV.get(j).getName());
                                        } else {
                                            message.append(", ").append(allV.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));

                                    break;
                                case "most_viewed":
                                    year = actions.get(i).getFilters().get(0);
                                    genre = actions.get(i).getFilters().get(1);
                                    id = actions.get(i).getActionId();
                                    ArrayList<AllVideosView> vView = new ArrayList<>();
                                    listSerials.viewSerial(vView, genre, year, listUsers);

                                    if (actions.get(i).getSortType().equals("asc")) {
                                        vView.sort(Collections.
                                                reverseOrder(new MyComparatorView()));
                                    } else {
                                        vView.sort(new MyComparatorView());
                                    }
                                    message = new StringBuilder("Query result: [");
                                    poz = Math.min(actions.get(i).getNumber(), vView.size());
                                    for (int j = 0; j < poz; j++) {
                                        if (j == 0) {
                                            message.append(vView.get(j).getName());
                                        } else {
                                            message.append(", ").append(vView.get(j).getName());
                                        }
                                    }
                                    message.append("]");
                                    arrayResult.add(fileWriter.writeFile(id,
                                            " ", message.toString()));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "users":
                            ArrayList<AllUsers> allUsers = new ArrayList<>();
                            id = actions.get(i).getActionId();
                            poz = 0;
                            for (User user : users) {
                                if (user.getNumberRatings() != 0) {
                                    allUsers.add(new AllUsers(user.getUsername(),
                                            user.getNumberRatings()));
                                }
                            }
                            if (actions.get(i).getSortType().equals("asc")) {
                                allUsers.sort(Collections.reverseOrder(new MyComparatorUsers()));
                            } else {
                                allUsers.sort(new MyComparatorUsers());
                            }
                            message = new StringBuilder("Query result: [");
                            poz = Math.min(actions.get(i).getNumber(), allUsers.size());
                            for (int j = 0; j < poz; j++) {
                                if (j == 0) {
                                    message.append(allUsers.get(j).getName());
                                } else {
                                    message.append(", ").append(allUsers.get(j).getName());
                                }
                            }
                            message.append("]");
                            arrayResult.add(fileWriter.writeFile(id,
                                    " ", message.toString()));
                            break;
                        default:
                            break;
                    }
                    break;
                case "recommendation":
                    StringBuilder message;
                    switch (actions.get(i).getType()) {
                        case "standard":
                            username = actions.get(i).getUsername();
                            id = actions.get(i).getActionId();
                            int nr = listUsers.findUser(username);
                            if (nr == -1) {
                                message = new StringBuilder("Standard"
                                        + "Recommendation cannot be applied!");
                                arrayResult.add(fileWriter.writeFile(id,
                                        username, message.toString()));
                                break;
                            }
                            String video;
                            video = listMovies.firstMovie(users.get(nr));
                            if (video == null) {
                                arrayResult.add(fileWriter.writeFile(id, video,
                                        "StandardRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(id, video,
                                        "StandardRecommendation result: " + video));
                            }
                            break;
                        case "best_unseen":
                            int poz = 0;
                            id = actions.get(i).getActionId();
                            ArrayList<Rating> ratings = new ArrayList<>();
                            nr = listUsers.findUser(actions.get(i).getUsername());
                            if (nr == -1) {
                                message = new StringBuilder("BestRated"
                                        + "UnseenRecommendation cannot be applied!");
                                arrayResult.add(fileWriter.writeFile(id,
                                        " ", message.toString()));
                                break;
                            }
                            listMovies.rateMovie(ratings, users.get(nr).getHistory());
                            listSerials.rateSerial(ratings, users.get(nr).getHistory());
                            ratings.sort(new MyComparatorUnseen());
                            if (ratings.size() != 0) {
                                message = new StringBuilder("BestRatedUnseenRecommendation "
                                        + "result: " + ratings.get(0).getName());
                            } else {
                                message = new StringBuilder("BestRatedUnseenRecommendation"
                                        + " cannot be applied!");
                            }
                            arrayResult.add(fileWriter.writeFile(id,
                                    " ", message.toString()));
                            break;
                        case "popular":
                            username = actions.get(i).getUsername();
                            id = actions.get(i).getActionId();
                            nr = listUsers.findUser(username);
                            if (nr == -1
                                    || (!users.get(nr).getSubscriptionType().equals("PREMIUM"))) {
                                arrayResult.add(fileWriter.writeFile(id, username,
                                        "PopularRecommendation cannot be applied!"));
                                break;
                            }
                            ArrayList<GenreView> genreViews = new ArrayList<>();
                            genreViews.add(new GenreView("Tv movie", 0));
                            genreViews.add(new GenreView("Drama", 0));
                            genreViews.add(new GenreView("Fantasy", 0));
                            genreViews.add(new GenreView("Comedy", 0));
                            genreViews.add(new GenreView("Family", 0));
                            genreViews.add(new GenreView("War", 0));
                            genreViews.add(new GenreView("Sci-Fi & fantasy", 0));
                            genreViews.add(new GenreView("Crime", 0));
                            genreViews.add(new GenreView("Animation", 0));
                            genreViews.add(new GenreView("Science Fiction", 0));
                            genreViews.add(new GenreView("Action", 0));
                            genreViews.add(new GenreView("Horror", 0));
                            genreViews.add(new GenreView("Mystery", 0));
                            genreViews.add(new GenreView("Western", 0));
                            genreViews.add(new GenreView("Adventure", 0));
                            genreViews.add(new GenreView("Action & Adventure", 0));
                            genreViews.add(new GenreView("Romance", 0));
                            genreViews.add(new GenreView("Thriller", 0));
                            genreViews.add(new GenreView("Kids", 0));
                            genreViews.add(new GenreView("History", 0));

                            listMovies.createGenreView(genreViews);
                            listSerials.createGenreView(genreViews);

                            genreViews.sort(new MyComparatorGenreView());
                            AllGenreViews allViews = new AllGenreViews(genreViews);
                            video = allViews.getVideo(users.get(nr));
                            if (video == null) {
                                arrayResult.add(fileWriter.writeFile(id, null,
                                        "PopularRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(id, video,
                                        "PopularRecommendation result: " + video));
                            }
                            break;
                        case "favorite":
                            ArrayList<AllVideosFavorite> fav = new ArrayList<>();
                            id = actions.get(i).getActionId();
                            username = actions.get(i).getUsername();
                            nr = listUsers.findUser(username);
                            if (nr == -1
                                    || !users.get(nr).getSubscriptionType().equals("PREMIUM")) {
                                arrayResult.add(fileWriter.writeFile(id, username,
                                        "FavoriteRecommendation cannot be applied!"));
                                break;
                            }
                            for (User user : users) {
                                listMovies.favoriteMovie(user);
                                listSerials.favoriteSerials(user);
                            }
                            for (Movie movie : movies) {
                                fav.add(new AllVideosFavorite(movie.getTitle(),
                                        movie.getAppearanceFav()));
                            }
                            for (Serial serial : serials) {
                                fav.add(new AllVideosFavorite(serial.getTitle(),
                                        serial.getAppearanceFav()));
                            }

                            String favMovie = listMovies.getFavorite(users.get(nr), fav);
                            if (favMovie == null) {
                                arrayResult.add(fileWriter.writeFile(id, null,
                                        "FavoriteRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(id, favMovie,
                                        "FavoriteRecommendation result: " + favMovie));
                            }
                            break;
                        case "search":
                            String genre = actions.get(i).getGenre();
                            id = actions.get(i).getActionId();
                            ratings = new ArrayList<>();
                            nr = listUsers.findUser(actions.get(i).getUsername());
                            if (nr == -1
                                    || !users.get(nr).getSubscriptionType().equals("PREMIUM")) {
                                message = new StringBuilder("SearchRecommendation "
                                        + "cannot be applied!");
                                arrayResult.add(fileWriter.writeFile(id,
                                        " ", message.toString()));
                                break;
                            }
                            listMovies.rateMovieGenre(ratings, genre, users.get(nr).getHistory());
                            listSerials.rateSerialGenre(ratings, genre, users.get(nr).getHistory());
                            ratings.sort(Collections.reverseOrder(new MyComparator()));

                            if (ratings.size() != 0) {
                                message = new StringBuilder("SearchRecommendation result: [");
                                for (int j = 0; j < ratings.size(); j++) {
                                    if (j == 0) {
                                        message.append(ratings.get(j).getName());
                                    } else {
                                        message.append(", ").append(ratings.get(j).getName());
                                    }
                                }
                                message.append("]");
                                arrayResult.add(fileWriter.writeFile(id,
                                        " ", message.toString()));
                            } else {
                                message = new StringBuilder("SearchRecommendation"
                                        + " cannot be applied!");
                                arrayResult.add(fileWriter.writeFile(id,
                                        " ", message.toString()));
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }

        }
        fileWriter.closeJSON(arrayResult);
    }
}


