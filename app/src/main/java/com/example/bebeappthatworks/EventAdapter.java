package com.example.bebeappthatworks;

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
import com.example.bebeappthatworks.ui.eventCreation.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private final List<Event> eventList;
    private OnItemClickListener onItemClickListener;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

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
        Event event = eventList.get(position);
        //holder.mImageView.setImageResource(R.mipmap.ic_launcher_foreground);
        setImage(event.getImageUrl(), holder.mImageView, holder.itemView.getContext());
        holder.mName.setText(event.getEventName());
        holder.mLocation.setText(event.getEventLocation());
        holder.mTime.setText(event.getEventTime());
        holder.mDescription.setText(event.getEventDescription());
        holder.mCapacity.setText(event.getEventMaxCapacity());
        holder.mDate.setText(event.getEventDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionInArray = holder.getAdapterPosition();
                if (positionInArray != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(positionInArray, event);
                }
            }
        });
    }

    //Pulling the picture from Firebase
    private void setImage(String imageUrl, ImageView imageView, Context context) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


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