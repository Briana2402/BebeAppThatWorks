package com.example.bebeappthatworks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsAttendee extends Fragment {

    private FirebaseFirestore db;
    View view;

    DocumentReference docRef;
    private String description, username;

    public SettingsAttendee(){
    //empty constructor needed for Firebase
    }

    /**
     * onCreate method.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_settings_attendee, container, false);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button backBtn = (Button) view.findViewById(R.id.backtoprofileAttendee);
        EditText editTextDescription = view.findViewById(R.id.description_fieldAttendee);
        EditText editTextUsername = view.findViewById(R.id.update_usernameAttendee);
        docRef = db.collection("Attendees").document((mAuth.getCurrentUser().getUid()));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button saveBtn = (Button) view.findViewById(R.id.save_changesAttendee);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = editTextDescription.getText().toString();
                username = editTextUsername.getText().toString();
                Log.i("username", username);
                Log.i("description", description);
                updateAccount(description, username);
            }
        });
        return view;
    }

    private void updateAccount(String description, String username) {
        Map<String, Object> newData = new HashMap<>();
        newData.put("description", description);
        newData.put("username", username);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        docRef.update(newData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Account Updated", Toast.LENGTH_SHORT).show();
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot document = task.getResult();
//                        attendee = document.toObject(Attendee.class);
//                        attendee.setDescription(description);
//                        attendee.setUsername(username);
//                    }
//                });
            }
        });
    }

}