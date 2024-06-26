package com.example.bebeappthatworks;

import android.content.Intent;
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
 * Class for paid events but it's still a work in progress and will have to be changed at least in the UI part
 * Also these are my 6am thoughts so forgive the long comment tmrw me will forget what I did
 */
public class InterestedInEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    //private String mParam2;
    String event_id;
    private String ARGM1 = "param1";
    View view;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public InterestedInEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment InterestedInEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static InterestedInEvent newInstance(String param1) {
        InterestedInEvent fragment = new InterestedInEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event_id = getArguments().getString(ARGM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_interested_in_event, container, false);

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

        return view;
    }
}