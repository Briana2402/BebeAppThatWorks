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


        binding = AttendeeLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EventsFragment fragment = new EventsFragment();
        replaceFragment(fragment);


        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Events) {
                replaceFragment(fragment);
            } else if (id == R.id.MyEvents) {
                replaceFragment(new OneEventFragment());
            } else if (id == R.id.Notifications){
                replaceFragment(new NotificationsFragment());
            } else if (id == R.id.Profile) {
                replaceFragment(new ProfileAttendeeFragment());
            }

            return true;
        });


    }

    public void buttonPlease(View v) {
        Intent intent = new Intent(AttendeeActivity.this, OneEventActivity.class);
        startActivity(intent);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_host_fragment_content_main, fragment);
        fragmentTransaction.commit();

    }

}