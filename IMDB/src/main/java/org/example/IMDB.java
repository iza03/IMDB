package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IMDB<T extends Comparable<T>> {
    private List<User> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private UserFactory<T> userFactory;
    Scanner scanner;
    private static IMDB instance;

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    private IMDB() {
        users = new ArrayList<>();
        actors = new ArrayList<>();
        requests = new ArrayList<>();
        productions = new ArrayList<>();
        this.userFactory = new UserFactory<>();
        scanner = new Scanner(System.in);
    }
    public void run() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        loadActorsFromJson(ACTORS_JSON_PATH);
        loadRequestsJson(REQUESTS_JSON_PATH);
        loadUsersFromJson(ACCOUNTS_JSON_PATH);
        loadProductionsFromJson(PRODUCTIONS_JSON_PATH);
        addActorsFromProductions(productions,actors);
        processRequests(requests);
        addRequests();
        addRatingsToUser();
        System.out.println("Choose how to use:");
        System.out.println("1. Terminal");
        System.out.println("2. Graphic Interface");

        try {
            int choice = 0;
            boolean validChoice = false;

            while (!validChoice) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            terminal(login());
                            validChoice = true;
                            break;
                        case 2:
                            System.out.println("Graphic interface not available");
                            validChoice = true;
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                            scanner.nextLine();
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number. Try again.");
                    scanner.nextLine();
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            run();
        }
    }
    private void terminal(User user) {

        if (user != null) {
            user.login();
            showOptions(user);
            chooseOption(user);
        }
        else
            System.out.println("You have logged out successfully!");
        scanner.close();
    }

