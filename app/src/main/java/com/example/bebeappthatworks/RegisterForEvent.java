package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterForEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterForEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String ARGM1 = "param1";
    private String event_type;

    //public Button button;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String event_id;

    View view;

    public RegisterForEvent() {
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
    public static RegisterForEvent newInstance(String param1) {
        RegisterForEvent fragment = new RegisterForEvent();
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
        view = inflater.inflate(R.layout.register_button, container, false);

        Button button = view.findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("gets here", event_id.toString());
                Map<String, String> data = new HashMap<>();
                data.put("type", "registered");
                //data.put("type of event", event_type);
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).set(data);
            }
        });
        Button details = view.findViewById(R.id.buttonSeeDetails);
        Button dereg = view.findViewById(R.id.buttonDeregister);

        dereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("gets here", event_id.toString());
                //Map<String, String> data = new HashMap<>();
                //data.put("type", "deregistered");
                //data.put("type of event", event_type);
                db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my events").document(event_id).delete();
            }
        });



        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlyEventView newEvent = new OnlyEventView();
                OnlyEventView newEventFinal = newEvent.newInstance(event_id);
                Fragment fragment = newEventFinal;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}