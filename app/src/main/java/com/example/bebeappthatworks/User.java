package com.example.bebeappthatworks;

public class User {
    //fields of the user class
    public String email, role, username, description;

    public User() {
        // empty constructor
        // required for Firebase.
    }

    //constructor for the class
    public User(String email, String role, String username, String description) {
        this.email = email;
        this.role = role;
    }

    /**
     * Method to set the email of the user.
     * @param email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get the email of a variable type user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get the username of the user.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method of getting the description of the variable type user.
     * @return a string containing the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the string of description of a user.
     * @param description a string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to ser the username of a object of type user
     * @param username a string representing the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
