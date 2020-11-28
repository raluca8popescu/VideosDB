package entertainment;

import users.User;

import java.util.List;
import java.util.Map;

public final class AllGenreViews {
   private final List<GenreView> genre;

    public AllGenreViews(final List<GenreView> genre) {
        this.genre = genre;
    }

    /**
     * returneaza primul video din lista de video-uri AllGenreViews
     * care nu se afla in istoricul userului dat ca parametru
     */
    public String getVideo(final User username) {
        Map<String, Integer> history = username.getHistory();
        for (GenreView genreView : genre) {
            for (int j = 0; j < genreView.videos.size(); j++) {
                if (!history.containsKey(genreView.videos.get(j))) {
                    return genreView.videos.get(j);
                }
            }
        }
        return null;
    }
}
