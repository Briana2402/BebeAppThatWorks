package com.example.bebeappthatworks;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleEventFree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARGM1 = "param1";
    public String eventID;

    public String eventName;

    private Event event;

    View view;

    private boolean sent;

    public List<Event> allAttendees = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CancelEventFragment() {
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
    public CancelEventFragment newInstance(String param1) {
        CancelEventFragment fragment = new CancelEventFragment();
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
        view = inflater.inflate(R.layout.fragment_cancel_event, container, false);
        DocumentReference docRef = db.collection("Events").document(eventID.toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    event = document.toObject(Event.class);
                    eventName = event.getEventName();
                }
            }
        });


        Button cancel = view.findViewById(R.id.cancelEvent);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
                db.collection("Events").document(eventID.toString()).delete();
                MyEventsFragment fragment = new MyEventsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void sendNotification(){
        Notification newNotication = new Notification(eventName,"This event has been canceled.");
        CollectionReference dbEvents = db.collection("Notification");
        dbEvents.add(newNotication);

        db.collection("Attendees")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot attendee : task.getResult()) {
                            // Query the subcollection to check if the document exists
                            db.collection("Attendees").document(attendee.getId()).collection("my events")
                                    .document(eventID.toString())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            // Document exists in the subcollection
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("eventName", newNotication.getEventName());
                                            data.put("message", newNotication.getMessage());
                                            // Add additional data as needed

                                            // Set data into the "my notifications" subcollection
                                            db.collection("Attendees")
                                                    .document(attendee.getId())
                                                    .collection("my notifications").document()
                                                    .set(data)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d("notif", "Notification added successfully.");
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("sad", "Error adding notification: ", e);
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("wrong subcollection", "Error querying subcollection: ", e);
                                    });
                        }
                    } else {
                        Log.e("not document", "Error getting documents: ", task.getException());
                    }
                });
//        CollectionReference citiesRef = db.collection("Attendees").;
//
//        citiesRef.whereIn("country", Arrays.asList("USA", "Japan"));


    }
}