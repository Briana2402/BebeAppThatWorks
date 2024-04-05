package com.example.bebeappthatworks;
import android.os.Bundle;
import com.example.bebeappthatworks.databinding.GuestLayoutBinding;

/**
 * A fragment representing the functionality of the guest account.
 * It extends functionality of OrganiserActivity.
 */
public class GuestActivity extends OrganiserActivity {

    GuestLayoutBinding binding;

    /**
     * onCreate method.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GuestLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}