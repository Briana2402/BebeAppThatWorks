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
import android.app.Activity;

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
import android.app.FragmentManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.forgotPassword.ForgotPasswordActivity;
import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;

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
    private Button captureImageButton;
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
    private Uri imageUri;

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

//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.camera_fragment, use_camera.class, null)
//                    .commit();
//        }
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
        CheckBox paidEvent = (CheckBox) view.findViewById(R.id.checkBox);
        captureImageButton = view.findViewById(R.id.event_cover);

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
                        Log.i("imageUrl", imageUrl);
                        addDataToFirestore(eventName, eventDescription, eventDuration, eventDate, eventLocation, eventCapacity,imageUrl, eventType, eventLink, creator);
                    }
                }
            }
        });

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your captureImage method or perform your action here
                captureImage(v);
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

    public void captureImage(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

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
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle upload failure
                Toast.makeText(getContext(), "Image upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL after successful upload
                imageUrl = storageRef.getDownloadUrl().toString();
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri downloadUrl)
                    {
                        Log.i("imageUrl", imageUrl);
                    }
                });
            }
        });
    }
//    private String getImageUrl(Bitmap imageBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
//        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(),
//                imageBitmap, fileName,null);
//        Uri uri = Uri.parse(path);
//        return uri.toString();
//    }
}