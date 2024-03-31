package com.example.bebeappthatworks;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.example.bebeappthatworks.ui.eventCreation.EventCreationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organiser{

    public String id;

    public Event event;

    public Organiser(){

    }

    public Organiser(String id, Event event){
        this.id = id;
        this.event = event;
    }

//    public void addEvent(Event event) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        CollectionReference dbOrg = db.collection("Organisers");
//
//
//
//        dbOrg.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot organiser : task.getResult()) {
//                        //myEvents.add(event.toObject(Event.class));
//                        if(organiser.getId().toString() == mAuth.getUid().toString()) {
//                            Event array = new Event();
//
//                        }
//                    }
//                }
//            }
//        });
//
//        myEvents.add(event);
//
//
//        dbOrg.document(mAuth.getUid().toString())
//                .set("created events", myEvents)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.i("organiser array", "updated");
//                        //Toast.makeText(Organiser.this, "Your Event has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
