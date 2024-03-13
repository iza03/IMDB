package org.example;

import java.time.LocalDateTime;
import java.util.*;

public abstract class User<T extends Comparable<T>> implements Comparable<User<T>>, Observer {
    private String username;
    private int experience;
    private Information information;
    private AccountType accountType;

    private List<String> notifications;
    public SortedSet<String> favorites;
    private List<Observer> observers;
    private boolean amILoggedIn = false;
    public List<Request> addedRequests;


    public User(Information information, AccountType accountType, String username,
                int experience,List<String> notifications, SortedSet<String> favorites) {
        this.information = information;
        this.accountType = accountType;
        this.username = username;
        this.experience = experience;
        this.notifications = notifications;
        this.favorites = favorites;
        addedRequests = new ArrayList<>();
        observers = new ArrayList<>();
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void updateRequestNotification(String notification, User user) {
        System.out.println("update s a folosit");
        user.notifications.add(notification);
        System.out.println("Request Notification received: " + notification);
    }

    @Override
    public void updateRatingNotification(String notification) {
        notifications.add(notification);
        System.out.println("Rating Notification received: " + notification);
    }
    public void login(){
        this.amILoggedIn = true;
    }
    public void logout(){
        this.amILoggedIn = false;
    }
    public void addFavorite(String favorite) {
        favorites.add(favorite);
    }

    public void removeFavorite(String favorite) {
        favorites.remove(favorite);
    }
    public User findUserByUsername(String nameToFind) {
        IMDB imdb = IMDB.getInstance();
        return imdb.findUserByUsername(nameToFind);
    }
    public Request createRequest(RequestTypes requestType, LocalDateTime creationDate, String title, String description, String requesterUsername, String resolverUsername){
        Request r = new Request(requestType,creationDate,title,description,getUsername(), resolverUsername);
        if(resolverUsername.equals("ADMIN"))
            RequestsHolder.addTeamRequest(r);
        else{
            Staff staff = (Staff) findUserByUsername(resolverUsername);
            staff.addRequest(r);
        }
        User user = findUserByUsername(getUsername());
        user.addRequest(r);
        return r;
    }
    public void addRequest(Request r){
        addedRequests.add(r);
    }
    public void removeRequest(Request r){
        addedRequests.remove(r);
    }
    public void displayRequests(){
        System.out.println(getUsername() + " requests");
        for( Request request : addedRequests)
            request.displayInfo();
    }
    public void updateExperience(int points) {
        experience += points;
    }

    @Override
    public int compareTo(User otherUser) {
        return Long.compare(otherUser.experience, this.experience);
    }
    public boolean getAmILoggedIn(){
        return amILoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public Information getInformation() {
        return information;
    }


    public SortedSet<String> getFavorites() {return favorites;}


    public String getAccountType() {
        return accountType.toString();
    }

    public int getExperience() {
        return experience;
    }

    public List<String> getNotifications() {
        System.out.println("getnotif s a folosit");
        return notifications;
    }
    public static class Information {
        private Credentials credentials;
        private String name;
        private String country;
        private int age;
        private String gender;
        private LocalDateTime birthDate;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.name = builder.name;
            this.country = builder.country;
            this.age = builder.age;
            this.gender = builder.gender;
            this.birthDate = builder.birthDate;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public LocalDateTime getBirthDate() {
            return birthDate;
        }

        public static class InformationBuilder {
            private Credentials credentials;
            private String name;
            private String country;
            private int age;
            private String gender;
            private LocalDateTime birthDate;

            public InformationBuilder(Credentials credentials) {
                this.credentials = credentials;
            }

            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }
            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }
            public InformationBuilder age(int age) {
                this.age = age;
                return this;
            }
            public InformationBuilder gender(String gender) {
                this.gender = gender;
                return this;
            }
            public InformationBuilder birthDate(LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }
            public Information build() {
                return new Information(this);
            }
        }
    }
    public void displayInfo() {
        System.out.println("Username: " + getUsername());
        System.out.println("User Type: " + getAccountType());
        System.out.println("Experience: " + getExperience());

        System.out.println("Email: " + information.getCredentials().getEmail());
        System.out.println("Password: " + information.getCredentials().getPassword());
        System.out.println("Name: " + information.getName());
        System.out.println("Country: " + information.getCountry());
        System.out.println("Age: " + information.getAge());
        System.out.println("Gender: " + information.getGender());
        System.out.println("Birth Date: " + information.getBirthDate());

        if(favorites != null)
            displayFavorites();

        printNotifications();
    }
    public void displayFavorites(){

        if (!favorites.isEmpty()) {
            System.out.println("Favorite Productions and Actors:");
            for (String favorite : favorites) {
                System.out.println(favorite);
            }
        }
        else
            System.out.println("Your favorites list is empty.");
    }
    public void printNotifications() {
        if(notifications != null){
            System.out.println("Notifications:");
            for (String notification : notifications) {
                System.out.println(notification);
            }
        }
        else
            System.out.println("You don't have any notifications!");
    }

}

