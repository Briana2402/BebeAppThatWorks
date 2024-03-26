package com.example.bebeappthatworks;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bebeappthatworks.databinding.AttendeeLayoutBinding;
import com.example.bebeappthatworks.databinding.OrganiserLayoutBinding;

public class OrganiserActivity extends AppCompatActivity {

    OrganiserLayoutBinding binding;
    private Button LogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OrganiserLayoutBinding.inflate(getLayoutInflater());
        //EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new EventsFragment());

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Events) {
                replaceFragment(new EventsFragment());
            } else if (id == R.id.MyEvents) {
                replaceFragment(new MyEventsFragment());
            } else if (id == R.id.Notifications){
                replaceFragment(new NotificationsFragment());
            } else if (id == R.id.Profile) {
                replaceFragment(new ProfileAttendeeFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
        fragmentTransaction.commit();

    }
}