package com.example.bebeappthatworks;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bebeappthatworks.R;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationRequest;

public class SettingsOrganiser extends AppCompatActivity {

    Switch sw_locationupdates, sw_gps;

    public static final int DEFAULT_UP_INTERVAL = 30000;
    public static final int FAST_UP_INTERVAL = 5000;


    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address;

    //Google API for location services.
//    FusedLocationProviderClient fusedLocationProviderClient;
//
//    //LocationRequest represents a config file for all settings of
//    //FusedLocationProviderClient.
//    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_organiser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settingsOrganiserXml), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //giving each variable a value
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_gps = findViewById(R.id.sw_gps);
        sw_locationupdates = findViewById(R.id.sw_locationsupdates);

        //setting up the properties of Location Request
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(DEFAULT_UP_INTERVAL);
//        locationRequest.setFastestInterval(FAST_UP_INTERVAL);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

}