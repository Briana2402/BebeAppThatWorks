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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
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

import com.example.bebeappthatworks.MainActivity;
import android.Manifest;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.example.bebeappthatworks.ui.eventCreation.EventCreationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.forgotPassword.ForgotPasswordActivity;
import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EventCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    // creating variables for our edit text
    private EditText eventNameEdt, eventDurationEdt, eventDescriptionEdt, eventLocationEdt, eventCapacityEdt, eventDateEdt, eventLinkEdt;

    // creating variable for button
    private Button submitEventBtn;

    private Button captureCoverBtn;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventCreateFragment newInstance(String param1, String param2) {
        EventCreateFragment fragment = new EventCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_event_create, container, false);

        super.onCreate(savedInstanceState);
        eventLinkEdt = view.findViewById(R.id.linkPaid);
        eventLinkEdt.setVisibility(View.INVISIBLE);

        imageView = view.findViewById(R.id.cover_image);
        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // initializing our edittext and buttons
        eventNameEdt = view.findViewById(R.id.idEdtEventName);
        eventDescriptionEdt = view.findViewById(R.id.idEdtEventDescription);
        eventLocationEdt = view.findViewById(R.id.idEdtEventLocation);
        eventCapacityEdt = view.findViewById(R.id.idEdtEventCapacity);
        eventDurationEdt = view.findViewById(R.id.idEdtEventDuration);
        eventDateEdt = view.findViewById(R.id.idEdtEventDate);
        submitEventBtn = view.findViewById(R.id.idBtnSubmitEvent);
        captureCoverBtn = view.findViewById(R.id.button_capture);
        CheckBox paidEvent = (CheckBox) view.findViewById(R.id.checkBox);

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
                String creator = mAuth.getCurrentUser().getUid().toString();

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
                        addDataToFirestore(eventName, eventDescription, eventDuration, eventDate, eventLocation, eventCapacity,imageUrl, eventType, eventLink, creator);
                    }
                }
            }
        });

        return view;
    }


    private void addDataToFirestore(String eventName, String eventDescription, String eventDuration, String eventDate, String eventLocation, String eventCapacity, String imageUrl, String eventType, String eventLink, String creator ) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbEvents = db.collection("Events");
        CollectionReference dbFreeEvents = db.collection("FreeEvents");
        CollectionReference dbPaidEvents = db.collection("PaidEvents");

        // adding our data to our courses object class.
        Event events = new Event(eventLocation, eventDuration, eventName, eventDate, eventCapacity, eventDescription, imageUrl, eventType, eventLink, creator);
        if(eventType.equals("Free")){
            dbFreeEvents.add(events);
        } else if(eventType.equals("Paid")){
            dbPaidEvents.add(events);
        }
        // below method is use to add data to Firebase Firestore.
        dbEvents.add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
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


//
//    public void captureImage(View view) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                    REQUEST_CAMERA_PERMISSION_CODE);
//            return;
//        }
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            try {
//                assert data != null;
//                Uri imageUri = data.getData();
//                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                imageView.setImageBitmap(imageBitmap);
//                // Save the full-size image to a file
//                saveImageToFile(imageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (resultCode == RESULT_CANCELED) {
//            // Handle the case where the user cancels taking a picture
//            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
//        } else {
//            // Handle other cases, such as if there's an error
//            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void saveImageToFile(Bitmap bitmap) {
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        imageUrl = null;
//        if (storageDir != null) {
//            String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
//            System.out.print(fileName);
//            File imageFile = new File(storageDir, fileName);
//            try {
//                FileOutputStream fos = new FileOutputStream(imageFile);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                fos.close();
//                imageUrl = imageFile.getAbsolutePath(); // Get the file URI
//                System.out.print(imageUrl);
//                Toast.makeText(this, "Image saved: " + imageUrl, Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}