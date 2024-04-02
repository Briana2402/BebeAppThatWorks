package com.example.bebeappthatworks;

public class User {
    public String email, role, name;

    public User() {
        // empty constructor
        // required for Firebase.
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}
