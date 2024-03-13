package org.example;

public interface Observer {

    void updateRequestNotification(String notification, User user);

    void updateRatingNotification(String notification);
}

