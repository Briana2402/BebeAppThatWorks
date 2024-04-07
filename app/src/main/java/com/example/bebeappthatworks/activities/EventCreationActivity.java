package com.example.bebeappthatworks.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

public class EventCreationActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText eventNameEdt, eventDurationEdt, eventDescriptionEdt, eventLocationEdt, eventCapacityEdt, eventDateEdt, eventLinkEdt;

    // creating variable for button
    private Button submitEventBtn;

    private Button captureCoverBtn;

    private Uri imageUri;
    private CheckBox paidEvent;

    // creating a strings for storing
    // our values from edittext fields.
    private String eventLocation, eventDate, eventName, eventDescription, eventCapacity, eventDuration, eventType, imageUrl, eventLink;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_create);
        eventLinkEdt = findViewById(R.id.linkPaid);
        eventLinkEdt.setVisibility(View.INVISIBLE);

        //imageUrl = findViewById(R.id.cover_image);
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
        captureCoverBtn = findViewById(R.id.event_cover);
        CheckBox paidEvent = (CheckBox) findViewById(R.id.checkBox);

        paidEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(paidEvent.isChecked()){
                    eventType = "Paid";
                    eventLinkEdt.setVisibility(View.VISIBLE);
                }
                if(!paidEvent.isChecked()) {
                    eventType = "Free";
                    eventLinkEdt.setVisibility(View.INVISIBLE);
                }
            }
        });


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

                if(paidEvent.isChecked()){
                    eventType = "Paid";
                } else {
                    eventType = "Free";
                    eventLink = "";
                }

                if(eventType.equals("Paid")){
                    eventLink = eventLinkEdt.getText().toString();
                }

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(eventName)) {
                    eventNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(eventDescription)) {
                    eventDescriptionEdt.setError("Please enter Event Description");
                } else if (TextUtils.isEmpty(eventDuration)) {
                    eventDurationEdt.setError("Please enter Event Duration");
                } else if (TextUtils.isEmpty(eventLocation)) {
                    eventLocationEdt.setError("Please enter Event Location");
                } else {
                    if (TextUtils.isEmpty(eventDate)) {
                        eventDateEdt.setError("Please enter Event Date");
                    } else {
                        // calling method to add data to Firebase Firestore.
                        addDataToFirestore(eventName, eventDescription, eventDuration, eventDate, eventLocation, eventCapacity, imageUri, eventType, eventLink);
                    }
                }
            }
        });


    }

    private void addDataToFirestore(String eventName, String eventDescription, String eventDuration, String eventDate, String eventLocation, String eventCapacity, Uri imageUri, String eventType, String eventLink) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbEvents = db.collection("Events");
        CollectionReference dbFreeEvents = db.collection("FreeEvents");
        CollectionReference dbPaidEvents = db.collection("PaidEvents");
        FirebaseAuth mAuth;



        mAuth = FirebaseAuth.getInstance();

        // adding our data to our courses object class.
        Event events = new Event(eventLocation, eventDuration, eventName, eventDate, eventCapacity, eventDescription, imageUrl, eventType, eventLink, mAuth.getCurrentUser().getUid());
        if (eventType.equals("Free")) {
            dbFreeEvents.add(events);
        } else if (eventType.equals("Paid")) {
            dbPaidEvents.add(events);
        }
        // below method is use to add data to Firebase Firestore.
        dbEvents.add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(EventCreationActivity.this, "Your Event has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                finish();
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

    public void captureImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Log.i("imageBitmap", imageBitmap.toString());
            this.imageUri = getImageUri(imageBitmap);
        } else if (resultCode == RESULT_CANCELED) {
            // Handle the case where the user cancels taking a picture
            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other cases, such as if there's an error
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, fileName, null);
        return Uri.parse(path);
    }
}
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        } else if (resultCode == RESULT_CANCELED) {
//            // Handle the case where the user cancels taking a picture
//            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
//        } else {
//            // Handle other cases, such as if there's an error
//            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
//        }
//}

