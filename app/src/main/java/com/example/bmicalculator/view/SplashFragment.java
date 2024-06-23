package com.example.bmicalculator.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bmicalculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment {

    private NavController navController;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize NavController
        navController = Navigation.findNavController(view);



        // Delay to simulate a splash screen
        new Handler().postDelayed(() -> {
            // Check if user is already logged in
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                // User is logged in, navigate to BMIFragment
                navController.navigate(R.id.action_splashFragment_to_BMIFragment);
            } else {
                // User is not logged in, navigate to SignInFragment
                navController.navigate(R.id.action_splashFragment_to_signInFragment);
            }
        }, 4000); // 4 seconds delay
    }
}
