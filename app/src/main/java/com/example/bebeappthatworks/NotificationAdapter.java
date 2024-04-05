package com.example.bebeappthatworks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for RecyclerView to display notifications.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    //Notifications to be displayed.
    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    /**
     * OnCreateViewHolder method.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    /**
     * OnBindViewHolder method.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.textViewName.setText(notification.getEventName());
        holder.textViewMessage.setText(notification.getMessage());
    }

    /**
     * Gets the number of notifications to be displayed in the recyclerView.
     */
    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    /**
     * ViewHolder class for managing notifications in a RecyclerView.
     * This class is responsible for holding references to the UI elements
     * of each notification item in the RecyclerView.
     */
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        // TextView to display the name of the notification.
        TextView textViewName;

        // TextView to display the message content of the notification.
        TextView textViewMessage;

        /**
         * Constructs a new NotificationViewHolder.
         *
         * @param itemView The View corresponding to the notification item layout.
         */
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the TextViews by finding them in the provided itemView.
            textViewName = itemView.findViewById(R.id.name);
            textViewMessage = itemView.findViewById(R.id.message);
        }
    }
}