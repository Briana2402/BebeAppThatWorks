package com.example.bebeappthatworks.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bebeappthatworks.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * This class represents a Map activity that displays a Google Map.
 * It implements the OnMapReadyCallback interface to handle map initialization.
 */
public class Map extends AppCompatActivity implements OnMapReadyCallback {

    // GoogleMap object to interact with the map.
    private GoogleMap myMap;
    private static final int PERMISSION_FINE_LOCATION = 60;

    //Google API for location services.
    FusedLocationProviderClient fusedLocationProviderClient;

    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                            being shut down, this Bundle contains the data it most
     *                            recently supplied in onSaveInstanceState(Bundle).
     *                            Otherwise, it is null.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        Button get_location = findViewById(R.id.get_location);
        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGPS();
                Toast.makeText(Map.this, "Current Location Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method used to get the current location of the user.
     */
    public void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //user provided permission
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Put the values in the UI
                    addMarker(location);
                }
            });
        } else {
            //permission not granted yet
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_FINE_LOCATION);
            }
        }
    }

    /**
     * Method used to set a marker on a map of the wanted location
     * @param location - param used to transfer the coordinates
     *                 to the method
     */
    public void addMarker(Location location) {
        double latitude = Double.valueOf(location.getLatitude());
        double longitude = Double.valueOf(location.getLongitude());

        // Set a marker for current user location
        LatLng current_location = new LatLng(latitude, longitude);
        myMap.addMarker(new MarkerOptions().position(current_location).title("Current Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));
    }


    /**
     * Called when the map is ready to be used. This is where we can add markers, move the camera, etc.
     *
     * @param googleMap A GoogleMap object representing the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;
        updateGPS();

        // Set the initial location on the map and add a marker.
        LatLng eindhoven = new LatLng(51.435, 5.435);
        myMap.addMarker(new MarkerOptions().position(eindhoven).title("Eindhoven"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(eindhoven));
    }
}