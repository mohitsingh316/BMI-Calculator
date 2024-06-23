package com.example.bmicalculator.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmi_data")
public class BMIData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private float bmi;
    private long timestamp;
    private int weight;

    public BMIData(float bmi, long timestamp, int weight) {
        this.bmi = bmi;
        this.timestamp = timestamp;
        this.weight = weight;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
