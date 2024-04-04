package com.example.bebeappthatworks;
import android.os.Bundle;
import com.example.bebeappthatworks.databinding.GuestLayoutBinding;

/**
 * A fragment representing a list of Items.
 */
public class GuestActivity extends OrganiserActivity {

    GuestLayoutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GuestLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}