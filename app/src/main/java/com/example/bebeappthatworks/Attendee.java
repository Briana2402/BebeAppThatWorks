package com.example.bebeappthatworks;

import com.example.bebeappthatworks.ui.eventCreation.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Attendee extends User{
    private String email, password;
    private ArrayList<Event> MyEvents  = new ArrayList<Event>();
    public Attendee(){

    }

    public Attendee(String email, String password){
        this.email = email;
        this.password = password;
    }
}
