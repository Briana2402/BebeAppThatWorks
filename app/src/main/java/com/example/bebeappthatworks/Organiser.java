package com.example.bebeappthatworks;

/**
 * Organiser class represents a user who organises events.
 * It extends the User class and contains methods and properties specific to an organiser.
 */
public class Organiser extends User{

    // Variables needed for the class
    private String name;         // Name of the organiser
    private String profileUrl;   // URL of the organiser's profile picture

    /**
     * Default constructor for Organiser class.
     * This empty constructor is needed for Firebase serialization.
     */
    public Organiser(){
        // empty constructor
    }

    /**
     * Parameterized constructor for Organiser class.
     * Initializes the organiser with the provided details.
     *
     * @param email       Email of the organiser
     * @param name        Name of the organiser
     * @param password    Password of the organiser
     * @param profileUrl  URL of the organiser's profile picture
     */
    public Organiser(String email, String name, String password, String profileUrl){
        this.name = name;
        this.profileUrl = profileUrl;
    }

    /**
     * Getter method to retrieve the name of the organiser.
     *
     * @return String containing the name of the organiser
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to retrieve the URL of the organiser's profile picture.
     *
     * @return String containing the URL of the profile picture
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Setter method to set the URL of the organiser's profile picture.
     *
     * @param profileUrl  String containing the URL to set
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
