package org.example;

import java.util.ArrayList;
import java.util.List;

public class RequestsHolder {
    private static List<Request> adminTeamRequests;

    static {
        adminTeamRequests = new ArrayList<>();
    }
    public static void addTeamRequest(Request request) {
        adminTeamRequests.add(request);
    }
    public static void removeTeamRequest(Request request) {
        adminTeamRequests.remove(request);
    }
    public static void displayAdminTeamRequests() {
        System.out.println("Admin Team Requests:");
        for (Request request : adminTeamRequests) {
            request.displayInfo();
            System.out.println("---------------");
        }
    }

}

