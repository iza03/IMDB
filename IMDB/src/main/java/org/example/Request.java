package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request implements Observer{
    private RequestTypes requestType;
    private LocalDateTime creationDate;
    private String title;
    private String description;
    private String requesterUsername;
    private String resolverUsername;

    public Request(RequestTypes requestType, LocalDateTime creationDate, String title, String description, String requesterUsername, String resolverUsername){
        this.requestType = requestType;
        this.creationDate = creationDate;
        this.title = title;
        this.description = description;
        this.requesterUsername = requesterUsername;
        this.resolverUsername = resolverUsername;
    }

    public void displayInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Request Type: " + requestType);
        System.out.println("Creation Date: " + creationDate.format(formatter));
        System.out.println("Requester Username: " + requesterUsername);
        System.out.println("Resolver Username: " + resolverUsername);
        if ("MOVIE_ISSUE".equals(requestType.toString())) {
            System.out.println("Movie Title: " + title);
        } else if ("ACTOR_ISSUE".equals(requestType.toString())) {
            System.out.println("Actor name: " + title);
        }

        System.out.println("Description: " + description);
    }

    public String getRequesterUsername(){return requesterUsername;}
    public String getResolverUsername(){
        return resolverUsername;
    }
    public RequestTypes getRequestType(){
        return requestType;
    }
    public String getDescription(){
        return description;
    }

    @Override
    public void updateRequestNotification(String notification, User user) {
    }

    @Override
    public void updateRatingNotification(String notification) {

    }
}
