package com.example.bebeappthatworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.bebeappthatworks.databinding.OneEventLayoutBinding;

/**
 * The activity for One Event. It is useful when
 * wanting to show the events using the RecycleView as
 * it functionality is to create another fragment upon
 * creation.
 */
public class OneEventActivity extends AppCompatActivity {

    OneEventLayoutBinding binding;

    /**
     * Called when the activity is starting. Method for initialization
     * most of the necessary variables and methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.one_event_layout); // Set the layout file for this activity

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding = OneEventLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Replacing the current fragment with the new one,
        OneEventFragment fragment = new OneEventFragment();
        replaceFragment(fragment);
    }

    /**
     * Method used in order to move from one fragment to another
     * fragment within an activity.
     * @param fragment - the fragment to be replaced
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_one_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }

}
