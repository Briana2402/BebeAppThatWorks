package com.example.bebeappthatworks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bebeappthatworks.databinding.RegisterButtonBinding;
//import com.example.bebeappthatworks.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.bebeappthatworks.ui.eventCreation.Event;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OneEventRecyclerView extends RecyclerView.Adapter<OneEventRecyclerView.ViewHolder> {

    private final List<Event> mValues;
    private Event event;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OneEventRecyclerView(List<Event> items) {
        this.mValues = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(RegisterButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

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


        public ViewHolder(@NonNull RegisterButtonBinding binding) {
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