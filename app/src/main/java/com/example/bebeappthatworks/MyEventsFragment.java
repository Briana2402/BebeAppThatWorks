package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyEventsFragment extends Fragment {
    private final int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // array list of events that are fetched
    public List<Event> myEvents = new ArrayList<>();

    // array list of the id's of all fetched events
    public List<String> allEventsId = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    View view;

    private EventAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    /**
     * onCreateView method to fetch events created by a user and display them in "my events" page
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
        view = inflater.inflate(R.layout.fragment_created_events, container, false);

        // Set the adapter
        CollectionReference eventsRef = db.collection("Events");
        // querys the database for events where the creater is the current user
        Query query = eventsRef.whereEqualTo("eventCreator", mAuth.getCurrentUser().getUid());

        //fetching the creator of the event in the event document
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            // fetched events and their id if they exist an dare created by a user
            // then sets the recycler view to display the events in the"my events" page
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        myEvents.add(document.toObject(Event.class));
                        allEventsId.add(document.getId());
                    }

                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        adapter = new EventAdapter(myEvents);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);


                        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                            @Override
                            // method to make each event when clicked display separately the event with
                            // details and the edit button for it
                            public void onItemClick(int count, Event event ) {
                                // Handle item click here, e.g., launch details activity/fragment
                                MyEventOrganizer eventTest = new MyEventOrganizer();
                                MyEventOrganizer eventFinal = eventTest.newInstance(allEventsId.get(count));
                                Fragment fragment = eventFinal;
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main,fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                EventButton add_button = new EventButton();
                                EventButton new_button = EventButton.newInstance(allEventsId.get(count));
                                Fragment fragment2 = new_button;
                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment2);
                                fragmentTransaction2.addToBackStack(null);
                                fragmentTransaction2.commit();
                            }
                        });
                    }
                }
            }
        });

        return view;
    }
}