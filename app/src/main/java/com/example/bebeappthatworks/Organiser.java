package com.example.bebeappthatworks;

import com.example.bebeappthatworks.ui.eventCreation.Event;

import java.util.ArrayList;

public class Organiser extends User{

    private String email, password;
    private ArrayList<Event> MyEvents  = new ArrayList<Event>();
    public Organiser(){

    }

    public Organiser(String email, String password){
        this.email = email;
        this.password = password;
    }
}
