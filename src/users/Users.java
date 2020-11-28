package users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Users {
    private final List<User> users;

    public Users(final List<User> users) {
        this.users = users;
    }

    /**
     * returneaza istoricul unui utilizator dat ca parametru
     */
    public Map<String, Integer> getHistory(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user.getHistory();
            }
        }
        return null;
    }

    /**
     * returneaza pozitia din lista de useri a userului dat ca parametru
     */
    public int findUser(final String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returneaza lista de filme favorite a unui utilizator dat ca parametru
     */
    public ArrayList<String> getFavoriteMovies(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user.getFavoriteMovies();
            }
        }
        return null;
    }

    /**
     * verifica daca video-ul dat ca parametru este sau nu in istoric
     * daca nu este in istoric sau este in istoric, dar se afla
     * in lista de favorite, se va returna false
     * in caz contrar, se va returna true
     */
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

    /**
     * se cauta video-ul in istoricul dat ca parametru si se mareste
     * cu 1 numarul de vizualizari al acestuia
     */
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

    /**
     * creste numarul de rating-uri oferite de userul dat ca parametru
     */
    public void addRating(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                user.setNumberRatings(user.getNumberRatings() + 1);
            }
        }
    }

    /**
     *  returneaza numarul de views al unui video dat ca parametru
     */
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
