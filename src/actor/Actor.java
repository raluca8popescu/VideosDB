package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;
    private final Integer awardsNumber;

    public Actor(final ActorInputData actorI) {
        this.name = actorI.getName();
        this.careerDescription = actorI.getCareerDescription();
        this.filmography = actorI.getFilmography();
        this.awards = actorI.getAwards();

        int number = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            number = number + entry.getValue();
        }
        this.awardsNumber = number;
    }

    public Integer getAwardsNumber() {
        return awardsNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
