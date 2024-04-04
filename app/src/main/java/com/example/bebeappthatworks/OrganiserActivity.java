package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.bebeappthatworks.databinding.OrganiserLayoutBinding;

public class OrganiserActivity extends AppCompatActivity {

    OrganiserLayoutBinding binding;
    //private Button LogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OrganiserLayoutBinding.inflate(getLayoutInflater());
        //EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new EventsFragmentOrganizer());

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