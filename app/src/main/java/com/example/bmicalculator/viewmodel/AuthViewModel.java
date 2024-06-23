package com.example.bmicalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bmicalculator.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        repository = new AuthRepository(application);
        currentUser = repository.getCurrentUser();
        mFirebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }

    public void signUp(String email, String pass){
        repository.signUp(email, pass);
    }

    public void signIn(String email, String pass){
        repository.signIn(email, pass);
    }



    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}
