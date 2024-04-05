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


public class AttendeeActivity extends AppCompatActivity {

    AttendeeLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Initialising the binding functionality
        binding = AttendeeLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new EventsFragment());


        //Binding the fragments with the nav bar and the activity
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Events) {
                replaceFragment(new EventsFragment());
            } else if (id == R.id.MyEvents) {
                replaceFragment(new MyEventsAttendee());
            } else if (id == R.id.Notifications){
                replaceFragment(new NotificationsFragment());
            } else if (id == R.id.Profile) {
                replaceFragment(new ProfileAttendeeFragment());
            }

            return true;
        });


    }


    //Method for replacing the fragments when a new button is pressed on the nav bar
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
        fragmentTransaction.commit();

    }

    public void openMaps(View v) {
        Intent intent = new Intent(this, com.example.bebeappthatworks.Map.class);
        startActivity(intent);
    }

}