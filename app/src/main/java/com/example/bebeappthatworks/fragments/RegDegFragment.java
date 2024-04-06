package com.example.bebeappthatworks.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bebeappthatworks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterForEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegDegFragment extends Fragment {

    //variables needed in the class
    private static final String ARG_PARAM1 = "param1";
    private String ARGM1 = "param1";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String event_id;

    View view;

    public RegDegFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 event id
     * @return A new instance of fragment RegisterForEvent.
     */
    public static RegDegFragment newInstance(String param1) {
        RegDegFragment fragment = new RegDegFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
            //get the id of the event
            event_id = getArguments().getString(ARGM1);
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
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.them_buttons, container, false);

        //setting the button for register to an event from the UI
        Button button = view.findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding the id of the event to the collection 'my events' of the uer logged in
                Map<String, String> data = new HashMap<>();
                data.put("type", "registered");
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).set(data);
            }
        });

        //setting the deregister button
        Button dereg = view.findViewById(R.id.buttonDeregister);

        dereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleting the event id from the 'my events' collection so that it does not show in the My Events page
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).delete();
            }
        });

        return view;
    }
}