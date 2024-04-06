package com.example.bebeappthatworks.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bebeappthatworks.Adapters.EventAdapter;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterForEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventButton extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private final String ARGM1 = "param1";
    private String event_type;
    private EventAdapter adapter;

    public List<Event> theEvent = new ArrayList<>();

    //public Button button;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String event_id;

    View view;

    public EventButton() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RegisterForEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static EventButton newInstance(String param1) {
        EventButton fragment = new EventButton();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //get the id of the event
            event_id = getArguments().getString(ARGM1);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.edit_event_button, container, false);

        Button button = view.findViewById(R.id.eventDetails);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelEventFragment add_button = new CancelEventFragment();
                CancelEventFragment new_button = add_button.newInstance(event_id);
                Fragment fragment2 = new_button;
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
            }
        });

        return view;
    }
}