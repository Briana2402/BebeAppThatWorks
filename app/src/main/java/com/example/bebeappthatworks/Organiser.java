package com.example.bebeappthatworks;

import com.example.bebeappthatworks.ui.eventCreation.Event;

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
