package com.example.bmicalculator.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bmicalculator.model.BMIData;
import com.example.bmicalculator.repository.BMIRepository;

import java.util.List;

public class BMIViewModel extends AndroidViewModel {
    private final BMIRepository repository;
    private final LiveData<List<BMIData>> lastSevenWeightData; // LiveData for last 7 weight entries

    public BMIViewModel(Application application) {
        super(application);
        repository = new BMIRepository(application);
        lastSevenWeightData = repository.getLastSevenWeightData();
    }

    public LiveData<List<BMIData>> getLastSevenWeightData() {
        return lastSevenWeightData;
    }

    public void calculateBMI(int weight, int heightFt, int heightIn) {
        repository.calculateBMI(weight, heightFt, heightIn);
    }

    public void insertBMIData(BMIData bmiData) {
        repository.insertBMIData(bmiData);
    }
}
