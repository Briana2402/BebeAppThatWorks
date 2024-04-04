package com.example.bebeappthatworks;

public class Notification {
    public String eventName, message;

    public Notification() {
        // empty constructor
        // required for Firebase.
    }

    public Notification(String eventName, String message) {
        this.eventName = eventName;
        this.message = message;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMessage() {
        return message;
    }

}
