package users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Users {
    private final List<User> users;

    public Users(final List<User> users) {
        this.users = users;
    }

    public Map<String, Integer> getHistory(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user.getHistory();
            }
        }
        return null;
    }

    public int findUser(final String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<String> getFavoriteMovies(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user.getFavoriteMovies();
            }
        }
        return null;
    }

    public boolean addFavorite(final String title,
                               final Map<String, Integer> k, final ArrayList<String> favorite) {
        for (Map.Entry<String, Integer> entry : k.entrySet()) {
            if (entry.getKey().equals(title)) {
                if (favorite.contains(title)) {
                    return false;
                } else {
                    favorite.add(title);
                    return true;
                }
            }
        }
        return false;
    }

    public Integer addViews(final String title, final Map<String, Integer> k) {
        if (k.containsKey(title)) {
            for (Map.Entry<String, Integer> entry : k.entrySet()) {
                if (entry.getKey().equals(title)) {
                    Integer views = entry.getValue() + 1;
                    k.put(entry.getKey(), views);
                    return views;
                }
            }
        } else {
            String s = String.valueOf(title);
            k.put(s, 1);
            return 1;
        }
        return 0;
    }

    public void addRating(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                user.setNumberRatings(user.getNumberRatings() + 1);
            }
        }
    }

    public int getViews(final String video) {
        int views = 0;
        for (User user : users) {
            Map<String, Integer> history = user.getHistory();
            if (history.containsKey(video)) {
                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    if (entry.getKey().equals(video)) {
                        views += entry.getValue();
                    }
                }
            }
        }
        return views;
    }

}
