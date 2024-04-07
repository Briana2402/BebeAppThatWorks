package com.example.bebeappthatworks.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;

import java.util.List;


// Adapter class for RecyclerView to display events.

/**
 * Adapter class for RecyclerView to display events.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    //List containing events to be shown in recycler view.
    private List<Event> eventList;
    private OnItemClickListener onItemClickListener;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }


    // Interface to handle events on click.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Method to create ViewHolder
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(itemView);
    }


    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        //Set data of the event to TextViews for displaying.
        Event event = eventList.get(position);
        setImage(event.getImageUrl(), holder.mImageView, holder.itemView.getContext());
        holder.mName.setText(event.getEventName());
        holder.mLocation.setText(event.getEventLocation());
        holder.mTime.setText(event.getEventTime());
        holder.mDescription.setText(event.getEventDescription());
        holder.mCapacity.setText(event.getEventMaxCapacity());
        holder.mDate.setText(event.getEventDate());

        // Set click listener for item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get position of clicked item
                int positionInArray = holder.getAdapterPosition();
                // Check if position is valid and listener is set
                if (positionInArray != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(positionInArray, event);
                }
            }
        });
    }

    /**
     * Method to load image using Glide library
     *
     * @param imageUrl ImageUrl to pe sent to library.
     * @param imageView Imageview wher eit cam from.
     * @param context Context.
     */
    private void setImage(String imageUrl, ImageView imageView, Context context) {
        if (imageUrl==null){
            Log.i("null", "Imageurl is null.");
        } else {
            Log.i("imageUrl:", imageUrl);
        }
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }


    /**
     * Method to get total number of items in RecyclerView
     *
     * @pre eventList contains elements and RecyclerView was initialised
     * @return eventList.size()
     */
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**
     * ViewHolder class to hold views of individual event item
     */
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mName;
        public final TextView mLocation;
        public final TextView mTime;
        public final TextView mDescription;
        public final TextView mCapacity;
        public final TextView mDate;

        //Set the view of the event
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.content);
            mName = itemView.findViewById(R.id.name);
            mLocation = itemView.findViewById(R.id.location);
            mTime = itemView.findViewById(R.id.time);
            mDescription = itemView.findViewById(R.id.description);
            mCapacity = itemView.findViewById(R.id.capacity);
            mDate = itemView.findViewById(R.id.date);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Event event);
    }
}