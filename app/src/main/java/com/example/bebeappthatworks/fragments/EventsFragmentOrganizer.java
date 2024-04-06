package com.example.bebeappthatworks.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.Adapters.EventAdapter;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EventsFragmentOrganizer extends Fragment {
    //variables needed for the functionality of the class

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> allEvents = new ArrayList<>();
    public List<String> allEventsId = new ArrayList<>();
    public int count = 0;
    private EventAdapter adapter;


    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragmentOrganizer() {
    }

    /**
     * onCreate method.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * onCreateView method.
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflating the view
        view = inflater.inflate(R.layout.fragment_events_list, container, false);
        //setting the instance for Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Fetch the event from Firebase
        db.collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //gets all the events from the events collection
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //adds the events and the ids in arrays for later use
                                allEvents.add(document.toObject(Event.class));
                                allEventsId.add(document.getId());
                            }
                            //set the adapter
                            if (view instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                adapter = new EventAdapter(allEvents);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                //gets the event information for the event that was clicked in the main events page
                                adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int count, Event event) {
                                        //sets the view for one event
                                      OnlyEventView newEvent = new OnlyEventView();
                                      OnlyEventView newEventFinal = newEvent.newInstance(allEventsId.get(count));
                                      //created the fragment for the view
                                      Fragment fragment = newEventFinal;
                                      //code to replace the fragment
                                      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                      fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                                      fragmentTransaction.addToBackStack(null);
                                      fragmentTransaction.commit();

                                    }
                                });
                            }

                        }
                    }
                });
        return view;
    }
}