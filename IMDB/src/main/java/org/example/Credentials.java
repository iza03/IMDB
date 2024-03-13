package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
