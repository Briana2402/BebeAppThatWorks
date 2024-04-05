package com.example.bebeappthatworks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class represents a Map activity that displays a Google Map.
 * It implements the OnMapReadyCallback interface to handle map initialization.
 */
public class Map extends AppCompatActivity implements OnMapReadyCallback {

    // GoogleMap object to interact with the map.
    private GoogleMap myMap;

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
    }

    /**
     * Called when the map is ready to be used. This is where we can add markers, move the camera, etc.
     *
     * @param googleMap A GoogleMap object representing the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // Set the initial location on the map and add a marker.
        LatLng eindhoven = new LatLng(51.435, 5.435);
        myMap.addMarker(new MarkerOptions().position(eindhoven).title("Eindhoven"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(eindhoven));
    }
}