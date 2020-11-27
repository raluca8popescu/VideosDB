package entertainment;

import users.User;

import java.util.List;
import java.util.Map;

public final class AllGenreViews {
   private final List<GenreView> genre;

    public AllGenreViews(final List<GenreView> genre) {
        this.genre = genre;
    }

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
