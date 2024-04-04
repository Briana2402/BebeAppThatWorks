package com.example.bebeappthatworks;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.ui.eventCreation.Event;

import java.util.List;


// Adapter class for RecyclerView to display events.
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

    // Method to bind data to ViewHolder
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

    // Method to load image using Glide library
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

    // Method to get total number of items in RecyclerView
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // ViewHolder class to hold views of individual event item
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mName;
        public final TextView mLocation;
        public final TextView mTime;
        public final TextView mDescription;
        public final TextView mCapacity;
        public final TextView mDate;

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