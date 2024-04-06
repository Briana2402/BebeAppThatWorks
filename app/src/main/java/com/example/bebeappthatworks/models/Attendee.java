package com.example.bebeappthatworks.models;

/**
 *Object class fot attendees.
 */
public class Attendee extends User {

    //fields for the attendee profile
    private String email, password, name, profileUrl;

    //empty constructor needed for Firebase to work
    public Attendee(){
    }

    /**
     * Constructor with parameters for attendee class.
     *
     * @param email inputted email
     * @param name inputted name
     * @param password inputted password
     * @param profileUrl inputted profileURL
     */
    public Attendee(String email, String name, String password, String profileUrl){
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    /**
     * Returns the objects profileUrl.
     *
     * @return attendee.profileUrl
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Sets the objects profileUrl
     *
     * @param profileUrl profileUrl instance to be set.
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
