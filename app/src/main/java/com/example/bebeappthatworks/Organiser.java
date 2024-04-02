package com.example.bebeappthatworks;

import com.example.bebeappthatworks.ui.eventCreation.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Organiser extends User{
    private String email, password, name, profileUrl;
    private ArrayList<Event> MyEvents  = new ArrayList<Event>();
    public Organiser(){
    }

    public Organiser(String email, String name, String password, String profileUrl){
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
