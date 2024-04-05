package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyEventsFragmentOrganiser extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> myEvents = new ArrayList<>();

    public List<String> allEventsId = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    View view;

    private EventAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragmentOrganiser() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_created_events, container, false);

        // Set the adapter
        CollectionReference eventsRef = db.collection("Events");

        Query query = eventsRef.whereEqualTo("eventCreator", mAuth.getCurrentUser().getUid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        myEvents.add(document.toObject(Event.class));
                        allEventsId.add(document.getId().toString());
                        //Log.i("miauuu", "miau miau");
                    }

                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        adapter = new EventAdapter(myEvents);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);

                        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int count, Event event ) {
                                Log.d("FOUND","IT");
                                // Handle item click here, e.g., launch details activity/fragment
                                MyEventOrganizer eventTest = new MyEventOrganizer();
                                MyEventOrganizer eventFinal = eventTest.newInstance(allEventsId.get(count));
                                Fragment fragment = eventFinal;
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main,fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                EventButton add_button = new EventButton();
                                EventButton new_button = add_button.newInstance(allEventsId.get(count));
                                Fragment fragment2 = new_button;
                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment2);
                                fragmentTransaction2.addToBackStack(null);
                                fragmentTransaction2.commit();


                            }
                        });
                    }
                }
            }
        });

        return view;
    }
}