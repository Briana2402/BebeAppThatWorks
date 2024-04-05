package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyEventsFragmentAttendee extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private final int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // array list of fetched events
    public List<Event> myEvents = new ArrayList<>();

    //array list of the id's of the fetched events
    public List<String> events_id = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public String event_id = "";

    View view;

    private EventAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragmentAttendee() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    /**
     * onCreateView method to fetch events the user is registered for/interested in and display them in "my events" page
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_events_attendee, container, false);
        // document reference to "my events" from "Attendees"
        CollectionReference eventsRef = db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events");

        eventsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            // gets the event id of one event and applies the document reference
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //creates an  array with all the ids of the events that the logged in user is registered for
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        event_id = document.getId();

                        // document reference to th event with current fethced id
                        DocumentReference docRef = db.collection("Events").document(event_id);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            // if the current user is registered/interested in the event then the recycler view
                            // displays the event
                            public void onComplete(Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Map Firestore document to Event object
                                        myEvents.add(document.toObject(Event.class));
                                        events_id.add(document.getId());
                                        if (view instanceof RecyclerView) {
                                            Context context = view.getContext();
                                            RecyclerView recyclerView = (RecyclerView) view;
                                            EventAdapter adapter = new EventAdapter(myEvents);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            recyclerView.setAdapter(adapter);

                                            adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                                                @Override
                                                // method to make each event when clicked display separately the event with
                                                // details and the edit button for it
                                                public void onItemClick(int count, Event event ) {
                                                    // Handle item click here, e.g., launch details activity/fragment
                                                    MyEventsAttendee register = new MyEventsAttendee();
                                                    MyEventsAttendee newRegister = register.newInstance(events_id.get(count));
                                                    Fragment fragment = newRegister;
                                                    // Replace fragment for the fetched event
                                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                                                    fragmentTransaction.addToBackStack(null);
                                                    fragmentTransaction.commit();
                                                    Log.d("my brother in chirst","what is wrong");

                                                    if (Objects.equals(event.getEventType(), "Free")) {

                                                        // if event is free show buttons register, deregister
                                                        RegDegFragment buttons = new RegDegFragment();
                                                        RegDegFragment newButtons = RegDegFragment.newInstance(events_id.get(count));
                                                        Fragment fragment2 = newButtons;

                                                        // add register/deregister buttons fragment on top of the event
                                                        FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                                        fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment2);
                                                        fragmentTransaction2.addToBackStack(null);
                                                        fragmentTransaction2.commit();
                                                    } else {

                                                        // if event is paid show buttons interested, not interested
                                                        InterestedInEvent interested = new InterestedInEvent();
                                                        InterestedInEvent newInterest = InterestedInEvent.newInstance(events_id.get(count));
                                                        Fragment fragment3 = newInterest;

                                                        // add interested/not interested buttons fragment on top of the event
                                                        FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                                                        fragmentTransaction3.add(R.id.navigation_host_fragment_content_main, fragment3);
                                                        fragmentTransaction3.addToBackStack(null);
                                                        fragmentTransaction3.commit();

                                                    }
                                                }
                                            });
                                        }
                                    }

                                }
                            }
                        });
                    }

                }
            }
        });

        return view;
    }
}