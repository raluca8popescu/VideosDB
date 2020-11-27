package users;

import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.List;

public final class Action {
    private final int actionId;
    private final String actionType;
    private final String type;
    private final String username;
    private final String objectType;
    private final String sortType;
    private final String criteria;
    private final String title;
    private final String genre;
    private final int number;
    private final double grade;
    private final int seasonNumber;
    private List<List<String>> filters = new ArrayList<>();

    public Action(final ActionInputData actionI) {
        this.actionId = actionI.getActionId();
        this.actionType = actionI.getActionType();
        this.type = actionI.getType();
        this.username = actionI.getUsername();
        this.genre = actionI.getGenre();
        this.objectType = actionI.getObjectType();
        this.sortType = actionI.getSortType();
        this.criteria = actionI.getCriteria();
        this.number = actionI.getNumber();
        this.title = actionI.getTitle();
        this.grade = actionI.getGrade();
        this.seasonNumber = actionI.getSeasonNumber();

    }
    public void setFilters(List<List<String>> filters) {
        this.filters = filters;
    }
    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumber() {
        return number;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return "ActionInputData{"
                + "actionId=" + actionId
                + ", actionType='" + actionType + '\''
                + ", type='" + type + '\''
                + ", username='" + username + '\''
                + ", objectType='" + objectType + '\''
                + ", sortType='" + sortType + '\''
                + ", criteria='" + criteria + '\''
                + ", title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", number=" + number
                + ", grade=" + grade
                + ", seasonNumber=" + seasonNumber
                + ", filters=" + filters
                + '}' + "\n";
    }
}
