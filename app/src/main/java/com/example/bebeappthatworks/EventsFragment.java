package com.example.bebeappthatworks;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EventsFragment extends Fragment {

    // TODO: Customize parameter argument names
    //private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private final int mColumnCount = 1;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> allEvents = new ArrayList<>();

    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        db.collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                allEvents.add(document.toObject(Event.class));
                                //Log.i(allEvents.get(0).getEventDate(), "miau miau");
                            }

                            if (view instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                if (mColumnCount <= 1) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                } else {
                                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                }
                                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(allEvents));
                                //Log.i(allEvents.get(0).getEventDate(), "miau miau");
                            }

                        }
                    }
                });

        return view;
    }
}