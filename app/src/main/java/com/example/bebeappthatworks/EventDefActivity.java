package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bebeappthatworks.ui.login.LoginActivity;

public class EventDefActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_def);

        Button locationButton;
        Button registerButton;

        locationButton = (Button) findViewById(R.id.openMapsBtn); //open Maps Button
        registerButton = (Button) findViewById(R.id.attendEventBtn); //Button to register to current event
//        locationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EventDefActivity.this, Map.class);
//                startActivity(intent);
//            }
//        });


//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }

    public void openMaps(View v) {
        Intent intent = new Intent( this, Map.class);
        startActivity(intent);
    }

}
