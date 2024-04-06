package com.example.bebeappthatworks.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.Adapters.OneEventRecyclerView;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleEventFree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleEventFree extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARGM1 = "param1";
    public String eventID;
    View view;

    public List<Event> theEvent = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public SingleEventFree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 the id of the event that was pressed on
     * @return A new instance of fragment SingleEvent.
     */
    public SingleEventFree newInstance(String param1) {
        SingleEventFree fragment = new SingleEventFree();
        Bundle args = new Bundle();
        args.putString(ARGM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate method.
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
     * onCreateView method.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_single_event, container, false);
        //path to the event collection on Firestore
        DocumentReference docRef = db.collection("Events").document(eventID.toString());
        //adding the event that was pressed to the variable
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //specifying the event for the adapter
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    theEvent.add(document.toObject(Event.class));
                    //set the adapter
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