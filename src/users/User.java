package users;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private int numberRatings;

    public User(final UserInputData userI) {
        this.username = userI.getUsername();
        this.subscriptionType = userI.getSubscriptionType();
        this.favoriteMovies = userI.getFavoriteMovies();
        this.history = userI.getHistory();
        this.numberRatings = 0;
    }

    public void setNumberRatings(int numberRatings) {
        this.numberRatings = numberRatings;
    }

    public int getNumberRatings() {
        return numberRatings;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
