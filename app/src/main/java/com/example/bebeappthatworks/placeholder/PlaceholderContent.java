package com.example.bebeappthatworks.placeholder;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.bebeappthatworks.ui.eventCreation.Event;

import com.example.bebeappthatworks.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    static String id;
    static ImageView content;
    static String name;
    static String location;
    static String time;
    static String description;
    static String capacity;
    static String date;

    //private String[] items = {name, location, description, time, capacity, date};
    //public List<String[]> allEvents = new ArrayList<String[]>();

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Event> ITEMS = new ArrayList<Event>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, Event> ITEM_MAP = new HashMap<String, Event>();

    private static final int COUNT = 25; //TODO change cout to database count

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Event item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Event createPlaceholderItem(int position) {
        return new Event(id, location, time, name, date, capacity, description);
    }
    /*
    private static String getContent(int position, String item) {
        ~~ FETCH FUNCTION BASED ON ITEMS ~~
    }
    */
    /**
     * A placeholder item representing a piece of content.
     */

}