package com.example.bmicalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bmicalculator.R;
import com.example.bmicalculator.viewmodel.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 1000;

    private AuthViewModel viewModel;
    private NavController navController;

    private EditText editEmail, editPass;
    private TextView signUpText;
    private Button signInBtn;
    private SignInButton googleSignInBtn;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(AuthViewModel.class);

        navController = Navigation.findNavController(view);
        editEmail = view.findViewById(R.id.usernameSignIn);
        editPass = view.findViewById(R.id.passwordSignIn);
        signUpText = view.findViewById(R.id.signUpText);
        signInBtn = view.findViewById(R.id.signInButton);
        googleSignInBtn = view.findViewById(R.id.google_btn);

        // Check if user is already authenticated
        if (viewModel.isUserLoggedIn()) {
            navController.navigate(R.id.action_signInFragment_to_BMIFragment);
        }

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if (!email.isEmpty() && !pass.isEmpty()) {
                    viewModel.signIn(email, pass);
                } else {
                    Toast.makeText(getContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        googleSignInBtn.setSize(SignInButton.SIZE_STANDARD);
        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        // Configure Google Sign-In
        configureGoogleSignIn();

        // Observe authentication state changes
        observeAuthenticationState();
    }

    private void configureGoogleSignIn() {
        // Configure Google Sign-In to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.app_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                // Google Sign-In was successful, authenticate with Firebase
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign-In failed
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getContext(), "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null))
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_signInFragment_to_BMIFragment);
                            // Optionally, clear EditTexts
                            editEmail.setText("");
                            editPass.setText("");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void observeAuthenticationState() {
        viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_signInFragment_to_BMIFragment);
                    // Optionally, clear EditTexts
                    editEmail.setText("");
                    editPass.setText("");
                } else {
                    // Handle sign-in failure (optional)
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
