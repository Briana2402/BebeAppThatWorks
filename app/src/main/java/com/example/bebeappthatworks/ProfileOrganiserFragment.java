package com.example.bebeappthatworks;

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

    //variables needed for the class
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView profile_pic;
    private String imageUrl;
    private Organiser organiser;
    private TextView username;
    private TextView description;
    private FirebaseFirestore db;
    private TextView email;
    View view;
    DocumentReference docRef;

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
    public static ProfileOrganiserFragment newInstance(String param1, String param2) {
        ProfileOrganiserFragment fragment = new ProfileOrganiserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate method.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflates the view
        view = inflater.inflate(R.layout.fragment_profile_organiser, container, false);
        //setting the Firebase instances
        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //setting the UI components to the fieldd
        username = view.findViewById(R.id.usernameOrganiser);
        email = view.findViewById(R.id.emailOrganiser);
        profile_pic = view.findViewById(R.id.imageViewOrganiserProfileImage);
        description = view.findViewById(R.id.descriptionOrganiser);
        //specifying the field from the databse for the profile logged in
        docRef = db.collection("Organisers").document((mAuth.getCurrentUser().getUid()));
        //updating the document with the inputted fields by the user
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    organiser = document.toObject(Organiser.class);
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
        Button profilepicBtnOrganiser = view.findViewById(R.id.addprofilepicOrganiser);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        Button openSettings = (Button) view.findViewById(R.id.openSettingsOrganiser);
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
        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete();
            }

        });

        profilepicBtnOrganiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

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