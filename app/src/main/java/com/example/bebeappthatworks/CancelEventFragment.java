package com.example.bebeappthatworks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleEventFree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelEventFragment extends Fragment {

    //Parameter to get the event id needed through constructor.
    private static String ARGM1 = "param1";

    //Parameter to save the event id needed.
    public String eventID;

    //Parameter to save the event name needed.
    public String eventName;

    //Parameter to save instance of type Event.
    private Event event;

    //Used view.
    View view;

    //Initializing a firebase instance.
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CancelEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 provided event ID parameter.
     * @return A new instance of fragment CancelEvent.
     */
    public CancelEventFragment newInstance(String param1) {
        CancelEventFragment fragment = new CancelEventFragment();
        Bundle args = new Bundle();
        args.putString(ARGM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventID = getArguments().getString(ARGM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cancel_event, container, false);

        // Get the information of the event being cancelled.
        getEvent(eventID);

        //Cancel event.
        Button cancel = view.findViewById(R.id.cancelEvent);
        deleteEvent(cancel);

        return view;
    }

    /**
    *Sends notifications to all attendees register for event when it is cancelled.
    * @pre An event was cancelled by its organiser.
    * @post Notification is sent to all the registered attendees.
    *
     */
    public void sendNotification(){
        //Create notification to be sent and add it to the Notification database.
        Notification newNotication = new Notification(eventName,"This event has been canceled.");
        CollectionReference dbEvents = db.collection("Notification");
        dbEvents.add(newNotication);

        //Search for all attendees registered to event with eventID.
        db.collection("Attendees").get().addOnCompleteListener(task -> {
                    //Go through all
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot attendee : task.getResult()) {

                            // Query the subcollection to check if the document existsing in the subcollection of my events of attendee and check if event being cancelled is there.
                            db.collection("Attendees").document(attendee.getId()).collection("my events")
                                    .document(eventID.toString()).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            // Attendee is registered for event being cancelled so notification is created.
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("eventName", newNotication.getEventName());
                                            data.put("message", newNotication.getMessage());

                                            // Set data into the "my notifications" subcollection of said attendee.
                                            db.collection("Attendees").document(attendee.getId()).collection("my notifications").document().set(data).addOnSuccessListener(aVoid -> {
                                                        Log.d("Notification", "Notification added successfully.");
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("Error", "Error adding notification: ", e);
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Wrong Subcollection", "Error querying subcollection: ", e);
                                    });
                        }
                    } else {
                        Log.e("Error fetching", "Error getting documents: ", task.getException());
                    }
                });
    }


    /**
     * Button functionality that deletes a created event from the database when pressed.
     *
     * @param button Button pressed to delete event.
     * @pre Event was created by logged in account.
     * @post Event is deleted from database and app events pages and notifications are sent to registerd
     * attendees.
     */
    private void deleteEvent(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
                db.collection("Events").document(eventID.toString()).delete();
                MyEventsFragment fragment = new MyEventsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    /**
     * Fetches the document containing the information of the event with document id ID.
     *
     * @param ID ID of document to be fetched.
     * @pre A document in collction "Events" with given ID.
     * @post Document data is saved in an Event object.
     *
     */
    private void getEvent(String ID){
        DocumentReference docRef = db.collection("Events").document(ID.toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    event = document.toObject(Event.class);
                    eventName = event.getEventName();
                }
            }
        });
    }
}



