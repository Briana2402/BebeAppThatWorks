public class Event {

    // variables for storing our data.
    private String eventLocation, eventTime, eventDate, eventName;
    private int maxCapacity = 0 ;

    public Event() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Event(String eventLocation, String eventTime, String eventName, String eventDate, int maxCapacity) {
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.maxCapacity = maxCapacity;
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


    public int getEventmaxCapacity() {
        return maxCapacity;
    }

    public void setEventMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
