package org.example;

public class Rating {
    private String username;
    private int rating;
    private String comments;

    public Rating(String username, int rating, String comments) {
        this.username = username;
        this.rating = rating;
        this.comments = comments;
    }
    public void displayInfo(){
        System.out.println("Username: " + username );
        System.out.println("Rating: " + rating);
        System.out.println("Comments: " + comments);
    }
    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }
}

