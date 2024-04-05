package com.example.bebeappthatworks.ui.login;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bebeappthatworks.AttendeeActivity;
import com.example.bebeappthatworks.MainActivity;
import com.example.bebeappthatworks.OrganiserActivity;
import com.example.bebeappthatworks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.bebeappthatworks.forgotPassword.ForgotPasswordActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;


/**
 * Activty class for theLogging in functionality.
 */
public class LoginActivity extends AppCompatActivity {

    //Instance of Firebase to be instantiated.
    public FirebaseFirestore db;

    //Instance of Firebase Authentificator to be instantiated.
    public FirebaseAuth mAuth;

    //Boolean variable to check if the logged in user is an attendee or an organiser.
    private boolean tryTest;

    /**
     * onCreate function for the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Variables neeeded for buttons and Firestore
        EditText editTextEmail, editTextPassword;
        Button backToMain;
        Button loginButton;
        TextView forgotPassword;

        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        //Initialising the variables with the UI
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        backToMain = (Button) findViewById(R.id.backToMain); //back button
        loginButton = (Button) findViewById(R.id.Loggingin);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        //Inputting the neccessary fields.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                //get the text
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                //checks if email and password are empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(email, password);
            }
        });

        //Back to main paige button.
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Forgot password button.
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method takes logged in user's ID and checks if it's an attendee.
     *
     * @pre A user has successfully signed in.
     * @post The right type of user is identified.
     * @return boolean variable which is true if logged in user is Attendee and false otherwise.
     */
    public boolean checkForBelongingAttendee() {
        tryTest = false;
        db.collection("Attendees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list) {
                        if(d.getId() ==  mAuth.getCurrentUser().getUid()){
                            tryTest = true;
                        }
                    }
                }
            }
        });
        return tryTest;
    }

    /**
     * Method takes logged in user's ID and checks if it's an organiser.
     *
     * @pre A user has successfully signed in.
     * @post The right type of user is identified.
     * @return boolean variable which is true if logged in user is Organiser and false otherwise.
     */
    public boolean checkForBelongingOrganisers() {
        tryTest = false;
        db.collection("Organisers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list) {
                        if(d.getId() ==  mAuth.getCurrentUser().getUid()){
                            tryTest = true;
                        }
                    }
                }
            }
        });
        return tryTest;
    }

    /**
     * Method to login the user with given email and password.
     *
     * @param email inputted email
     * @param password inputted password
     * @pre There exists an account in the database with the given email and password.
     * @post User is logged in.
     */
    public void loginUser(String email, String password) {
        //User signs in.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the type of signed-in user's information
                    tryTest = false;
                    if(checkForBelongingAttendee()) {
                        Intent intent = new Intent(LoginActivity.this, AttendeeActivity.class);
                        startActivity(intent);
                    } else if(checkForBelongingOrganisers()) {
                        Intent intent = new Intent(LoginActivity.this, OrganiserActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}