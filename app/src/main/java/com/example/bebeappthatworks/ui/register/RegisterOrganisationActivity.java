package com.example.bebeappthatworks.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bebeappthatworks.MainActivity;
import com.example.bebeappthatworks.OrganiserActivity;
import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterOrganisationActivity extends AppCompatActivity {


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

    private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EditText editTextEmail, editTextPassword, editTextCP;
        Button backButton;
        Button registerButton;
        FirebaseAuth mAuth;
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_register_organisation);
        db = FirebaseFirestore.getInstance();


        backButton = (Button) findViewById(R.id.backToMain); //back button
        mAuth = FirebaseAuth.getInstance();
        registerButton =(Button) findViewById(R.id.btn_register);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextCP = findViewById(R.id.passwordConfirmOrganisation);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String email, password, confPass;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                confPass = editTextCP.getText().toString();

                CollectionReference dbOrg = db.collection("Organisers");



                //checks if email and password are empty
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterOrganisationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterOrganisationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(confPass)) {
                    Toast.makeText(RegisterOrganisationActivity.this, "Confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isPasswordValid(password)){
                    Toast.makeText(RegisterOrganisationActivity.this, "Password invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmailValid(email)){
                    Toast.makeText(RegisterOrganisationActivity.this, "Email invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confPass)){
                    Toast.makeText(RegisterOrganisationActivity.this, "Confirm password invalid", Toast.LENGTH_SHORT).show();
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
                                    User user = new User(email, "organiser");
                                    //adds user to Firestore with he same uid
                                    dbOrg.document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).set(user);
                                    Toast.makeText(RegisterOrganisationActivity.this, "Organisation account created",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterOrganisationActivity.this, "Account creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                 */

                Intent intent = new Intent(RegisterOrganisationActivity.this, OrganiserActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOrganisationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}