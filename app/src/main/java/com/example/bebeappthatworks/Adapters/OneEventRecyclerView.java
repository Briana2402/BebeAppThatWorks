package com.example.bebeappthatworks.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.databinding.LayoutOneEventBinding;
//import com.example.bebeappthatworks.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.bebeappthatworks.models.Event;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event}.
 * Class used to showcase one event.
 */
public class OneEventRecyclerView extends RecyclerView.Adapter<OneEventRecyclerView.ViewHolder> {

    // Declaring variables
    private final List<Event> mValues;
    private Event event;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OneEventRecyclerView(List<Event> items) {
        this.mValues = items;
    }


    /**
     * Method used to initialize the showcase of the event.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return the event as viewTyoe
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutOneEventBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    /**
     *  Giving values from the firebase to all of our variables.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //mValues.addAll(AttendeeActivity.allEvents);


        Event event = mValues.get(position);
        holder.mImageView.setImageResource(R.mipmap.ic_launcher_foreground);
        holder.mName.setText(event.getEventName());
        holder.mLocation.setText(event.getEventLocation());
        holder.mDate.setText(event.getEventDate());
        holder.mTime.setText(event.getEventTime());
        holder.mDescription.setText(event.getEventDescription());
        holder.mCapacity.setText(event.getEventMaxCapacity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(event);
                }
            }
        });

    }

    /**
     * Method used to count the events from an arrayList.
     * @return size of the array of events
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Method used in order to make the user to be able to click
     * anywhere on the item an open a new fragment.
     */
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    /**
     * Class used to showcase the event with all its variables
     * filled in with the correct information.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public final TextView mIdView;
        public final ImageView mImageView;

        public final TextView mName;
        public final TextView mLocation;
        public final TextView mTime;
        public final TextView mDescription;
        public final TextView mCapacity;
        public final TextView mDate;

        //public PlaceholderItem mItem;


        /**
         * Method used to get and remember the details of an event.
         * @param binding
         */
        public ViewHolder(@NonNull LayoutOneEventBinding binding) {
            super(binding.getRoot());
            //mIdView = binding.itemNumber;
            mImageView = binding.content;
            mLocation = binding.location;
            mTime = binding.time;
            mDescription = binding.description;
            mCapacity = binding.capacity;
            mDate = binding.date;
            mName = binding.name;

        }
    }
}