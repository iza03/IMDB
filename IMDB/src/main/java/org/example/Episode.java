package org.example;

public class Episode {
    private String episodeName;
    private String duration;

    public Episode(String episodeName, String duration) {
        this.episodeName = episodeName;
        this.duration = duration;
    }
    public void displayInfo() {
        System.out.println("Episode Name: " + episodeName);
        System.out.println("Duration: " + duration);
       }
}