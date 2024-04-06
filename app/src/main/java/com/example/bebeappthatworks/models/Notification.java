package com.example.bebeappthatworks.models;

/**
 * Notification object class to store data about notifications and send to Firebase.
 */
public class Notification {
    //Variables holding information about each Notificaiton.
    public String eventName, message;

    public Notification() {
        // empty constructor
        // required for Firebase.
    }

    /**
     * Constructor with parameters creating instance of type Notification.
     *
     * @param eventName Name of the event which the notification is about.
     * @param message Message displayed by notification.
     */
    public Notification(String eventName, String message) {
        this.eventName = eventName;
        this.message = message;
    }

    /**
     * Sets the name of the event notification is about.
     *
     * @param eventName The name of the event to be set.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Sets the message associated with the notification.
     *
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the name of the event of the notification.
     *
     * @return The name of the event.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Retrieves the message associated with the notification.
     *
     * @return The message associated with the notification.
     */
    public String getMessage() {
        return message;
    }

}
