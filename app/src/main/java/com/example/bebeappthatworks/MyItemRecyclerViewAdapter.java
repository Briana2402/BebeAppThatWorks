package com.example.bebeappthatworks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bebeappthatworks.databinding.FragmentEventsBinding;
//import com.example.bebeappthatworks.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private Event event;

    public MyItemRecyclerViewAdapter(List<Event> items) {
        mValues = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //mValues.addAll(AttendeeActivity.allEvents);


        Event event = mValues.get(position);
        holder.mImageView.setImageResource(R.mipmap.ic_banner_foreground);
        holder.mName.setText(R.string.mName);
        holder.mLocation.setText(event.getEventLocation());
        holder.mDate.setText(event.getEventDate());
        holder.mTime.setText(event.getEventTime());
        holder.mDescription.setText(event.getEventDescription());
        holder.mCapacity.setText(event.getEventMaxCapacity());

        /*
        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);


         */
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        //public final TextView mIdView;
        public final ImageView mImageView;

        public final TextView mName;
        public final TextView mLocation;
        public final TextView mTime;
        public final TextView mDescription;
        public final TextView mCapacity;
        public final TextView mDate;

        //public PlaceholderItem mItem;


        public ViewHolder(FragmentEventsBinding binding) {
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
        /*
        @Override
        public String toString() {
            return super.toString() + " '" + mImageView.getImageAlpha() + "'";
        }
         */
    }
}