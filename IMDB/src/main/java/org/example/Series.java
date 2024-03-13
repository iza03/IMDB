package org.example;

import java.util.List;
import java.util.Map;

public class Series extends Production {
    public int releaseYear;
    public int numberOfSeasons;
    private Map<String, List<Episode>> seasons;

    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                  List<Rating> userRatings, String description, int releaseYear, int numberOfSeasons,
                  Map<String, List<Episode>> seasons) {
        super(title, type, directors, actors, genres, userRatings, description);
        this.releaseYear = releaseYear;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.averageRating = calculateAverageRating();
    }

    @Override
    public void displayInfo() {
        System.out.println("Series Title: " + title);
        System.out.println("Type: " + type);
        System.out.println("Directors: " + directors);
        System.out.println("Actors: " + actors);
        System.out.println("Genres: " + genres);
        displayUserRatings();
        System.out.println("Description: " + description);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Number of Seasons: " + numberOfSeasons);
        System.out.println("Seasons and Episodes:");
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            System.out.println(entry.getKey() + ":"); //Seasons
            for (Episode episode : entry.getValue()) {
                episode.displayInfo();
            }
        }
    }

    public void setNumberOfSeasons(int nrSeasons) {this.numberOfSeasons = nrSeasons;
    }
    public void setReleaseYear(int year) {this.releaseYear = year;
    }
}