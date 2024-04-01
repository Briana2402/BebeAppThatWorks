package com.example.bebeappthatworks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleEventFree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleEventPaid extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARGM1 = "param1";
    public String eventID;

    private Event event;

    View view;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public List<Event> theEvent = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public SingleEventPaid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1
     * @return A new instance of fragment SingleEvent.
     */
    // TODO: Rename and change types and number of parameters
    public SingleEventPaid newInstance(String param1) {
        SingleEventPaid fragment = new SingleEventPaid();
        Bundle args = new Bundle();
        args.putString(ARGM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("TEROG",ARGM1);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventID = getArguments().getString(ARGM1);
        }
        Log.i("TEROG",eventID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_single_event, container, false); //??
        DocumentReference docRef = db.collection("Events").document(eventID.toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    theEvent.add(document.toObject(Event.class));
                    if (view instanceof RecyclerView) {
                        RecyclerView recyclerView = (RecyclerView) view;
                        recyclerView.setAdapter(new OneEventRecyclerView(theEvent));
                    }
                }

            }
        });

        Button button = view.findViewById(R.id.registerFree); //should be interested new button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("gets here", event_id.toString());
                Map<String, String> data = new HashMap<>();
                data.put("type", "registered");
                //data.put("type of event", event_type);
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(eventID).set(data);
            }
        });

        Button dereg = view.findViewById(R.id.deregisterFree); //should be remove new button

        dereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("gets here", event_id.toString());
                //Map<String, String> data = new HashMap<>();
                //data.put("type", "deregistered");
                //data.put("type of event", event_type);
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(eventID).delete();
            }
        });
        return view;
    }
}