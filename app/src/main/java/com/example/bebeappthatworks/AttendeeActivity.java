package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bebeappthatworks.databinding.AttendeeLayoutBinding;


/**
 * Activity containing the fragments and the functionality of the attendee account.
 */
public class AttendeeActivity extends AppCompatActivity {

    AttendeeLayoutBinding binding;

    /**
     * onCreate method.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Initialising the binding functionality
        binding = AttendeeLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting the first fragment to be displayed
        replaceFragment(new EventsFragment());


        //Binding the fragments with the nav bar and the activity
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Events) {
                replaceFragment(new EventsFragment());
            } else if (id == R.id.MyEvents) {
                replaceFragment(new MyEventsFragmentAttendee());
            } else if (id == R.id.Notifications){
                replaceFragment(new NotificationsFragment());
            } else if (id == R.id.Profile) {
                replaceFragment(new ProfileAttendeeFragment());
            }

            return true;
        });


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

    /**
     * Function to open GPS and maps.
     *
     * @param v View
     * @pre Permission has been given for GPS.
     * @post Map is opened.
     */
    public void openMaps(View v) {
        Intent intent = new Intent(this, com.example.bebeappthatworks.Map.class);
        startActivity(intent);
    }

    /**
     * Function to open GPS settings.
     *
     * @param v View
     */
    public void openGPSsettings(View v) {
        Intent intent = new Intent(this, GPS_settings.class);
        startActivity(intent);
    }


}