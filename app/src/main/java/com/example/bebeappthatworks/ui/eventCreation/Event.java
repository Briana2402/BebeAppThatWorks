package com.example.bebeappthatworks.ui.eventCreation;

import com.example.bebeappthatworks.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class Event {

    // variables for storing our data.
    private String eventLocation, eventTime, eventDate, eventName,eventDescription, maxCapacity, imageUrl, eventType, eventLink;

    public Event() {
        // empty constructor
        // required for Firebase.
    }

    public static final List<Event> ITEMS = new ArrayList<Event>();

    // Constructor for all variables.
    public Event(String eventLocation, String eventTime, String eventName, String eventDate, String maxCapacity,String eventDescription, String imageUrl, String eventType, String eventLink) {
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.maxCapacity = maxCapacity;
        this.eventDescription = eventDescription;
        this.imageUrl = imageUrl;
        this.eventType = eventType;
        this.eventLink = eventLink;
    }

    // getter methods for all variables.
    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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
        this.imageUrl = imageUrl;
    }

    public String getEventType() {
        return eventType;
    }


    public String getEventMaxCapacity() {
        return maxCapacity;
    }

    public void setEventMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
