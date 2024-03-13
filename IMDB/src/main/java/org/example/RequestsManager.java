package org.example;

import java.time.LocalDateTime;

public interface RequestsManager {
    Request createRequest(RequestTypes requestType, LocalDateTime creationDate, String title, String description, String requesterUsername, String resolverUsername);

    void removeRequest(Request r);
}
