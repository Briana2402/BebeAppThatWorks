package com.example.bebeappthatworks.ui.login;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.bebeappthatworks.Home;
import com.example.bebeappthatworks.AttendeeActivity;
import com.example.bebeappthatworks.MainActivity;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.ui.login.LoginViewModel;
import com.example.bebeappthatworks.ui.login.LoginViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.bebeappthatworks.forgotPassword.ForgotPasswordActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    FirebaseAuth mAuth;

    private boolean tryTest;
    //private LoginViewModel loginViewModel;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EditText editTextEmail, editTextPassword;
        Button backToMain;
        Button backToLogin;
        Button loginButton;
        super.onCreate(savedInstanceState);
        TextView forgotPassword;
        db = FirebaseFirestore.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        backToMain = (Button) findViewById(R.id.backToMain); //back button
        loginButton = (Button) findViewById(R.id.Loggingin);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                //checks if email and password are empty
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Authentication successful",
                                            Toast.LENGTH_SHORT).show();


                                    tryTest = false;
                                    db.collection("Attendees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if(!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                for(DocumentSnapshot d : list) {
                                                    //Log.i(d.getId(),mAuth.getCurrentUser().getUid());
                                                    if(d.getId().toString() ==  mAuth.getCurrentUser().getUid().toString()){
                                                        Intent intent = new Intent(LoginActivity.this, AttendeeActivity.class);
                                                        startActivity(intent);
                                                        Log.i("attendee", "yes");
                                                    }
                                                }
                                            }
                                        }
                                    });

                                        db.collection("Organisers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if(!queryDocumentSnapshots.isEmpty()) {
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for(DocumentSnapshot d : list) {
                                                        if(d.getId().toString() ==  mAuth.getCurrentUser().getUid().toString()){
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            Log.i("organizer", "yes");
                                                        }
                                                    }
                                                }
                                            }
                                        });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, Home.class);
//                startActivity(intent);
//            }
//        });




    }
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
}