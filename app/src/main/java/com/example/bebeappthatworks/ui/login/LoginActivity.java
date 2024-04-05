package com.example.bebeappthatworks.ui.login;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
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


public class LoginActivity extends AppCompatActivity {
    public FirebaseFirestore db;
    public FirebaseAuth mAuth;

    private boolean tryTest;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Variables neeeded for buttons and Firestore
        EditText editTextEmail, editTextPassword;
        Button backToMain;
        Button loginButton;
        FirebaseAuth mAuth;
        super.onCreate(savedInstanceState);
        TextView forgotPassword;
        db = FirebaseFirestore.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        //Initialising the variables with the UI
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        backToMain = (Button) findViewById(R.id.backToMain); //back button
        loginButton = (Button) findViewById(R.id.Loggingin);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);




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


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    //method to check if the user trying to log in is an attendee
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
    //method to check if the user trying to log in is an organiser
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

    //method to log in the user using the credentials entered in the specified fields and checking
    //if they match an account existing in our database
    private void loginUser(String email,String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    tryTest = false;
                    if(checkForBelongingAttendee()) {
                        Intent intent = new Intent(LoginActivity.this, AttendeeActivity.class);
                        startActivity(intent);
                        Log.i("attendee", "yes");
                    } else if(checkForBelongingOrganisers()) {
                        Intent intent = new Intent(LoginActivity.this, OrganiserActivity.class);
                        startActivity(intent);
                        Log.i("organiser", "yes");
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