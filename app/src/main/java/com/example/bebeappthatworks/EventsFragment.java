package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class EventsFragment extends Fragment {

    // Declaring the variables.
    private static final String ARG_COLUMN_COUNT = "column-count";
    private final int mColumnCount = 1;

    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Event> allEvents = new ArrayList<>();
    public List<String> allEventsId = new ArrayList<>();
    public int count = 0;

    private RecyclerView recyclerView;
    private List<Event> eventList;
    private EventAdapter adapter;

    public final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private boolean tryTest;

    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {
    }


    /**
     * Called when the activity is starting. Method for initialization
     * most of the necessary variables and methods.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tryTest = false;

    }

    /**
     * Called when the activity is starting.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //using the specefied layout
        view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        db.collection("Events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // if the task is successful the add all the event from the firebase
                        // into a list.
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                allEvents.add(document.toObject(Event.class));
                                allEventsId.add(document.getId());
                            }
                            //setting the adapter
                            if (view instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                adapter = new EventAdapter(allEvents);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {

                                    /**
                                     * Method used to implement the functionality of clicking
                                     * on an event from a list of them and then moving the user
                                     * to another fragment that showcases all the details of the
                                     * specific event.
                                     * @param count - the index of the specific event ID in the list
                                     *              declared preveously
                                     * @param event - the event whose information has to be shared
                                     */
                                    @Override
                                    public void onItemClick(int count, Event event) {

                                        // Fetch event clicked on
                                        RegisterForEvent register = new RegisterForEvent();
                                        RegisterForEvent newRegister = RegisterForEvent.newInstance(allEventsId.get(count));
                                        Fragment fragment = newRegister;

                                        // Replace fragment for the fetched event
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

                                            if (Objects.equals(event.getEventType(), "Free")) {

                                                // if event is free show buttons register, deregister
                                                RegDegFragment buttons = new RegDegFragment();
                                                RegDegFragment newButtons = RegDegFragment.newInstance(allEventsId.get(count));
                                                Fragment fragment2 = newButtons;

                                                // add register/deregister buttons fragment on top of the event
                                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                                fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment2);
                                                fragmentTransaction2.addToBackStack(null);
                                                fragmentTransaction2.commit();
                                            } else {
                                                InterestedInEvent interested = new InterestedInEvent();
                                                InterestedInEvent newInterest = InterestedInEvent.newInstance(allEventsId.get(count));
                                                Fragment fragment3 = newInterest;

                                                // add interested/not interested buttons fragment on top of the event
                                                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                                                fragmentTransaction3.add(R.id.navigation_host_fragment_content_main, fragment3);
                                                fragmentTransaction3.addToBackStack(null);
                                                fragmentTransaction3.commit();
                                            }


                                    }
                                });
                            }

                        }
                    }
                });
        return view;
    }

    /**
     * Method used in order to move from one fragment to another
     * fragment within an activity.
     * @param fragment - the fragment to be replaced
     */
    private void replaceFragment(Fragment fragment) {
        // add register/deregister buttons fragment on top of the event
        FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        fragmentTransaction2.add(R.id.navigation_host_fragment_content_main, fragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();
    }

}