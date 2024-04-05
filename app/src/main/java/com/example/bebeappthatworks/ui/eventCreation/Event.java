package com.example.bebeappthatworks.ui.eventCreation;

import com.example.bebeappthatworks.Attendee;
import com.example.bebeappthatworks.Organiser;

import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.widget.ImageView;


public class Event {

    // variables for storing our data.
    //public String id;
    private String eventLocation;
    private String eventTime;
    private String eventDate;
    private String eventName;
    private String eventDescription;
    private String maxCapacity;
    private String imageUrl;
    private String eventType;
    private String eventLink;

    private String creator_id;

    public Event() {
        // empty constructor
        // required for Firebase.
    }

    public static final List<Event> ITEMS = new ArrayList<Event>();
    private final ArrayList<Attendee> eventAttendees = new ArrayList<Attendee>();

    // Constructor for all variables.
    public Event(String eventLocation, String eventTime, String eventName, String eventDate, String maxCapacity,String eventDescription, String imageUrl, String eventType, String eventLink, String creator) {
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.maxCapacity = maxCapacity;
        this.eventDescription = eventDescription;
        this.imageUrl = imageUrl;
        this.eventType = eventType;
        this.eventLink = eventLink;
        this.creator_id = creator;
    }


    /*
    public Event(String eventLocation,String eventTime, String eventName, String eventDate, String eventCapacity, String eventDescription) {
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.maxCapacity = eventCapacity;
        this.eventDescription = eventDescription;
    }*/


    // getter methods for all variables.
    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink (String eventLink  ) {
        this.eventLink = eventLink;
    }

    // getter methods for all variables.
    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventCreator(String creator) {
        this.creator_id = creator;
    }

    public String getEventCreator() {
        return creator_id;
    }

    public String getEventMaxCapacity() {
        return maxCapacity;
    }

    public void setEventMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
