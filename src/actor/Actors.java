package actor;

import entertainment.Movies;
import entertainment.Serials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actors {
    private final List<Actor> actors;

    public Actors(final List<Actor> actors) {
        this.actors = actors;
    }

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

    public ArrayList<AllActors> getAwards(final List<String> filters) {
        int found = 0;
        ArrayList<AllActors> allActors = new ArrayList<>();
        for (int j = 0; j < actors.size(); j++) {
            found = 0;
            for (String filter : filters) {
                for (Map.Entry<ActorsAwards, Integer> entry
                        : actors.get(j).getAwards().entrySet()) {
                    if (entry.getKey().toString().equals(filter)) {
                        found++;
                    }
                }
            }
            if (found == filters.size()) {
//                System.out.println(actor.getName() + " "+ actor.getAwardsNumber()) ;
                allActors.add(new AllActors(actors.get(j).getName(),
                        actors.get(j).getAwardsNumber()));
            }
        }
        return allActors;
    }

    private boolean isContainExactWord(final String fullString, final String partWord) {
        String pattern = "\\b" + partWord + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullString);
        return m.find();
    }

    public ArrayList<String> getFilters(final List<String> filters) {
        String description;
        ArrayList<String> filterActors = new ArrayList<>();
        int contor = 0;

        for (Actor actor : actors) {
            contor = 0;
            description = actor.getCareerDescription();
            String desc = description.replaceAll("[,'.-:!?)(&]", " ");
            List<String> s = Arrays.asList(desc.split(" "));
            for (String filter : filters) {
                for (String value : s) {
                    if (isContainExactWord(value.toLowerCase(), filter.toLowerCase())) {
                        contor++;
                        break;
                    }
                }
            }
            if (contor == filters.size()) {
                filterActors.add(actor.getName());
            }
        }
        return filterActors;
    }
}
