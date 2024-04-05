package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyEventsAttendee#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Fragment to display the events an attendee has registered for on their event page.
 */
public class MyEventsAttendee extends Fragment {

    //Parameter send to class through constructor.
    private static final String ARGM1 = "param1";

    //Initialising Firebase database instance.
    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Array containing all displayed events.
    public List<Event> myEvents = new ArrayList<>();

    //Event ID inputted through the constructor.
    public String eventID;

    //Instance of Firebase Authentificator.
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //View created.
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsAttendee() {
    }


    public MyEventsAttendee newInstance(String param1) {
        MyEventsAttendee fragment = new MyEventsAttendee();
        Bundle args = new Bundle();
        args.putString(ARGM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * OnCreate method.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventID = getArguments().getString(ARGM1);
        }

    }

    /**
     * OnCreateView method.
     * Displays events of attende in MyEvents page.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_events_attendee, container, false);

        //Checks the document from Firebase of currently logged in user.
        CollectionReference eventsRef = db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events");

        eventsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //creates an  array with all the ids of the events that the logged in user is registered for
                        DocumentReference docRef = db.collection("Events").document(eventID);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Map Firestore document to Event object
                                        myEvents.add(document.toObject(Event.class));
                                        EventAdapter adapter = new EventAdapter(myEvents);
                                        RecyclerView recyclerView = (RecyclerView) view;
                                        Context context = view.getContext();
                                        recyclerView.setAdapter(adapter);
                                    }

                                }
                            }
                        });
                    }
                }

        });


        return view;
    }
}