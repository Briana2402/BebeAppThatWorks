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
import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;


public class RegisterAttendeeActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
     * Function to check if an inputed password is valid
     * In order for a password to be valid it should
     * contain at least a special character, an upper
     * cased letter, a lower case letter and 2 numbers.
     *
     * @param password - inputed password by the user that
     * needs to be checked
     */
    private boolean isPasswordValid(String password) {

        // Declare variable in order to check for the special character.
        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);

        // Declare variable in order to check for the upper case character.
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");

        // Declare variable in order to check for the lower case character.
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");

        // Declare variable in order to check for the number of digits.
        int numberOfNumbers = 0;

        // Variable representing if the password has enough digits.
        boolean digits;
        digits=false;

        // Loop to check for the number of digits.
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                numberOfNumbers++;
                if (numberOfNumbers >= 2) {
                    digits = true;
                }
            }
        }

        // Check if the inputed password passes the requirements.
        return password != null && password.trim().length() > 5 &&
                specialCharPatten.matcher(password).find() &&
                UpperCasePatten.matcher(password).find() &&
                lowerCasePatten.matcher(password).find() &&
                digits;
    }

    /*
     * Function to check if an inputed username is valid
     * In order for an username to be valid it should
     * not be null and it should contain  the '@'
     * character.
     *
     * @param username - inputed email by the user that
     * needs to be checked
     */
    private boolean isEmailValid(String username) {

        // Checking if the email field is not empty
        if (username == null) {
            return false;
        }

        // Checking if the email contains '@' character
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    /*
     * Called when the activity is starting. Method for initialization
     * most of the necessary variables and methods.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Declaring variables from the XML file.
        EditText editTextEmail, editTextPassword, editTextCP;
        Button backButton;
        Button registerButton;

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register_attendee);



        backButton = (Button) findViewById(R.id.backToMain); //back button

        registerButton =(Button) findViewById(R.id.btn_register);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextCP = findViewById(R.id.passwordConfirmAttendee);

        /*
         * Used for setting up the register button and also implementing its functionality.
         */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, confPass;

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                confPass = editTextCP.getText().toString();


                //checks if email, password are empty and if password and confirm password are equal
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterAttendeeActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterAttendeeActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confPass)) {
                    Toast.makeText(RegisterAttendeeActivity.this, "Confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isPasswordValid(password)){
                    Toast.makeText(RegisterAttendeeActivity.this, "Password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmailValid(email)){
                    Toast.makeText(RegisterAttendeeActivity.this, "Email invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confPass)){
                    Toast.makeText(RegisterAttendeeActivity.this, "Confirm password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                //creates a user
                createAttendee(email, password);

            }
        });

        /*
         * If the user presses on the Back Button they will be taken back to the Welcome Page.
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAttendeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * Method to create an account and add it to Auth in Firebase.
     */
    private void createAttendee(String email, String password) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if user is added to auth db
                            //FirebaseUser user = mAuth.getUid();
                            User user = new User(email, "attendee", null, null);
                            //adds user to Firestore with he same uid
                            CollectionReference dbAttendees = db.collection("Attendees");
                            dbAttendees.document(mAuth.getCurrentUser().getUid()).set(user);
                            //display a message if the account creation worked
                            Toast.makeText(RegisterAttendeeActivity.this, "Account created",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(RegisterAttendeeActivity.this, LoginActivity.class);
                            startActivity(i);

                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(RegisterAttendeeActivity.this, LoginActivity.class);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterAttendeeActivity.this, "Account creation failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}