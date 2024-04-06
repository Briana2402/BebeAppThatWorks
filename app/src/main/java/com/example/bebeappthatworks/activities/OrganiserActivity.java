package com.example.bebeappthatworks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.databinding.OrganiserLayoutBinding;
import com.example.bebeappthatworks.fragments.EventCreateFragment;
import com.example.bebeappthatworks.fragments.EventsFragmentOrganizer;
import com.example.bebeappthatworks.fragments.MyEventsFragment;
import com.example.bebeappthatworks.fragments.ProfileOrganiserFragment;

/**
 * Activity containing the fragments and the functionality of the organiser account.
 */
public class OrganiserActivity extends AppCompatActivity {

    OrganiserLayoutBinding binding;

    /**
     * OnCreate method.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OrganiserLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting initial fragment to be displayed.
        replaceFragment(new EventsFragmentOrganizer());

        //Binding the fragments with the nav bar and the activity
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Events) {
                replaceFragment(new EventsFragmentOrganizer());
            } else if (id == R.id.MyEvents) {
                replaceFragment(new MyEventsFragment());
            } else if (id == R.id.Notifications){
                replaceFragment(new EventCreateFragment());
            } else if (id == R.id.Profile) {
                replaceFragment(new ProfileOrganiserFragment());
            }

            return true;
        });

    }

    /**
     * Function to open GPS and maps.
     *
     * @param v View
     * @pre Permission has been given for GPS.
     * @post Map is opened.
     */
    public void openMaps(View v) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    /**
     * Method for replacing the fragments when a new button is pressed on the nav bar
     * @param fragment Fragment to replace the current one with.
     * @pre New valid fragment was selected.
     * @post Fragment is changed.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
        fragmentTransaction.commit();

    }
}