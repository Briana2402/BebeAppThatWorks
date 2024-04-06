package com.example.bebeappthatworks.fragments;

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

public class SettingsOrganiser extends Fragment {

    //initialising variables needed in the class
    private FirebaseFirestore db;
    View view;

    DocumentReference docRef;
    private String description, username;

    //empty constructor needed for Firebase
    public SettingsOrganiser(){

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
     * @return the view of the settings page
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflating the view
        view = inflater.inflate(R.layout.activity_settings_organiser, container, false);
        //setting up the firebase instances
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //specifying the connection with the UI and initialising the buttons and fields
        Button backBtn = (Button) view.findViewById(R.id.backtoprofileOrganiser);
        EditText editTextDescription = view.findViewById(R.id.description_fieldOrganiser);
        EditText editTextUsername = view.findViewById(R.id.update_usernameOrganiser);

        //defining the path in the database
        docRef = db.collection("Organisers").document((mAuth.getCurrentUser().getUid()));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button saveBtn = (Button) view.findViewById(R.id.save_changesOrganiser);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updating the document of the orgabiser that changed the profile
                description = editTextDescription.getText().toString();
                username = editTextUsername.getText().toString();
                updateAccount(description, username);
            }
        });
        return view;
    }

    //Method to update the account in Firebase
    private void updateAccount(String description, String username) {
        //setting the fields and data for the information that has to be added to the database
        Map<String, Object> newData = new HashMap<>();
        newData.put("description", description);
        newData.put("username", username);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //updating the database to add the new information completed in the fields of the UI
        docRef.update(newData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast message for the user
                Toast.makeText(getActivity(), "Account Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

}