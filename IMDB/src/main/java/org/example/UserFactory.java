package org.example;

import java.util.List;
import java.util.SortedSet;

public class UserFactory<T extends Comparable<T>> {
    public Regular createRegular(User.Information information, String username, int experience, List<String> notifications, SortedSet<String> favorites){

        return new Regular(information, username, experience, notifications, favorites);
    }
    public Admin createAdmin(User.Information information, String username, int experience,
                                    SortedSet<String> addedProductionsActors,
                                    List<String> notifications, SortedSet<String> favorites){
        Admin admin = new Admin(information, username, experience,addedProductionsActors,notifications, favorites);
        admin.addTeamAdmins(admin);
        return admin;

    }
    public Contributor createContributor(User.Information information, String username, int experience,
                                                SortedSet<String> addedProductionsActors,
                                                List<String> notifications, SortedSet<String> favorites){
        return new Contributor(information, username, experience,addedProductionsActors,notifications, favorites);

    }
}
