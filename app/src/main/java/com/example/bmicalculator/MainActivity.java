package com.example.bmicalculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        if (navHostFragment != null) {
            // Additional initialization if required
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle navigate up behavior if using Navigation component
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        if (navHostFragment != null) {
            return navHostFragment.getNavController().navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
}
