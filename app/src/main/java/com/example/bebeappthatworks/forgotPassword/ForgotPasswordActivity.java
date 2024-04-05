package com.example.bebeappthatworks.forgotPassword;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Activity class for the Forgot Password functionality.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    //Field to input email to reset password for.
    private EditText enterEmailEdt;

    //String field to save email.
    private String email;

    /**
     * On create method.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialization of used buttons.
        Button backToLogin;
        Button sendCode;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        //Connect variable with vinstances from the XML file.
        backToLogin = (Button) findViewById(R.id.backToLoginPage);
        sendCode = (Button) findViewById(R.id.sendCode);
        enterEmailEdt = findViewById(R.id.code);

        //Go to login pagen when button backToLogin is pressed.
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initiate intent.
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Send reset code to reset password to inputted email when button sendCode is pressed.
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get inputted
                email = enterEmailEdt.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email);
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}