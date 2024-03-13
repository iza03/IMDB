package org.example;

import java.util.List;

public class Movie extends Production {
    public String duration;
    public int releaseYear;

    // Constructor
    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> userRatings, String description, String duration, int releaseYear) {
        super(title,type, directors, actors, genres, userRatings, description);
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.averageRating = calculateAverageRating();
    }
    public String getDuration() {
        return duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public void displayInfo() {
        System.out.println("Movie Title: " + title);
        System.out.println("Type: " + type);
        System.out.println("Directors: " + directors);
        System.out.println("Actors: " + actors);
        System.out.println("Genres: " + genres);
        displayUserRatings();
        System.out.println("Average Rating: " + averageRating);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration);
        System.out.println("Release Year: " + releaseYear);
    }

    public void setDuration(String duration) {this.duration = duration;
    }
    public void setReleaseYear(int year) {this.releaseYear = year;
    }
}