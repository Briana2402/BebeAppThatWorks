package com.example.bebeappthatworks;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;
import android.app.Activity;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.Manifest;
import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;
import java.util.UUID;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass to create events for organisers.
 * Use the {@link EventCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @pre Organzier account is logged in.
 * @post Event is created by organizer.
 */
public class EventCreateFragment extends Fragment {

    //Used view.
    View view;

    //Initialising used EditText components.
    private EditText eventNameEdt, eventDurationEdt, eventDescriptionEdt, eventLocationEdt, eventCapacityEdt, eventDateEdt, eventLinkEdt;

    // creating variable for buttons.
    private Button submitEventBtn;
    private Button captureImageButton;

    // Creating a strings for storing values from EditText fields.
    private String eventLocation, eventDate, eventName, eventDescription, eventCapacity, eventDuration, eventType, imageUrl, eventLink;

    private CheckBox paidEvent;

    //Initialising instance of Firebase database.
    private FirebaseFirestore db;

    //Initialising instance of Firebase authentification.
    private FirebaseAuth mAuth;

    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventCreateFragment.
     */
    public static EventCreateFragment newInstance() {
        EventCreateFragment fragment = new EventCreateFragment();
        return fragment;
    }

    public EventCreateFragment() {
        // Required empty public constructor
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
     * Creating the view for the event creation.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_event_create, container, false);
        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Initializing the EditText fields, checkbox and ImageView.
        eventLinkEdt = view.findViewById(R.id.linkPaid);
        eventLinkEdt.setVisibility(View.INVISIBLE);
        imageView = view.findViewById(R.id.cover_image);
        eventNameEdt = view.findViewById(R.id.idEdtEventName);
        eventDescriptionEdt = view.findViewById(R.id.idEdtEventDescription);
        eventLocationEdt = view.findViewById(R.id.idEdtEventLocation);
        eventCapacityEdt = view.findViewById(R.id.idEdtEventCapacity);
        eventDurationEdt = view.findViewById(R.id.idEdtEventDuration);
        eventDateEdt = view.findViewById(R.id.idEdtEventDate);
        submitEventBtn = view.findViewById(R.id.idBtnSubmitEvent);
        paidEvent = (CheckBox) view.findViewById(R.id.checkBox);
        captureImageButton = view.findViewById(R.id.event_cover);

        //Code for Checkbox paidEvent to check type of event and show or hide eventLink field.
        paidEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paidEvent.isChecked()) {
                    eventType = "Paid";
                    eventLinkEdt.setVisibility(View.VISIBLE);
                }
                if (!paidEvent.isChecked()) {
                    eventType = "Free";
                    eventLinkEdt.setVisibility(View.INVISIBLE);
                }
            }
        });


        // adding on click listener for event submitting button.
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
                String creator = mAuth.getCurrentUser().getUid();

                //Check if event is free or paid.
                if (paidEvent.isChecked()) {
                    eventType = "Paid";
                } else {
                    eventType = "Free";
                    eventLink = "";
                }

                if (eventType.equals("Paid")) {
                    eventLink = eventLinkEdt.getText().toString();
                }

                // validating the mandatory text fields.
                if (TextUtils.isEmpty(eventName)) {
                    eventNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(eventDescription)) {
                    eventDescriptionEdt.setError("Please enter Event Description");
                } else if (TextUtils.isEmpty(eventDuration)) {
                    eventDurationEdt.setError("Please enter Event Duration");
                } else if (TextUtils.isEmpty(eventLocation)) {
                    eventLocationEdt.setError("Please enter Event Location");
                } else if (paidEvent.isChecked() && TextUtils.isEmpty(eventLink)) {
                    eventLinkEdt.setError("Please enter Event link");
                    Toast.makeText(getActivity(), "Please enter Event link", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(eventLocation)) {
                    eventLocationEdt.setError("Please enter Event Location");
                } else {
                    if (TextUtils.isEmpty(eventDate)) {
                        eventDateEdt.setError("Please enter Event Date");
                    } else {
                        // calling method to add data to Firebase Firestore if all mandatory fields are filled.
                        addDataToFirestore(eventName, eventDescription, eventDuration, eventDate, eventLocation, eventCapacity, imageUrl, eventType, eventLink, creator);
                    }
                }
            }
        });


        //Button to take picture
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your captureImage method or perform your action here
                captureImage(v);
            }
        });
        return view;
    }


    /**
     * Sends the information of the created event to a document in firebase databse Events collection.
     *
     * @param eventName        Inputted event name.
     * @param eventDescription Inputted event description.
     * @param eventDuration    Inputted event duration.
     * @param eventDate        Inputted event date.
     * @param eventLocation    Inputted event location.
     * @param eventCapacity    Inputted event capacity.
     * @param imageUrl         Inputted event image.
     * @param eventType        Selected event type.
     * @param eventLink        Inputted event link.
     * @param creator          Inputted event creator.
     * @pre All mandatory fields are inputted.
     * @post Event instance is created in Firebase database under "Events" collection and either also under "PaidEvents"
     * or "FreeEvents".
     */
    private void addDataToFirestore(String eventName, String eventDescription, String eventDuration, String eventDate, String eventLocation, String eventCapacity, String imageUrl, String eventType, String eventLink, String creator) {

        //Creating collection references for the events collection, the paid events collection and the free events collection.
        CollectionReference dbEvents = db.collection("Events");
        CollectionReference dbFreeEvents = db.collection("FreeEvents");
        CollectionReference dbPaidEvents = db.collection("PaidEvents");

        // Create Event object and add it to the correct collections in Firebase database.
        Event events = new Event(eventLocation, eventDuration, eventName, eventDate, eventCapacity, eventDescription, imageUrl, eventType, eventLink, creator);
        if (eventType.equals("Free")) {
            dbFreeEvents.add(events);
        } else if (eventType.equals("Paid")) {
            dbPaidEvents.add(events);
        }
        dbEvents.add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful, we are displaying a success toast message.
                Toast.makeText(getActivity(), "Your Event has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getActivity(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     *
     * @param view
     */
    public void captureImage(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //Uri imageUri = data.getData();
                //Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    imageView.setImageBitmap(imageBitmap);
                    uploadImageToFirebase(imageBitmap);
                    // Save the full-size image to a file
                } else {
                    Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // Handle the case where the user cancels taking a picture
            Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other cases, such as if there's an error
            Toast.makeText(getContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     * @param imageBitmap
     */
    private void uploadImageToFirebase(Bitmap imageBitmap) {
        // Firebase Storage reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString());

        // Convert Bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload task
        UploadTask uploadTask = storageRef.putBytes(data);

        // Register observers to handle success, failure, and progress
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL after successful upload
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        imageUrl = downloadUrl.toString();  // Correct URL retrieval
                        Log.i("imageUrl", imageUrl);
                        // Use the imageUrl to display or store in your app
                    }
                });
            }
        });
    }
}