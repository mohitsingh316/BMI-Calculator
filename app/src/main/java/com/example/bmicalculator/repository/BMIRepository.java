package com.example.bmicalculator.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bmicalculator.model.BMIData;
import com.example.bmicalculator.model.BMIDao;
import com.example.bmicalculator.model.BMIDatabase;

import java.util.List;

public class BMIRepository {
    private final BMIDao bmiDao;
    private final LiveData<List<BMIData>> lastSevenWeightData;

    public BMIRepository(Application application) {
        BMIDatabase db = BMIDatabase.getInstance(application);
        bmiDao = db.bmiDao();
        lastSevenWeightData = bmiDao.getLastSevenWeightData();
    }

    public LiveData<List<BMIData>> getLastSevenWeightData() {
        return lastSevenWeightData;
    }

    public void calculateBMI(int weight, int heightFt, int heightIn) {
        float heightInMeters = (heightFt * 12 + heightIn) * 0.0254f;
        float bmi = weight / (heightInMeters * heightInMeters);
        BMIData bmiData = new BMIData(bmi, System.currentTimeMillis(), weight);
        insertBMIData(bmiData);
    }

    public void insertBMIData(BMIData bmiData) {
        BMIDatabase.getDatabaseWriteExecutor().execute(() -> bmiDao.insert(bmiData));
    }
}
