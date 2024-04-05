package com.example.bebeappthatworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.bebeappthatworks.ui.eventCreation.EventCreationActivity;
import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.example.bebeappthatworks.ui.register.RegisterAttendeeActivity;
import com.example.bebeappthatworks.ui.register.RegisterOrganisationActivity;
//import com.example.bebeappthatworks.ui.register.RegisterActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
            if (firebaseUser != null) {
                db.collection("Attendees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
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

                db.collection("Organisers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);

        super.onCreate(savedInstanceState);

        Button loginButton;
        Button guestButton;
        Button registerButton;
        Button registerOrganiser;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.Login); //login button
        guestButton = (Button) findViewById(R.id.Guest); //guestbutton
        registerButton = (Button) findViewById(R.id.button4); //register attendee button
        registerOrganiser = (Button) findViewById(R.id.button5); //register organiser button

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuestActivity.class);
                startActivity(intent);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterAttendeeActivity.class);
                startActivity(intent);
            }
        });


        registerOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterOrganisationActivity.class);
                startActivity(intent);
            }
        });



    }
}