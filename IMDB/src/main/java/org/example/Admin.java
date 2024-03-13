package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Admin<T extends Comparable<T>> extends Staff<T> {

    private static List<Admin> teamAdmins; // Lista cu toți adminii din echipă

    static {
        teamAdmins = new ArrayList<>();
    }
    public Admin(Information information, String username, int experience,
                 SortedSet<String> addedProductionsActors,
                 List<String> notifications, SortedSet<String> favorites) {
        super(information, AccountType.Admin, username, experience, addedProductionsActors, notifications, favorites);
    }
    public void addTeamAdmins(Admin admin) {
        teamAdmins.add(admin);
    }
    public void addUser(Admin admin) {
            teamAdmins.add(admin);
    }

    public void removeUser(User user) {
            teamAdmins.remove((Admin) user);
    }

    @Override
    public void addProductionSystem(Production production) {

    }

    @Override
    public void addActorSystem(Actor actor) {

    }

    @Override
    public void removeProductionSystem(String name) {

    }

    @Override
    public void removeActorSystem(String name) {

    }

    @Override
    public void updateProduction(Production production) {

    }

    @Override
    public void updateActor(Actor actor) {

    }

}
