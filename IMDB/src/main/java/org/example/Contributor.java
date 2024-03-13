package org.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;

public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager {

    public Contributor(Information information, String username, int experience,
                       SortedSet<String> addedProductionsActors,
                       List<String> notifications, SortedSet<String> favorites) {
        super(information, AccountType.Contributor, username, experience, addedProductionsActors, notifications, favorites);
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
