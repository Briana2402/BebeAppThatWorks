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

    static ImageView content;
    static String name;
    static String location;
    static String time;
    static String description;
    static String capacity;
    static String date;

    private String[] items = {name, location, description, time, capacity, date};

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 25; //TODO change cout to database count

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {
        return new PlaceholderItem(String.valueOf(position), content, name, location, time,
                description, capacity, date);
    }
    /*
    private static String getContent(int position, String item) {
        ~~ FETCH FUNCTION BASED ON ITEMS ~~s
    }
    */
    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String id;
        public final ImageView content;
        public final String name;
        public final String location;
        public final String time;
        public final String description;
        public final String capacity;
        public final String date;
        public PlaceholderItem(String id, ImageView content, String name, String location,
                               String time, String description, String capacity, String date) {
            this.id = id;
            this.content = content;
            this.date = date;
            this.location = location;
            this.time = time;
            this.description = description;
            this.name = name;
            this.capacity = capacity;
        }
        /*
        @Override
        public String toString() {
            return attributeSet;
        }
        */
    }
}