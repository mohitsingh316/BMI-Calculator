package com.example.bmicalculator.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BMIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BMIData bmiData);

    @Query("SELECT * FROM bmi_data ORDER BY timestamp DESC LIMIT 7")
    LiveData<List<BMIData>> getLastSevenWeightData(); // This method retrieves the last seven weight entries
}
