package actor;

import entertainment.Movies;
import entertainment.Serials;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actors {
    private final List<Actor> actors;

    public Actors(final List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Calculeaza rating-ul total al unor filme si seriale date ca parametru
     * si returneaza un ArrayList de Rating format din numele si rating-ul video-ului
     */
    public ArrayList<Rating> getRating(final Movies movies, final Serials serials) {
        ArrayList<Rating> allRatings = new ArrayList<>();
        Double r;
        Double s;
        double total;
        double rating = 0.0;
        int number = 0;
        for (Actor actor : actors) {
            rating = 0.0;
            number = 0;
            for (int j = 0; j < actor.getFilmography().size(); j++) {
                r = movies.isMovie(actor.getFilmography().get(j));
                if (r != -1) {
                    if (r != 0) {
                        rating += r;
                        number++;
                    }
                } else {
                    s = serials.isSerial(actor.getFilmography().get(j));
                    if (s != -1.0) {
                        if (s != 0) {
                            rating += s;
                            number++;
                        }
                    }
                }
            }
            if (rating == 0.0 && number == 0) {
                total = 0.0;
            } else {
                total = rating / (double) number;
            }
            if (total != 0.0) {
                allRatings.add(new Rating(actor.getName(), total));
            }

        }
        return allRatings;
    }

    /**
     * Verifica, pe rand, daca un actor are toate premiile continute
     * de filters, iar in cazul in care le contine pe toate, va fi
     * adaugat in lista AllActors care va fi returnata
     * de catre functie
     */
    public ArrayList<AllActors> getAwards(final List<String> filters) {
        int found = 0;
        ArrayList<AllActors> allActors = new ArrayList<>();
        for (Actor actor : actors) {
            found = 0;
            for (String filter : filters) {
                for (Map.Entry<ActorsAwards, Integer> entry
                        : actor.getAwards().entrySet()) {
                    if (entry.getKey().toString().equals(filter)) {
                        found++;
                    }
                }
            }
            if (found == filters.size()) {
                allActors.add(new AllActors(actor.getName(),
                        actor.getAwardsNumber()));
            }
        }
        return allActors;
    }
    /**
     * Verifica daca partWord este continut in fullString
     */
    private boolean isExactWord(final String fullString, final String partWord) {
        String pattern = "\\b" + partWord + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullString);
        return m.find();
    }

    /**
     * Pentru fiecare descriere a unui actor, inlocuieste toate caracterele
     * cu spatii albe, apoi, prin intermediul functiei isExactWird,se verifica daca
     * descrierea contine string-urile din filters.
     * In cazul in care sunt continute, se adauga numele actorului in lista
     * de string-uri care va fi returnata de catre functie
     */
    public ArrayList<String> getFilters(final List<String> filters) {
        String description;
        ArrayList<String> filterActors = new ArrayList<>();
        int contr = 0;

        for (Actor actor : actors) {
            contr = 0;
            description = actor.getCareerDescription();
            String desc = description.replaceAll("[,'.-:!?)(&]", " ");
            String[] s = desc.split(" ");
            for (String filter : filters) {
                for (String value : s) {
                    if (isExactWord(value.toLowerCase(), filter.toLowerCase())) {
                        contr++;
                        break;
                    }
                }
            }
            if (contr == filters.size()) {
                filterActors.add(actor.getName());
            }
        }
        return filterActors;
    }
}
