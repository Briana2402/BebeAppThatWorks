package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.bebeappthatworks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsAttendee extends Fragment {

    private FirebaseFirestore db;
    View view;

    DocumentReference docRef;
    private Attendee attendee;
    private String description, username;

    public SettingsAttendee(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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