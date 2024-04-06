package com.example.bebeappthatworks.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bebeappthatworks.Adapters.OneEventRecyclerView;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OneEventFragment extends Fragment {

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // array list to be used when fetching all events
    public List<Event> allEvents = new ArrayList<>();

    // additional array list to be used for displaying one event using the same recyclerview
    public List<Event> theEvent = new ArrayList<>();

    View view;

    // empty constructor
    public OneEventFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Creates the view for the first event fetched and sets it
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return @view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one_event, container, false);

        db.collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                allEvents.add(document.toObject(Event.class));
                            }
                            theEvent.add(allEvents.get(0));
                            if (view instanceof RecyclerView) {
                                RecyclerView recyclerView = (RecyclerView) view;
                                recyclerView.setAdapter(new OneEventRecyclerView(theEvent));
                            }

                        }
                    }
                });

        return view;
    }


}
