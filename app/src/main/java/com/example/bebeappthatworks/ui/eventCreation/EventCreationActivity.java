package com.example.bebeappthatworks.ui.eventCreation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bebeappthatworks.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventCreationActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText eventNameEdt, eventDurationEdt, eventDescriptionEdt, eventLocationEdt, eventCapacityEdt, eventDateEdt;

    // creating variable for button
    private Button submitEventBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String eventLocation, eventDate, eventName,eventDescription,eventCapacity,eventDuration;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        eventNameEdt = findViewById(R.id.idEdtEventName);
        eventDescriptionEdt = findViewById(R.id.idEdtEventDescription);
        eventLocationEdt = findViewById(R.id.idEdtEventLocation);
        eventCapacityEdt = findViewById(R.id.idEdtEventCapacity);
        eventDurationEdt = findViewById(R.id.idEdtEventDuration);
        eventDateEdt = findViewById(R.id.idEdtEventDate);
        submitEventBtn = findViewById(R.id.idBtnSubmitEvent);

        // adding on click listener for button
        submitEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                eventName = eventNameEdt.getText().toString();
                eventDescription = eventDescriptionEdt.getText().toString();
                eventDate = eventDateEdt.getText().toString();
                eventDuration = eventDurationEdt.getText().toString();
                eventLocation = eventLocationEdt.getText().toString();
                eventCapacity = eventCapacityEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(eventName)) {
                    eventNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(eventDescription)) {
                    eventDescriptionEdt.setError("Please enter Event Description");
                } else if (TextUtils.isEmpty(eventDuration)) {
                    eventDurationEdt.setError("Please enter Event Duration");
                } else  if (TextUtils.isEmpty(eventLocation)) {
                    eventLocationEdt.setError("Please enter Event Location");
                } else {
                    if (TextUtils.isEmpty(eventDate)) {
                        eventDateEdt.setError("Please enter Event Date");
                    } else {
                        // calling method to add data to Firebase Firestore.
                        addDataToFirestore(eventName, eventDescription, eventDuration, eventDate, eventLocation, eventCapacity);



                    }
                }
            }
        });
    }

    private void addDataToFirestore(String eventName, String eventDescription, String eventDuration, String eventDate, String eventLocation, String eventCapacity) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbEvents = db.collection("Events");

        // adding our data to our courses object class.
        Event events = new Event(eventLocation, eventDuration, eventName, eventDate,eventCapacity, eventDescription);

        // below method is use to add data to Firebase Firestore.
        dbEvents.add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(EventCreationActivity.this, "Your Event has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(EventCreationActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
