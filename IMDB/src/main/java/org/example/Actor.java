package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Actor implements Comparable<Object>{

    private String name;
    private List<Map.Entry<String, String>> roles;
    private String biography;

    public Actor(String name, List<Map.Entry<String, String>> roles, String biography) {
        this.name = name;
        this.roles = roles;
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public List<Map.Entry<String, String>> getRoles() {
        return roles;
    }

    public String getBiography() {
        return biography;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", roles=" + roles +
                ", biography='" + biography + '\'' +
                '}';
    }
    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Performances: ");

        for (Map.Entry<String, String> role : roles) {
            System.out.println("  Title: " + role.getKey());
            System.out.println("  Type: " + role.getValue());
            System.out.println("--------------");
        }

        System.out.println("Biography: " + biography);
        System.out.println("---------------");
        System.out.println("---------------");
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Actor otherActor) {
            return this.name.compareTo(otherActor.name);
        }
        return 0;
    }
}
