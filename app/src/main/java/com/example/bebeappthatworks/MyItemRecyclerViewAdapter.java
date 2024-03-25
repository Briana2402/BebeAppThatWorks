package com.example.bebeappthatworks;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bebeappthatworks.databinding.FragmentEventsBinding;
import com.example.bebeappthatworks.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.mImageView.setImageResource(R.mipmap.ic_banner_foreground);
        holder.mName.setText(R.string.mName);
        holder.mLocation.setText(R.string.mLocation);
        holder.mDate.setText(R.string.mDate);
        holder.mTime.setText(R.string.mTime);
        holder.mDescription.setText(R.string.lorem_ipsum);
        holder.mCapacity.setText(R.string.mCapacity);
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

        public PlaceholderItem mItem;


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