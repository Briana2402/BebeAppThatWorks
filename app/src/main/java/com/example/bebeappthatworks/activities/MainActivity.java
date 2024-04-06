package com.example.bebeappthatworks.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bebeappthatworks.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * MainActivty class, functionality of first screen displayed when opening app.
 */
public class MainActivity extends AppCompatActivity {
    //Instance of Firestore database.
    private FirebaseFirestore db;

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {

        /**
         * Listens for changes in the Authentificator instance in Firebase.
         *
         * @param firebaseAuth instance of Firebase authentificator.
         */
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            //gets current user
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            //instantiates Firebase database
            db = FirebaseFirestore.getInstance();

            //Checks if user which just connected is an attendee.
            if (firebaseUser != null) {
                db.collection("Attendees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    /**
                     * Method on Success used to send the user to the AttendeeActivity after authentication
                     *
                     * @queryDocumentSnapshots used to fetch the documents from firebase
                     */
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list) {
                                if(d.getId().equals(firebaseUser.getUid())){
                                    Intent intent = new Intent(MainActivity.this, AttendeeActivity.class);
                                    startActivity(intent);
                                    Log.i("attendee", "yes");
                                }
                            }
                        }
                    }
                });

                //Checks if user which just connected is an organiser.
                db.collection("Organisers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    /**
                     * Method on Success used to send the user to the OrganiserActivity after authentication
                     *
                     * @queryDocumentSnapshots used to fetch the documents from firebase
                     */
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list) {
                                if(d.getId().equals(firebaseUser.getUid())){
                                    Intent intent = new Intent(MainActivity.this, OrganiserActivity.class);
                                    startActivity(intent);
                                    Log.i("organizer", "yes");
                                }
                            }
                        }
                    }
                });
            }
        }
    };

    /**
     * Oncreate method.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);

        super.onCreate(savedInstanceState);

        //Initiate buttons.
        Button loginButton;
        Button guestButton;
        Button registerButton;
        Button registerOrganiser;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Connect button variables to instances in the layout.
        loginButton = (Button) findViewById(R.id.Login); //login button
        guestButton = (Button) findViewById(R.id.Guest); //guestbutton
        registerButton = (Button) findViewById(R.id.button4); //register attendee button
        registerOrganiser = (Button) findViewById(R.id.button5); //register organiser button

        //Chnages intent based on button pressed.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * When the login button is clicked it sends the user to the login page
             */
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * When the guest button is clicked it sends the user to the quest main page
             */
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuestActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * When the register attendee button is clicked it sends the user to the register attendee page
             */
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterAttendeeActivity.class);
                startActivity(intent);
            }
        });

        registerOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * When the register organiser button is clicked it sends the user to the register organiser page
             */
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterOrganisationActivity.class);
                startActivity(intent);
            }
        });



    }
}