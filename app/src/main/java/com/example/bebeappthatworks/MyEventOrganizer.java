package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyEventOrganizer#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Fragment to display a singel event view when an organiser presses on said event.
 */
public class MyEventOrganizer extends Fragment {

    //Parameter send to class through constructor.
    private static final String ARGM1 = "param1";

    //Event ID inputted through the constructor.
    public String eventID;

    //List to contain event to be displayed.
    public List<Event> theEvent = new ArrayList<>();

    //Event adapter used.
    private EventAdapter adapter;

    //View.
    View view;

    //Initialising Firebase database instance.
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyEventOrganizer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 eventID sent trrough connstructor.
     * @return A new instance of fragment MyEventOrganizer.
     */
    public MyEventOrganizer newInstance(String param1) {
        MyEventOrganizer fragment = new MyEventOrganizer();
        Bundle args = new Bundle();
        args.putString(ARGM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * OnCreate method. Sets parameter.
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
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View with displayed one event
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one_event, container, false);

        DocumentReference docRef = db.collection("Events").document(eventID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    theEvent.add(document.toObject(Event.class));
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    adapter = new EventAdapter(theEvent);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }

            }
        });

        return view;
    }
}