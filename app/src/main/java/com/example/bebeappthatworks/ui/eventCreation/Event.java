package com.example.bebeappthatworks.ui.eventCreation;

/**
 * Class to hold information about an event.
 */
public class Event {

    // variables for storing event data.
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

    // Constructor for all variables creating an event.
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
    /**
     * Retrieves the location of the event.
     *
     * @return The location of the event.
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * Sets the location of the event.
     *
     * @param eventLocation The location of the event to be set.
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * Retrieves the link associated with the event.
     *
     * @return The link associated with the event.
     */
    public String getEventLink() {
        return eventLink;
    }

    /**
     * Sets the link associated with the event.
     *
     * @param eventLink The link to be set.
     */
    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    /**
     * Retrieves the time of the event.
     *
     * @return The time of the event.
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * Sets the time of the event.
     *
     * @param eventTime The time of the event to be set.
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Retrieves the date of the event.
     *
     * @return The date of the event.
     */
    public String getEventDate() {
        return eventDate;
    }

    /**
     * Sets the date of the event.
     *
     * @param eventDate The date of the event to be set.
     */
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Retrieves the name of the event.
     *
     * @return The name of the event.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event.
     *
     * @param eventName The name of the event to be set.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Retrieves the description of the event.
     *
     * @return The description of the event.
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * Sets the description of the event.
     *
     * @param eventDescription The description of the event to be set.
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    /**
     * Retrieves the image URL associated with the event.
     *
     * @return The image URL associated with the event.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL associated with the event.
     *
     * @param imageUrl The image URL to be set.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Retrieves the type of the event.
     *
     * @return The type of the event.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the type of the event.
     *
     * @param eventType The type of the event to be set.
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
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
