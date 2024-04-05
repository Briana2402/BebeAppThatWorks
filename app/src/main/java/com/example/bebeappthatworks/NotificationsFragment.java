package com.example.bebeappthatworks;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Fragment for displaying notifications when attendee is logged in and an
 * event they are registered for got cancelled.
 */
public class NotificationsFragment extends Fragment {

    //Initialise Firebase instance.
    public final FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Notifications to be displayed.
    public List<Notification> allNotifications = new ArrayList<>();

    //Notification adapter.
    private NotificationAdapter adapter;

    //Instance of Firebase Authentificator.
    private FirebaseAuth mAuth;

    //View.
    View view;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    /**
     * OnCreate method.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * OnCreateView method which displays the notifications. s
     *
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
        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.notifications_adapter, container, false);
        displayNotifications();
        return view;
    }

    /**
     * Displays all notifications associated with logged in user.
     *
     * @pre Attendee user is logged in.
     * @post If notifications exists, they will be displayed on the attendee notification page.
     */
    private void displayNotifications(){
        CollectionReference attendeeRef = db.collection("Attendees").document(mAuth.getCurrentUser().getUid()).collection("my notifications");
        attendeeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        allNotifications.add(document.toObject(Notification.class));
                    }
                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        adapter = new NotificationAdapter(allNotifications);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);

                    }

                }
            }
        });
    }
}