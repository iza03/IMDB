package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {

    private List<Request> assignedRequests;
    private SortedSet<String> addedProductionsActors;

    public Staff(Information information, AccountType accountType, String username, int experience,
                SortedSet<String> addedProductionsActors,
                 List<String> notifications, SortedSet<String> favorites) {
        super(information, accountType, username, experience, notifications, favorites);
        this.assignedRequests = new ArrayList<>();
        this.addedProductionsActors = addedProductionsActors;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Contributions: ");
        if (addedProductionsActors != null) {
            for (String favorite : addedProductionsActors) {
                System.out.println(favorite.toString());
            }
        }
    }
    public void displayAddedProductionsActors() {
        System.out.println("Added Productions and Actors:");
        Iterator<String> iterator = addedProductionsActors.iterator();
        while (iterator.hasNext()) {
            String entry = iterator.next();
            System.out.println(entry);
        }
    }

    public void displayAssignedRequests() {
        System.out.println("Assigned Requests:");
        for (Request request : assignedRequests) {
            request.displayInfo();
            System.out.println("---------------");
        }
    }

    public SortedSet<String> getAddedProductionsActors() {
        return addedProductionsActors;
    }

    public void addRequest(Request request) {
        if (assignedRequests == null) {

            assignedRequests = new ArrayList<>();
        }
        assignedRequests.add(request);
    }
    public void removeRequest(Request request){

        assignedRequests.remove(request);
    }
    public void addProductionsActors(String addition){
        addedProductionsActors.add(addition);
    }

    public void removeProductionsActors(String name) {
        addedProductionsActors.remove(name);
    }

    @Override
    public void resolveRequests(List<Request> requests) {

    }
}

