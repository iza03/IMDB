package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager {

    private List<Rating> ratings;

    public Regular(Information information, String username, int experience, List<String> notifications, SortedSet<String> favorites) {
        super(information, AccountType.Regular, username, experience, notifications, favorites);
        ratings = new ArrayList<>();
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }
    public void removeRating(Rating rating){
        if(ratings != null)
            ratings.remove(rating);
    }
    public void displayRatings(){
        for( Rating rating : ratings){
            rating.displayInfo();
        }
    }

}

