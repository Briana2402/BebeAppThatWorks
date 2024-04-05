package com.example.bebeappthatworks;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/*
 * Public class GPS_settings that is responsible for the implementation of the
 * GPS hardware of a mobile phone / tablet.
 */
public class GPS_settings extends AppCompatActivity {

    //Declaring the variables.
    private static final int PERMISSION_FINE_LOCATION = 60;
    Switch sw_locationupdates, sw_gps;

    public static final int DEFAULT_UP_INTERVAL = 30000;
    public static final int FAST_UP_INTERVAL = 5000;


    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address;

    //Google API for location services.
    FusedLocationProviderClient fusedLocationProviderClient;

    //LocationRequest represents a config file for all settings of
    //FusedLocationProviderClient.
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gps_settings);
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
        locationRequest = new LocationRequest();
        locationRequest.setInterval(DEFAULT_UP_INTERVAL);
        locationRequest.setFastestInterval(FAST_UP_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        /*
         * Choose if the GPS feature is turned on or off.
         */
        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()) {
                    //using GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                    updateGPS();
                }
                else {
                    tv_sensor.setText("GPS sensors are off");
                }
            }
        });

        updateGPS();
    }

    /*
     * Method for requesting access in order for the app to be allowed
     * to use the GPS hardware of the mobile phone / tablet +
     * remembering the answer of the user.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateGPS();
            } else {
                Toast.makeText(this, "In order to enable GPS, permission must be granted",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /*
     * Function that uses the fusedLocationProviderClient in order to get the
     * current location of the user.
     */
    private void updateGPS() {
        //get permisssions from the user to track Gps
        //get the current location
        //update the UI

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //user provided permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Put the values in the UI
                    updateUIValues(location);
                }
            });
        }
        else {
            //permission not granted yet
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_FINE_LOCATION);
            }
        }
    }

    //updates all the values for the UI
    private void updateUIValues(Location location) {
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
        tv_altitude.setText(String.valueOf(location.getAltitude()));
        tv_speed.setText(String.valueOf(location.getSpeed()));

        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        }
        catch (Exception e) {
            tv_address.setText("Unable to get street address");
        }

    }

}