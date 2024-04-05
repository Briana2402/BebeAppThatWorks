package com.example.bebeappthatworks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InterestedInEvent#newInstance} factory method to
 * create an instance of this fragment.
 * Class for paid events in order for the user to be added as interested in the Firestore
 */
public class InterestedInEvent extends Fragment {

    //setting the needed fields
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    String event_id;
    private final String ARGM1 = "param1";
    View view;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public InterestedInEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 id of the events to be added.
     * @return A new instance of fragment InterestedInEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static InterestedInEvent newInstance(String param1) {
        InterestedInEvent fragment = new InterestedInEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event_id = getArguments().getString(ARGM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.them_button_with_interest, container, false);

        Button button = view.findViewById(R.id.buttonInterested);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("gets here", event_id.toString());
                Map<String, String> data = new HashMap<>();
                data.put("type", "interested");
                //data.put("type of event", event_type);
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).set(data);
            }
        });

        Button dereg = view.findViewById(R.id.buttonNotIntr);

        dereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).delete();
            }
        });

        return view;
    }
}