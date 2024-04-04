package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyEventsAttendee extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> myEvents = new ArrayList<>();

    public List<String> events_id = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public String event_id = new String();

    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsAttendee() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_events_attendee, container, false);
        //gets here
        //Log.i("miauuu", mAuth.getCurrentUser().getUid().toString());


        Log.i("miauuu", "approaces for loop");

        //doesnt get into this for some unknown reason( i think that it takes too long for the first for loop to
        //get executed so when it gets to this loop, the array is still empty so somehow this for has to wait for
        //all the ids to be fetched.
        //checks all the ids of the vents a user is registered for and fetches the events based on the ids
//        for (String eventId : events_id) {
//            Log.i("miauuu", eventId);
//            DocumentReference docRef = db.collection("Events").document(eventId);
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(Task<DocumentSnapshot> task) {
//                    Log.i("miauuu", "starts fetching again");
//                    if (task.isSuccessful()) {
//                        //gets here
//                        Log.i("miauuu", "document.getId().toString()");
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            // Map Firestore document to Event object
//                            myEvents.add(document.toObject(Event.class));
//                            Log.i("miauuu", document.getId().toString());
//                            //adapter.notifyDataSetChanged();
//                        }
//
//                    }
//                }
//            });

        CollectionReference eventsRef = db.collection("Attendees").document(mAuth.getCurrentUser().getUid().toString()).collection("my events");


        //Log.i("miauuu", "gets after collection");
        //Query query = eventsRef.whereEqualTo("eventCreator", mAuth.getCurrentUser().getUid());

        eventsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.i("miauuu", "goes into first fetch");
                if (task.isSuccessful()) {
                    //creates an  array with all the ids of the events that the logged in user is registered for
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        events_id.add(document.getId().toString());
                        //Log.i("miauuu", document.getId().toString());
                        event_id = document.getId().toString();
                        DocumentReference docRef = db.collection("Events").document(event_id);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                                Log.i("miauuu", "starts fetching again for events");
                                if (task.isSuccessful()) {
                                    //gets here
                                    Log.i("miauuu", "document.getId().toString()");
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Map Firestore document to Event object
                                        myEvents.add(document.toObject(Event.class));
                                        Log.i("miauuu", document.getId().toString());
                                        EventAdapter adapter = new EventAdapter(myEvents);
                                        RecyclerView recyclerView = (RecyclerView) view;
                                        Context context = view.getContext();
                                        if (mColumnCount <= 1) {
                                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        } else {
                                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                        }
                                        recyclerView.setAdapter(adapter);
                                        Log.i("miauuu", "miau miau");
                                        //adapter.notifyDataSetChanged();
                                    }

                                }
                            }
                        });
                    }
                } else {
                    Log.i("miauuu", "doesnt work :(");
                }

            }
        });




        return view;
    }
}