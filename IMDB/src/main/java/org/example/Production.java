package org.example;
import java.util.ArrayList;
import java.util.List;

public abstract class Production implements Comparable<Object> {
    String title;
    String type;
    List<String> directors;
    List<String> actors;
    List<Genre> genres;
    List<Rating> userRatings;
    String description;
    double averageRating;

    // Constructor
    public Production(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                      List<Rating> userRatings, String description) {
        this.title = title;
        this.type = type;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.userRatings = userRatings;
        this.description = description;
        this.averageRating = calculateAverageRating();
    }

    // Metoda pentru afișarea informațiilor specifice fiecărei subclase
    public abstract void displayInfo();

    // Metoda pentru afișarea ratingurilor utilizatorilor
    public void displayUserRatings() {
        if (userRatings == null || userRatings.isEmpty()) {
            System.out.println("No user ratings available.");
        } else {
            System.out.println("User Ratings:");
            for (Rating rating : userRatings) {
                System.out.println("Username: " + rating.getUsername());
                System.out.println("Rating: " + rating.getRating());
                System.out.println("Comments: " + rating.getComments());
                System.out.println("--------------");
            }
        }
    }
    public void addRating(Rating newRating) {
        if (newRating != null) {
            // Asigură că lista de evaluări este inițializată
            if (userRatings == null) {
                userRatings = new ArrayList<>();
            }

            // Adaugă noul rating la lista de evaluări
            userRatings.add(newRating);

            // Recalculează media după adăugarea noului rating
            averageRating = calculateAverageRating();
        } else {
            System.out.println("Invalid rating. Unable to add.");
        }
    }
    public  void removeRating(Rating rating){
        if(userRatings != null) {
            userRatings.remove(rating);
            averageRating = calculateAverageRating();
        }
    }



    // Metoda necesară sortării filmelor și serialelor în funcție de titlu
    @Override
    public int compareTo(Object o) {
        if (o instanceof Production) {
            Production otherProduction = (Production) o;
            return this.title.compareTo(otherProduction.title);
        }
        return 0;
    }

    double calculateAverageRating() {
        if (userRatings == null || userRatings.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (Rating rating : userRatings) {
            sum += rating.getRating();
        }

        return (double) sum / userRatings.size();
    }
    public int getNumberOfRatings() {
        if (userRatings != null) {
            return userRatings.size();
        } else {
            return 0;
        }
    }
    public String getType(){
        return type;
    }

    public String getTitle() {
        return title;
    }

    public List<Genre> getGenres() {return genres;}

    public List<String> getActors() {return actors;}

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description) {this.description = description;}
}