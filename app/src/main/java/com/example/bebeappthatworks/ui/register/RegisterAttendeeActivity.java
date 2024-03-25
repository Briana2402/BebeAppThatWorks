package com.example.bebeappthatworks.ui.register;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bebeappthatworks.MainActivity;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterAttendeeActivity extends AppCompatActivity {

    private boolean isPasswordValid(String password) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        int numberOfNumbers = 0;
        boolean digits;
        digits=false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                numberOfNumbers++;
                if (numberOfNumbers >= 2) {
                    digits = true;
                }
            }
        }

        return password != null && password.trim().length() > 5 && specailCharPatten.matcher(password).find() && UpperCasePatten.matcher(password).find() && lowerCasePatten.matcher(password).find() && digits;
    }

    // A placeholder username validation check
    private boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    //private RegisterViewModel registerViewModel;

//    @Override
//    public void onStart() {
//        super.onStart();
//        //check if user if already logged in, if yes, open main activity
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EditText editTextEmail, editTextPassword;
        Button backButton;
        Button registerButton;
        FirebaseAuth mAuth;
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();


        backButton = (Button) findViewById(R.id.backToMain); //back button
        mAuth = FirebaseAuth.getInstance();
        registerButton =(Button) findViewById(R.id.btn_register);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                CollectionReference dbAttendees = db.collection("Attendees");
                //checks if email and password are empty
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterAttendeeActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterAttendeeActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isPasswordValid(password)){
                    Toast.makeText(RegisterActivity.this, "Password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmailValid(email)){
                    Toast.makeText(RegisterActivity.this, "Email invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                //creates a user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //if user is added to auth db
                                    //FirebaseUser user = mAuth.getUid();
                                    User user = new User(email, "attendee");
                                    //adds user to Firestore with he same uid
                                    dbAttendees.document(mAuth.getCurrentUser().getUid()).set(user);
                                    Toast.makeText(RegisterAttendeeActivity.this, "Account created",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterAttendeeActivity.this, "Account creation failed.",
                                           Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAttendeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}