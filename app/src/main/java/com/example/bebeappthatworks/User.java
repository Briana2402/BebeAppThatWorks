package com.example.bebeappthatworks;

public class User {
    public String email, role;

    public User() {
        // empty constructor
        // required for Firebase.
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

}