private User login() {
    System.out.println("Please enter your credentials!");
    System.out.print("Email: ");
    String email = scanner.next();

    System.out.print("Password: ");
    String password = scanner.next();

    for (User user : users) {
        if (user.getInformation().getCredentials().getEmail().equals(email) && user.getInformation().getCredentials().getPassword().equals(password)) {
            return user;
        }
    }

    System.out.println("Failed authentication! Please try again.");
    return login();
}

    private void showOptions(User user){
        System.out.println("Logged in user: " + user.getUsername());
        System.out.println("User experience: " + user.getExperience());
        System.out.println("Choose action:");

        System.out.println("1) View production details");
        System.out.println("2) View actor details");
        System.out.println("3) View notifications");
        System.out.println("4) Search for actor/movie/series");
        System.out.println("5) Add/Delete actor/movie/series to/from favorites");

        if (user instanceof Admin) {
            System.out.println("6) Add/Delete user from system");
            System.out.println("7) Add/Delete actor/movie/series from system");
            System.out.println("8) View and solve a request");
            System.out.println("9) Update movie/series details");
            System.out.println("10) Update actor details");
            System.out.println("11) Logout");
        } else if (user instanceof Contributor) {
            System.out.println("6) Create/Remove a request");
            System.out.println("7) Add/Delete actor/movie/series from system");
            System.out.println("8) View and solve a request");
            System.out.println("9) Update movie/series details");
            System.out.println("10) Update actor details");
            System.out.println("11) Logout");
        } else {
            System.out.println("6) Create/Remove a request");
            System.out.println("7) Add/Delete a rating for a movie/series");
            System.out.println("8) Logout");
        }
    }
    private void chooseOption(User user){
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (user instanceof Admin) {
            switch (choice) {
                case 1:
                    printProductionChoices(user);
                    break;
                case 2:
                    printActorChoices(user);
                    break;
                case 3:
                    user.printNotifications();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 4:
                    searchActorProduction(user);
                    break;
                case 5:
                    addDeleteFavorites(user);
                    break;
                case 6:
                    displayUsers();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 7:
                    addDeleteFromSystem(user);
                    break;
                case 8:
                    RequestsHolder.displayAdminTeamRequests();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 9:
                    updateProductionDetails((Staff) user);
                    break;
                case 10:
                    System.out.println("ai ales optiunea 10");
                    break;
                case 11:
                    user.logout();
                    user = optionsAfterLogout(user);
                    terminal(user);
                    break;
                default:
                    System.out.println("Invalid option!Choose again!");
                    chooseOption(user);
            }

        } else if (user instanceof Contributor) {
            switch (choice){
                case 1:
                    printProductionChoices(user);
                    break;
                case 2:
                    printActorChoices(user);
                    break;
                case 3:
                    user.printNotifications();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 4:
                    searchActorProduction(user);
                    break;
                case 5:
                    addDeleteFavorites(user);
                    break;
                case 6:
                    createRemoveRequest(user);
                    break;
                case 7:
                    addDeleteFromSystem(user);
                    break;
                case 8:
                    ((Staff)user).displayAssignedRequests();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 9:
                    updateProductionDetails((Staff) user);
                    break;
                case 10:
                    System.out.println("ai ales optiunea 10");
                    break;
                case 11:
                    user.logout();
                    user = optionsAfterLogout(user);
                    terminal(user);
                    break;
                default:
                    System.out.println("Invalid option!Choose again!");
                    chooseOption(user);
                    break;
            }

        } else {
            switch (choice){
                case 1:
                    printProductionChoices(user);
                    break;
                case 2:
                    printActorChoices(user);
                    break;
                case 3:
                    user.printNotifications();
                    System.out.println("Press any key and enter to go back to Menu");
                    scanner.next();
                    showOptions(user);
                    chooseOption(user);
                    break;
                case 4:
                    searchActorProduction(user);
                    break;
                case 5:
                    addDeleteFavorites(user);
                    break;
                case 6:
                    createRemoveRequest(user);
                    break;
                case 7:
                    addDeleteRating(user);
                    break;
                case 8:
                    user.logout();
                    user = optionsAfterLogout(user);
                    terminal(user);
                    break;
                default:
                    System.out.println("Invalid option! Choose again!");
                    chooseOption(user);
                    break;
            }

        }
    }
    private void updateProductionDetails(Staff staff) {
        try {
            scanner.nextLine();
            staff.displayAddedProductionsActors();
            System.out.println("Enter production title:");
            String productionTitle = scanner.nextLine();
            Production production = findProductionByName(productionTitle);

            if (production == null) {
                System.out.println("Production not found in the system.");

                return;
            }

            if (!staff.getAddedProductionsActors().contains(production.getTitle())) {
                System.out.println("You can't update a production you haven't added in the system");
            }
            production.displayInfo();
            System.out.println("Enter new title:");
            String title = scanner.nextLine();
            System.out.println("Enter new description:");
            String description = scanner.nextLine();
            System.out.println("Enter new release year:");
            int year = scanner.nextInt();

            if (production.type.equals("Movie")) {
                scanner.nextLine();
                System.out.println("Enter duration in minutes:");
                String duration = scanner.nextLine();
                ((Movie) production).setDuration(duration + " minutes");
                ((Movie) production).setReleaseYear(year);
            } else if (production instanceof Series) {
                System.out.println("Enter number of seasons:");
                int nrSeasons = scanner.nextInt();
                ((Series) production).setNumberOfSeasons(nrSeasons);
                ((Series) production).setReleaseYear(year);
            }

            production.setTitle(title);
            production.setDescription(description);
            production.displayInfo();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid value. Try again.");
            scanner.nextLine();
        }
    }

    private void addDeleteFromSystem(User user){
        try {
            System.out.println("Would you like to:");
            System.out.println("1) Add an actor/production");
            System.out.println("2) Remove an actor/production");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                addActorProduction((Staff) user);
            }
            else if (choice == 2){
                deleteActorProduction((Staff) user);
            }
            else {
                System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                addDeleteFromSystem(user);
                return;
            }

            System.out.println("Press any key and enter to go back to Menu");
            scanner.next();
            showOptions(user);
            chooseOption(user);

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            addDeleteFromSystem(user);
        }
    }
    public void deleteActorProduction(Staff staff) {
        try {
            staff.displayAddedProductionsActors();

            System.out.println("What would you like to delete?");
            System.out.println("1) A movie/series");
            System.out.println("2) An actor");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter the title of the movie/series you want to delete:");
                    String titleToDelete = scanner.nextLine();
                    Production productionToDelete = findProductionByName(titleToDelete);

                    if (productionToDelete != null && staff.getAddedProductionsActors().stream()
                            .anyMatch(item -> item.toString().equalsIgnoreCase(titleToDelete))) {
                        staff.removeProductionsActors(productionToDelete.getTitle());
                        productions.remove(productionToDelete);

                        System.out.println("Movie/Series deleted successfully.");
                    } else {
                        System.out.println("Invalid title or not added by this staff member.");
                    }
                    break;

                case 2:
                    System.out.println("Enter the name of the actor you want to delete:");
                    String actorToDeleteName = scanner.nextLine();
                    Actor actorToDelete = findActorByName(actorToDeleteName);

                        if (actorToDelete != null && staff.getAddedProductionsActors().stream()
                                .anyMatch(item -> item.toString().equalsIgnoreCase(actorToDeleteName))) {
                        staff.removeProductionsActors(actorToDelete.getName());
                        actors.remove(actorToDelete);

                        System.out.println("Actor deleted successfully.");
                    } else {
                        System.out.println("Invalid actor name or not added by this staff member.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                    break;
            }
            staff.displayAddedProductionsActors();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            deleteActorProduction(staff);
        }
    }

    public void addActorProduction(Staff staff) {
        try {
            System.out.println("What would you like to add?");
            System.out.println("1) An actor");
            System.out.println("2) A movie/series");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter actor name:");
                    String actorName = scanner.nextLine();
                    Actor existingActor = findActorByName(actorName);
                    if (existingActor != null) {
                        System.out.println("Actor already exists in the system.");
                        break;
                    }
                    System.out.println("Enter actor biography");
                    String bio = scanner.nextLine();
                    Actor newActor = new Actor(actorName, new ArrayList<>(), bio);
                    actors.add(newActor);
                    staff.addProductionsActors(newActor.getName());
                    break;

                case 2:
                    System.out.println("Enter production title:");
                    String productionTitle = scanner.nextLine();
                    Production existingProduction = findProductionByName(productionTitle);
                    if (existingProduction != null) {
                        System.out.println("Production already exists in the system.");
                        break;
                    }
                    System.out.println("Enter production type, movie/series");
                    String type = scanner.nextLine().toLowerCase();
                    if (!type.equals("movie") && !type.equals("series")) {
                        throw new IllegalArgumentException("Invalid production type. Please enter 'movie' or 'series'.");
                    }

                    System.out.println("Enter description");
                    String description = scanner.nextLine();
                    System.out.println("Enter release year");
                    int year = scanner.nextInt();
                    Production newProduction = null;
                    if (type.equals("movie")) {
                        System.out.println("Enter duration in minutes");
                        String duration = scanner.nextLine();
                        newProduction = new Movie(productionTitle, "Movie", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), description, duration + " minutes", year);
                    } else {
                        System.out.println("Enter number of seasons");
                        int nrseasons = scanner.nextInt();
                        newProduction = new Series(productionTitle, "Series", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), description, year, nrseasons, Collections.emptyMap());
                    }
                    productions.add(newProduction);
                    staff.addProductionsActors(newProduction.getTitle());
                    if(staff instanceof Contributor){
                        ExperienceStrategy experienceStrategy = new Experience();
                        int experience = experienceStrategy.calculateExperience(ActionType.ADD_ITEM);
                        staff.updateExperience(experience);
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                    addActorProduction(staff);
                    break;
            }
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. " + e.getMessage());
            scanner.nextLine();
            addActorProduction(staff);
        }
    }

    private void addDeleteRating(User user){
        try {
            System.out.println("Would you like to:");
            System.out.println("1) Add a rating");
            System.out.println("2) Remove a rating");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                addRating(user);
            }
            else if (choice == 2){
                deleteRating(user);
            }
            else {
                System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                addDeleteRating(user);
                return;
            }

            System.out.println("Press any key and enter to go back to Menu");
            scanner.next();
            showOptions(user);
            chooseOption(user);

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            addDeleteRating(user);
        }
    }
    private void deleteRating(User user) {
        try {
            ((Regular) user).displayRatings();

            System.out.println("Type the comment of the production for which you want to remove the rating:");
            String comment = scanner.nextLine();
            Production productionToRemove = null;
            Rating ratingToRemove = null;
            for (Production p : productions) {
                for (Rating r : p.getUserRatings()) {
                    if (r.getComments().equals(comment) && r.getUsername().equals(user.getUsername())) {
                        productionToRemove = p;
                        ratingToRemove = r;
                        break;
                    }
                }
            }
            if (productionToRemove != null && ratingToRemove != null) {
                productionToRemove.removeRating(ratingToRemove);
                ((Regular) user).removeRating(ratingToRemove);
                ExperienceStrategy experienceStrategy = new Experience();
                int experience = - experienceStrategy.calculateExperience(ActionType.REVIEW);
                user.updateExperience(experience);

            } else {
                System.out.println("Rating not found for the given comment. Try again!");
                deleteRating(user);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private void addRating(User user) {
        try {
            System.out.println("What would you like to give a rating to? Type the title.");
            String title = scanner.nextLine();
            Production production = findProductionByName(title);

            if (production != null) {
                if (hasRatedProduction(production, user)) {
                    System.out.println("You have already rated this production. You cannot add another rating.");
                    return;
                }

                System.out.println("What rating would you like to give it? Insert a number between 1 and 10");

                int rating = 0;
                boolean validRating = false;

                while (!validRating) {
                    try {
                        rating = Integer.parseInt(scanner.nextLine());
                        if (rating < 1 || rating > 10) {
                            throw new IllegalArgumentException();
                        }
                        validRating = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid input. Please enter a number between 1 and 10. Try again.");
                    }
                }

                System.out.println("Add a comment");
                String comment = scanner.nextLine();
                Rating userRating = new Rating(user.getUsername(), rating, comment);
                production.addRating(userRating);
                ((Regular) user).addRating(userRating);
                ExperienceStrategy experienceStrategy = new Experience();
                int experience = experienceStrategy.calculateExperience(ActionType.REVIEW);
                user.updateExperience(experience);

                System.out.println("Rating added successfully!");
            } else {
                System.out.println("Production not found. Try again.");
                addRating(user);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private boolean hasRatedProduction(Production production, User user) {
        if (production != null && user != null) {
            for (Rating rating : production.getUserRatings()) {
                if (rating.getUsername().equals(user.getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }


    private void createRemoveRequest(User user){
        try {
        System.out.println("Do you want to:");
        System.out.println("1) Create a request");
        System.out.println("2) Remove a request");
        int choice = scanner.nextInt();

        if (choice == 1)
            createRequest(user);
        else if (choice == 2){
            scanner.nextLine();
            deleteRequests(user);
        }
        else {
            System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
            createRemoveRequest(user);
            return;
        }

        System.out.println("Press any key and enter to go back to Menu");
        scanner.next();
        showOptions(user);
        chooseOption(user);

    } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number. Try again.");
        scanner.nextLine();
        createRemoveRequest(user);
    }

    }
    private void createRequest(User user) {
        try {
            System.out.println("What type of request would you like to make?");
            System.out.println("1) " + RequestTypes.ACTOR_ISSUE);
            System.out.println("2) " + RequestTypes.MOVIE_ISSUE);
            System.out.println("3) " + RequestTypes.DELETE_ACCOUNT);
            System.out.println("4) " + RequestTypes.OTHERS);

            RequestTypes requestType = RequestTypes.OTHERS;
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1)
                requestType = RequestTypes.ACTOR_ISSUE;
            else if (choice == 2)
                requestType = RequestTypes.MOVIE_ISSUE;
            else if (choice == 3)
                requestType = RequestTypes.DELETE_ACCOUNT;


            switch (requestType) {
                case ACTOR_ISSUE:
                    System.out.println("Type the name of the actor");
                    String name = scanner.nextLine();
                    if (findActorByName(name) == null) {
                        System.out.println("Couldn't find the actor. Try again!");
                        createRequest(user);
                        return;
                    }
                    String resolverUsername = findUserByAddedProductionOrActor(name);

                    System.out.println("Type the description of the request");
                    String description = scanner.nextLine();
                    user.createRequest(RequestTypes.ACTOR_ISSUE, LocalDateTime.now(), name, description, user.getUsername(), resolverUsername);
                    break;

                case MOVIE_ISSUE:
                    System.out.println("Type the title of the movie/series");
                    String title = scanner.nextLine();
                    if (findProductionByName(title) == null) {
                        System.out.println("Couldn't find the production. Try again!");
                        createRequest(user);
                        return;
                    }

                    System.out.println("Type the description of the request");
                    String description1 = scanner.nextLine();
                    user.createRequest(RequestTypes.MOVIE_ISSUE, LocalDateTime.now(), title, description1, user.getUsername(), "natalie_brown_2021");
                    break;

                case DELETE_ACCOUNT:
                case OTHERS:
                    System.out.println("Type the description of the request");
                    String description3 = scanner.nextLine();
                    user.createRequest(requestType, LocalDateTime.now(), "", description3, user.getUsername(), "ADMIN");
                    RequestsHolder.displayAdminTeamRequests();
                    break;
                default:
                    break;
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            createRequest(user);
        }
    }
    public void deleteRequests(User user) {
        user.displayRequests();

        System.out.println("What request would you like to remove? Type the description you gave the request");

        String description = scanner.nextLine();

        Request r = findRequestByDescription(user, description);

        if (r != null) {
            user.removeRequest(r);

            if (r.getResolverUsername().equals("ADMIN")) {
                RequestsHolder.removeTeamRequest(r);
            } else {
                Staff resolver = (Staff) findUserByUsername(r.getResolverUsername());
                if (resolver != null) {
                    resolver.removeRequest(r);
                } else {
                    System.out.println("Resolver not found.");
                }
            }
        } else {
            System.out.println("Request not found. Try again.");
            deleteRequests(user);
        }
    }

    private Request findRequestByDescription(User user, String description) {
        for (Object request : user.addedRequests) {
            if (((Request) request).getDescription().equals(description)) {
                return (Request) request;
            }
        }
        return null;
    }

    public String findUserByAddedProductionOrActor(String productionOrActor) {
        for (User user : users) {
            if (user instanceof Contributor || user instanceof Admin) {
                SortedSet<String> addedProductionsActors = ((Staff) user).getAddedProductionsActors();
                if (addedProductionsActors != null) {
                    for (String addedProductionOrActor : addedProductionsActors) {
                        if (addedProductionOrActor.equalsIgnoreCase(productionOrActor)) {
                            return user.getUsername();
                        }
                    }
                }
            }
        }
        return null;
    }

    private void addDeleteFavorites(User user) {
        try {
            System.out.println("Do you want to:");
            System.out.println("1) Add to favorites");
            System.out.println("2) Remove from favorites?");
            int choice = scanner.nextInt();

            if (choice == 1)
                addFavorites(user);
            else if (choice == 2)
                deleteFavorites(user);
            else {
                System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                addDeleteFavorites(user);
                return;
            }

            System.out.println("Press any key and enter to go back to Menu");
            scanner.next();
            showOptions(user);
            chooseOption(user);

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            addDeleteFavorites(user);
        }
    }

    private void addFavorites(User user) {
        try {
            System.out.println("What would you like to add?");
            System.out.println("1) An actor");
            System.out.println("2) A movie/series");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                Actor actor = searchActor();
                user.addFavorite(actor.getName());
                user.displayFavorites();
            } else if (choice == 2) {
                Production production = searchProduction();
                user.addFavorite(production.getTitle());
                user.displayFavorites();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                addFavorites(user);
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            addFavorites(user);
        }
    }

    private void deleteFavorites(User user) {
        try {
            user.displayFavorites();
            if(!user.favorites.isEmpty()) {
                System.out.println("What would you like to delete?");
                System.out.println("1) An actor");
                System.out.println("2) A movie/series");
                int choice = scanner.nextInt();

                scanner.nextLine();

                if (choice == 1) {
                    Actor actor = searchActor();
                    user.removeFavorite(actor.getName());
                } else if (choice == 2) {
                    Production production = searchProduction();
                    user.removeFavorite(production.getTitle());
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2. Try again.");
                    deleteFavorites(user);
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            deleteFavorites(user);
        }
    }

    private void searchActorProduction(User user) {
        try {
            System.out.println("What do you want to search?");
            System.out.println("1) An actor");
            System.out.println("2) A movie or series");

            int choice = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    if (choice != 1 && choice != 2) {
                        throw new IllegalArgumentException();
                    }
                    validChoice = true;
                } catch (InputMismatchException | IllegalArgumentException e) {
                    System.out.println("Invalid input. Please enter 1 or 2. Try again.");
                    scanner.nextLine();
                }
            }

            scanner.nextLine();

            if (choice == 1) {
                searchActor();
                }
            else {
                searchProduction();
            }

            System.out.println("Press any key and enter to go back to Menu");
            scanner.next();
            showOptions(user);
            chooseOption(user);

        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number. Try again.");
            scanner.nextLine();
            searchActorProduction(user);
        }
    }
    private Actor searchActor(){

        System.out.println("Type the name: ");
        String name = scanner.nextLine();
        Actor actor = findActorByName(name);
        if (actor != null) {
            actor.displayInfo();
            return actor;
        }
        else {
            System.out.println("Couldn't find " + name + ". Try again");
            searchActor();
        }
        return null;
    }
    private Production searchProduction(){

        System.out.println("Type the name: ");
        String name = scanner.nextLine();
        Production production = findProductionByName(name);
        if (production != null)
            production.displayInfo();
        else {
            System.out.println("Couldn't find " + name + ". Try again");
            searchProduction();
        }
        return production;
    }
    public Production findProductionByName(String name) {
        for (Production production : productions) {
            if (production.getTitle().equalsIgnoreCase(name)) {
                return production;
            }
        }
        return null;
    }
    public Actor findActorByName(String nameToFind) {
        for (Actor actor : actors) {
            if (actor.getName().equalsIgnoreCase(nameToFind)) {
                return actor;
            }
        }
        return null;
    }
    public User findUserByUsername(String nameToFind) {
        for (User user : users) {
            if (user.getUsername().equals(nameToFind)) {
                return user;
            }
        }
        return null;
    }
    private void printActorChoices(User user) {
        boolean validChoice = false;
        String choice = "";

        while (!validChoice) {
            System.out.println("Do you want the actors to be sorted by name?(y/n): ");
            choice = scanner.next().toLowerCase();

            if (choice.equals("y") || choice.equals("n")) {
                validChoice = true;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'. Try again.");
            }
        }

        if (choice.equals("y"))
            printActorsByName();
        else
            printActors();

        System.out.println("Press any key and enter to go back to Menu");
        scanner.next();
        showOptions(user);
        chooseOption(user);
    }

    private void printProductionChoices(User user) {
    try {
        System.out.println("How do you want the productions to be shown?");
        System.out.println("1) Movies/Series with a certain genre");
        System.out.println("2) In descending order by number of ratings");

        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            try {
                choice = scanner.nextInt();
                if (choice != 1 && choice != 2) {
                    throw new IllegalArgumentException();
                }
                validChoice = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter 1 or 2. Try again.");
                scanner.nextLine();
            }
        }

        System.out.println("Do you want to see details about:");
        System.out.println("1) Movies");
        System.out.println("2) Series");

        int choice2 = 0;
        boolean validChoice2 = false;
        while (!validChoice2) {
            try {
                choice2 = scanner.nextInt();
                if (choice2 != 1 && choice2 != 2) {
                    throw new IllegalArgumentException();
                }
                validChoice2 = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter 1 or 2. Try again.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();

        if (choice == 1) {
            Genre selectedGenre = null;
            while (selectedGenre == null) {
                System.out.println("Enter the genre:");
                String genreInput = scanner.nextLine();

                try {
                    selectedGenre = Genre.valueOf(genreInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Genre not found. Please enter a different genre. Try again.");
                }
            }
            if (choice2 == 1)
                printProductionsByGenre(selectedGenre, "Movie");
            else
                printProductionsByGenre(selectedGenre, "Series");
        } else if (choice == 2) {
            if (choice2 == 1)
                printProductionsByRatings("Movie");
            else
                printProductionsByRatings("Series");
        }

        System.out.println("Press any key and enter to go back to Menu");
        scanner.next();
        showOptions(user);
        chooseOption(user);

    } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number. Try again.");
        scanner.nextLine();
        printProductionChoices(user);
    }
}

    private User optionsAfterLogout(User user){
        System.out.println("1) Go back to login");
        System.out.println("2) Exit");
        int choice2 = scanner.nextInt();
        if(choice2 == 2){
           return null;
        }
        return login();
    }
    public void loadActorsFromJson(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object jsonElement : jsonArray) {
                JSONObject actorJson = (JSONObject) jsonElement;
                Actor actor = createActorFromJson(actorJson);
                actors.add(actor);
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            System.err.println("Error loading actors from JSON file: " + e.getMessage());
        }
    }
    private Actor createActorFromJson(JSONObject actorJson) {
        String name = (String) actorJson.get("name");
        String biography = (String) actorJson.get("biography");

        JSONArray performancesArray = (JSONArray) actorJson.get("performances");
        List<Map.Entry<String, String>> performances = createPerformancesList(performancesArray);

        return new Actor(name, performances, biography);
    }

    private List<Map.Entry<String, String>> createPerformancesList(JSONArray performancesArray) {
        List<Map.Entry<String, String>> performances = new ArrayList<>();

        if (performancesArray != null) {
            for (Object performanceElement : performancesArray) {
                JSONObject performanceJson = (JSONObject) performanceElement;
                String title = (String) performanceJson.get("title");
                String type = (String) performanceJson.get("type");
                performances.add(new AbstractMap.SimpleEntry<>(title, type));
            }
        }

        return performances;
    }

    public void printActors() {
        if (actors.isEmpty()) {
            System.out.println("No actors loaded.");
        } else {
            System.out.println("Actors:");
            for (Actor actor : actors) {
                actor.displayInfo();
            }
        }
    }
    public void addRatingsToUser() {
        for (Production production : productions) {
            for (Rating rating : production.getUserRatings()) {
                User user = findUserByUsername(rating.getUsername());
                if( user instanceof Regular){
                   Regular regular = (Regular) user;
                if (regular != null) {
                    regular.addRating(rating);
                } else {
                    System.out.println("User not found: " + rating.getUsername());
                }
                }

            }
        }
    }
    public void loadProductionsFromJson(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object jsonElement : jsonArray) {
                JSONObject productionJson = (JSONObject) jsonElement;
                Production production = createProductionFromJson(productionJson);
                productions.add(production);
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            System.err.println("Error loading productions from JSON file: " + e.getMessage());
        }
    }
    private List<Genre> createGenresList(JSONArray genresArray) {
        List<Genre> genres = new ArrayList<>();

        if (genresArray != null) {
            for (Object genreElement : genresArray) {
                String genre = (String) genreElement;
                genres.add(Genre.valueOf(genre));
            }
        }

        return genres;
    }

    private List<Rating> createRatingsList(JSONArray ratingsArray) {
        List<Rating> ratings = new ArrayList<>();

        if (ratingsArray != null) {
            for (Object ratingElement : ratingsArray) {
                JSONObject ratingJson = (JSONObject) ratingElement;
                String username = (String) ratingJson.get("username");
                int score = ((Long) ratingJson.get("rating")).intValue();
                String comments = (String) ratingJson.get("comment");
                ratings.add(new Rating(username, score, comments));
            }
        }

        return ratings;
    }
    private Map<String, List<Episode>> createSeasonsMap(JSONObject seasonsJson) {
        Map<String, List<Episode>> seasonsMap = new TreeMap<>();

        if (seasonsJson != null) {
            for (Object seasonKey : seasonsJson.keySet()) {
                String seasonName = (String) seasonKey;
                JSONArray episodesArray = (JSONArray) seasonsJson.get(seasonName);
                List<Episode> episodes = createEpisodesList(episodesArray);
                seasonsMap.put(seasonName, episodes);
            }
        }

        return seasonsMap;
    }
    private List<Episode> createEpisodesList(JSONArray episodesArray) {
        List<Episode> episodes = new ArrayList<>();

        if (episodesArray != null) {
            for (Object episodeElement : episodesArray) {
                JSONObject episodeJson = (JSONObject) episodeElement;
                String episodeName = (String) episodeJson.get("episodeName");
                String episodeDuration = (String) episodeJson.get("duration");
                episodes.add(new Episode(episodeName, episodeDuration));
            }
        }

        return episodes;
    }
    private Production createProductionFromJson(JSONObject productionJson) {
        String title = (String) productionJson.get("title");
        String type = (String) productionJson.get("type");
        List<String> directors = (List<String>) productionJson.get("directors");
        List<String> actors = (List<String>) productionJson.get("actors");
        List<Genre> genres = createGenresList((JSONArray) productionJson.get("genres"));
        List<Rating> ratings = createRatingsList((JSONArray) productionJson.get("ratings"));
        String description = (String) productionJson.get("plot");

        if ("Movie".equals(type)) {
            String duration = (String) productionJson.get("duration");
            int releaseYear = (productionJson.get("releaseYear") != null) ? Integer.parseInt(productionJson.get("releaseYear").toString()) : 0;
            return new Movie(title,type, directors, actors, genres, ratings, description, duration, releaseYear);
        } else if ("Series".equals(type)) {
            int releaseYear = (productionJson.get("releaseYear") != null) ? Integer.parseInt(productionJson.get("releaseYear").toString()) : 0;
            int numberOfSeasons = ((Long) productionJson.get("numSeasons")).intValue();
            Map<String, List<Episode>> seasons = createSeasonsMap((JSONObject) productionJson.get("seasons"));

            return new Series(title, type, directors, actors, genres, ratings, description, releaseYear, numberOfSeasons, seasons);
        } else {
            throw new IllegalArgumentException("Invalid production type");
        }
    }
    public void addActorsFromProductions(List<Production> productions, List<Actor> actors) {
        Set<Actor> existingActors = new HashSet<>(actors);

        for (Production production : productions) {
            if (production.getActors() != null) {
                for (String actorName : production.getActors()) {
                    Map.Entry<String, String> productionInfo = Map.entry("Title", production.getTitle());
                    Map.Entry<String, String> productionType = Map.entry("Type", production.getType());
                    Actor actor = new Actor(actorName, List.of(productionInfo,productionType), null);

                    if (!existingActors.contains(actor)) {
                        actors.add(actor);
                        existingActors.add(actor);
                    }
                }
            }
        }
    }

    public void printProductions() {
        if (productions.isEmpty()) {
            System.out.println("No productions loaded.");
        } else {
            System.out.println("Productions:");
            for (Production production : productions) {
                production.displayInfo();
                System.out.println();
            }
        }
    }
    public void printProductionsByGenre(Genre targetGenre, String type){
        System.out.println("Productions with Genre '" + targetGenre + "':");
        for (Production production : productions) {
            if (production.getGenres().contains(targetGenre) && Objects.equals(production.type, type)) {
                production.displayInfo();
                System.out.println("--------------");
            }
        }
    }


    public void printProductionsByRatings(String type){
        System.out.println("Productions sorted by descending ratings:");

        productions.sort(Comparator.comparingInt(Production::getNumberOfRatings).reversed());

        for (Production production : productions) {
            if(Objects.equals(production.type, type)){
                System.out.println("Number of ratings: " + production.getNumberOfRatings());
                production.displayInfo();
                System.out.println("--------------");
            }
        }
    }
    public void printActorsByName(){
        System.out.println("Actors sorted alphabetically: ");
        actors.sort(Actor::compareTo);
        if (actors.isEmpty()) {
            System.out.println("No actors loaded.");
        } else {
            System.out.println("Actors:");
            for (Actor actor : actors) {
                actor.displayInfo();
            }

        }
    }
    private void loadRequestsJson(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object jsonElement : jsonArray) {
                JSONObject requestJson = (JSONObject) jsonElement;
                Request request = createRequestFromJson(requestJson);
                requests.add(request);
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            System.err.println("Error loading requests from JSON file: " + e.getMessage());
        }
    }

    private Request createRequestFromJson(JSONObject requestJson) {
        RequestTypes requestType = RequestTypes.valueOf((String) requestJson.get("type"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime creationDate = LocalDateTime.parse((String) requestJson.get("createdDate"), formatter);
        String title = null;
        if ("MOVIE_ISSUE".equals(requestType.toString())) {
            title = (String) requestJson.get("movieTitle");
        } else if ("ACTOR_ISSUE".equals(requestType.toString())) {
            title = (String) requestJson.get("actorName");
        }
        
        String description = (String) requestJson.get("description");
        String requesterUsername = (String) requestJson.get("username");
        String resolverUsername = (String) requestJson.get("to");

        Request r = new Request(requestType,creationDate, title, description, requesterUsername, resolverUsername);
        return r;
    }
    public void addRequests(){
        for(Request request : requests){
            User user = findUserByUsername(request.getRequesterUsername());
            user.addRequest(request);
        }
    }
    public void processRequests(List<Request> requests) {
        for (Request request : requests) {
            String resolverUsername = request.getResolverUsername();


            Staff staff = (Staff) findUserByUsername(resolverUsername);

                if ("ADMIN".equals(resolverUsername)) {
                    RequestsHolder.addTeamRequest(request);
                }
                if (staff != null) {
                    staff.addRequest(request);
                }
        }
    }

    public void displayRequests() {
        if (requests.isEmpty()) {
            System.out.println("No requests loaded.");
        } else {
            System.out.println("Testing displayInfo() for Requests:");
            System.out.println("-----------------------------------");
            for (Request request : requests) {
                request.displayInfo();
                System.out.println("-----------------------------------");
            }
        }
    }
    public void loadUsersFromJson(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONArray jsonArray = (JSONArray) obj;

            for (Object jsonElement : jsonArray) {
                JSONObject userJson = (JSONObject) jsonElement;
                User user = createUserFromJson(userJson, productions, actors);
                users.add(user);
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            System.err.println("Error loading users from JSON file: " + e.getMessage());
        }
    }
    private User createUserFromJson(JSONObject userJson, List<Production> allProductions, List<Actor> allActors) {
        String username = (String) userJson.get("username");
        String userType = (String) userJson.get("userType");
        int experience = 0;
        if (userJson.get("experience") != null) {
            experience = Integer.parseInt(userJson.get("experience").toString());
        }
        List<String> notifications = null;
        if(userJson.get("notifications")!= null)
            notifications = (List<String>) userJson.get("notifications");
        JSONObject informationJson = (JSONObject) userJson.get("information");
        JSONObject credentialsJson = (JSONObject) informationJson.get("credentials");

        String email = (String) credentialsJson.get("email");
        String password = (String) credentialsJson.get("password");

        String name = (String) informationJson.get("name");
        String country = (String) informationJson.get("country");
        int age = Integer.parseInt(informationJson.get("age").toString());
        String gender = (String) informationJson.get("gender");
        String birthDate = (String) informationJson.get("birthDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDate.parse(birthDate, formatter).atStartOfDay();


        User.Information.InformationBuilder informationBuilder = new User.Information.InformationBuilder(new Credentials(email, password))
                .name(name)
                .country(country)
                .age(age)
                .gender(gender)
                .birthDate(localDateTime);

        User.Information information = informationBuilder.build();
        switch (userType) {
            case "Contributor":

                SortedSet<String> contributions1 = createContribution(userJson);
                SortedSet<String> favorites = createFavorites(userJson);
                return userFactory.createContributor(information, username, experience, contributions1,notifications,favorites);
            case "Regular":
                SortedSet<String> favoritesRegular = createFavorites(userJson);
                return userFactory.createRegular(information, username, experience, notifications, favoritesRegular);
            case "Admin":
                SortedSet<String> favoritesAdmin = createFavorites(userJson);
                SortedSet<String> contributions = createContribution(userJson);
                return userFactory.createAdmin(information, username, experience,contributions,notifications, favoritesAdmin);
            default:
                throw new IllegalArgumentException("Invalid user type");
        }
    }
    private SortedSet<String> createContribution(JSONObject userJson) {
        JSONArray productionsContribution = (JSONArray) userJson.get("productionsContribution");
        JSONArray actorsContribution = (JSONArray) userJson.get("actorsContribution");

        SortedSet<String> contribution = new TreeSet<>();

        if (productionsContribution != null) {
            for (Object production : productionsContribution) {
                contribution.add((String) production);
            }
        }

        if (actorsContribution != null) {
            for (Object actor : actorsContribution) {
                contribution.add((String) actor);
            }
        }
        return contribution;
    }
    private SortedSet<String> createFavorites(JSONObject userJson) {
        JSONArray favoriteProductions = (JSONArray) userJson.get("favoriteProductions");
        JSONArray favoriteActors = (JSONArray) userJson.get("favoriteActors");

        SortedSet<String> favorites = new TreeSet<>();

        if (favoriteProductions != null) {
            for (Object production : favoriteProductions) {
                favorites.add((String) production);
            }
        }
        if (favoriteActors != null) {
            for (Object actor : favoriteActors) {
                favorites.add((String) actor);
            }
        }
        return favorites;
    }


    public void displayUsers() {
        System.out.println("List of Users:");

            for (User user : users) {
                if(user != null) {
                user.displayInfo();
                    if(user instanceof Regular)
                        ((Regular) user).displayRatings();
                System.out.println("---------------");
                }
            }

    }


    private static final String ACTORS_JSON_PATH = "C:\\Users\\Izabela\\OneDrive - Universitatea Politehnica Bucuresti\\Desktop\\PROIECT\\src\\main\\resources\\input\\actors.json";
    private static final String PRODUCTIONS_JSON_PATH = "C:\\Users\\Izabela\\OneDrive - Universitatea Politehnica Bucuresti\\Desktop\\PROIECT\\src\\main\\resources\\input\\production.json";
    private static final String REQUESTS_JSON_PATH = "C:\\Users\\Izabela\\OneDrive - Universitatea Politehnica Bucuresti\\Desktop\\PROIECT\\src\\main\\resources\\input\\requests.json";
    private static final String ACCOUNTS_JSON_PATH = "C:\\Users\\Izabela\\OneDrive - Universitatea Politehnica Bucuresti\\Desktop\\PROIECT\\src\\main\\resources\\input\\accounts.json";

    public static void main(String[] args) {

        IMDB imdb = IMDB.getInstance();
        imdb.run();

    }
}