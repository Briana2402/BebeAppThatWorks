package com.example.bebeappthatworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.bebeappthatworks.EventCreationActivity;


import com.example.bebeappthatworks.databinding.OrganiserLayoutBinding;

public class OrganiserActivity extends AppCompatActivity {

    OrganiserLayoutBinding binding;
    EventCreationActivity eventCreationActivity;
    //private Button LogOutBtn;

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
                replaceFragment(new OrganiserEventFragment());
            } else if (id == R.id.MyEvents) {
                replaceFragment(new OneEventFragment());
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
}