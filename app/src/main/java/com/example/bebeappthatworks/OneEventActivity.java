package com.example.bebeappthatworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.bebeappthatworks.databinding.OneEventLayoutBinding;
import com.example.bebeappthatworks.ui.eventCreation.Event;
import com.example.bebeappthatworks.MyItemRecyclerViewAdapter;
import com.example.bebeappthatworks.EventsFragment;

import java.util.ArrayList;
import java.util.List;

public class OneEventActivity extends AppCompatActivity {

    OneEventLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.one_event_layout); // Set the layout file for this activity

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding = OneEventLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OneEventFragment fragment = new OneEventFragment();
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_one_event_fragment, fragment);
        fragmentTransaction.commit();
    }

}
