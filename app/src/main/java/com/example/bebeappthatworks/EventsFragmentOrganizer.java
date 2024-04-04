package com.example.bebeappthatworks;

import static com.google.android.gms.tasks.Tasks.await;

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

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class EventsFragmentOrganizer extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> allEvents = new ArrayList<>();
    public List<String> allEventsId = new ArrayList<>();
    public int count = 0;

    private RecyclerView recyclerView;
    private List<Event> eventList;
    private EventAdapter adapter;

    private FirebaseAuth mAuth;

    private boolean tryTest;

    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragmentOrganizer() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tryTest = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_list, container, false);
        mAuth = FirebaseAuth.getInstance();

        // Set the adapter
        db.collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                allEvents.add(document.toObject(Event.class));
                                allEventsId.add(document.getId().toString());
                            }
                            if (view instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                adapter = new EventAdapter(allEvents);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                // recyclerView.setAdapter(new MyItemRecyclerViewAdapter(allEvents));
                                adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int count, Event event) {
                                      OnlyEventView newEvent = new OnlyEventView();
                                      OnlyEventView newEventFinal = newEvent.newInstance(allEventsId.get(count));
                                      Fragment fragment = newEventFinal;
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