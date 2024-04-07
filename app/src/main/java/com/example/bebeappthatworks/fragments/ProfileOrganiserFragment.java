package com.example.bebeappthatworks.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bebeappthatworks.activities.MainActivity;
import com.example.bebeappthatworks.models.Organiser;
import com.example.bebeappthatworks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileOrganiserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileOrganiserFragment extends Fragment {

    //Declaring global variables
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView profile_pic;
    private String imageUrl;
    private Organiser organiser;
    private TextView username;
    private TextView description;
    private Button profilepicBtnOrganiser;
    private FirebaseFirestore db;
    private TextView email;
    private FirebaseAuth mAuth;
    View view;
    DocumentReference docRef;

    /**
     * Method in order to create constructor for
     * the ProfileAttendeeFragment.
     */

    public ProfileOrganiserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileOrganiserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileOrganiserFragment newInstance(String param1, String param2) {
        ProfileOrganiserFragment fragment = new ProfileOrganiserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the activity is starting. Method for initialization
     * most of the necessary variables and methods.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_organiser, container, false);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        username = view.findViewById(R.id.usernameOrganiser);
        email = view.findViewById(R.id.emailOrganiser);
        profile_pic = view.findViewById(R.id.imageViewOrganiserProfileImage);
        description = view.findViewById(R.id.descriptionOrganiser);
        docRef = db.collection("Organisers").document((mAuth.getCurrentUser().getUid()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            /**
             * Method used to setup the UI.
             * @param task - find the current task of the method
             */
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    organiser = document.toObject(Organiser.class);
                    //name.setText("my name is Jeff");
                    username.setText(organiser.getUsername());
                    email.setText(organiser.getEmail());
                    description.setText(organiser.getDescription());
                    try {
                        if (organiser.getProfileUrl() != null) {
                            setImage(organiser.getProfileUrl(), profile_pic, getContext());
                        }
                    } catch (NullPointerException e) {
                        // This catch block likely won't be reached as the null check happens before accessing attendee.getProfileUrl()
                        Log.i("Error", "Unexpected null pointer exception", e); // Log the error for debugging
                    }
                }
            }
        });

        Button myButton = view.findViewById(R.id.LOGOUTBUTTONORGANISER);
        profilepicBtnOrganiser = view.findViewById(R.id.addprofilepicOrganiser);

        /**
         * Method used to setup the functionality of myButton (Sign Out Button)
         * It sends the User back to Welcome Screen.
         */
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        Button openSettings = (Button) view.findViewById(R.id.openSettingsOrganiser);

        /**
         * Method used to setup the functionality of openSettings.
         * It sends the User to the Settings Page when the button is pressed.
         */
        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsOrganiser settingsOrganiser = new SettingsOrganiser();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, settingsOrganiser);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button deleteAccountBtn = view.findViewById(R.id.deleteAccountOrganiserBtn);

        /**
         * Method used to setup the functionality of deleteAccountBtn.
         * It uses a dialog to make sure that the User does want to
         * delete their account.
         */
        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete();
            }

        });

        /**
         * Method used to setup the functionality of profilepicBtnAttendee (Take picture Button).
         * It uses a dialog to ask the user if they want to take a picture or
         * import one from the library.
         */
        profilepicBtnOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    /**
     * Method used to setup the functionality changing the profile picture.
     * It uses a dialog to ask the user if they want to take a picture or
     * import one from the library.
     */
    private void showDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);
        Button cameraBtn = dialog.findViewById(R.id.buttonCamera);
        Button galleryBtn = dialog.findViewById(R.id.buttonGallery);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your captureImage method
                dialog.dismiss();
                captureImage(v);
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Method used to setup the functionality of deleting an user's account.
     * It uses a dialog to make sure that the User does want to
     * delete their account.
     */
    private void showDialogDelete() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pop_up_delete);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);
        Button cancelButton = dialog.findViewById(R.id.cancel_delete_button);
        Button confirmButton = dialog.findViewById(R.id.confirm_delete_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your captureImage method
                dialog.dismiss();
            }
        });
        /**
         * Method used to check for confirmation of user's intentions.
         * It uses a dialog to make sure that the User does want to
         * delete their account.
         */
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                db.collection("Organisers").document((mAuth.getCurrentUser().getUid()))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Account has been deleted.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error deleting the account.", Toast.LENGTH_SHORT).show();
                            }
                        });

                mAuth.getCurrentUser().delete();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        dialog.show();
    }

    /**
     * Method used to setup the functionality of changing the profile picture.
     */
    private void setImage(String imageUrl, ImageView imageView, Context context) {
        if (imageUrl==null){
            Log.i("null", "IMAGEURL IS NULL");
        } else {
            Log.i("imageUrl:", imageUrl);
        }
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

    /**
     * Method used to capture an image in order to change the profile picture
     * of an user.
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
     * Method used to explain the functionality of the camera feature +
     * catching errors.
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
                    profile_pic.setImageBitmap(imageBitmap);
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
     * Method used to upload the captured image to Firebase.
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
                        //attendee.setProfileUrl(imageUrl);
                        Map<String, Object> newData = new HashMap<>();
                        newData.put("profileUrl", imageUrl);
                        docRef.update(newData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("SUCCESS", "DocumentSnapshot successfully updated!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FAILURE", "Error updating document", e);
                            }
                        });
                        Log.i("imageUrl", imageUrl);
                        // Use the imageUrl to display or store in your app
                    }
                });
            }
        });
    }
}