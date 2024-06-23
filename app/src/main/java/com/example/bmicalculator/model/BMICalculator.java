package com.example.bmicalculator.model;

public class BMICalculator {

    public static double calculateBMI(int weight, int heightFt, int heightIn) {
        int totalIn = heightFt * 12 + heightIn;
        double totalCm = totalIn * 2.54;
        double totalM = totalCm / 100;
        return weight / (totalM * totalM);
    }

    public static String getBMICategory(double bmi) {
        if (bmi > 25) {
            return "You're Overweight!";
        } else if (bmi < 18.5) {
            return "You're Underweight!";
        } else {
            return "You're Healthy!";
        }
    }
}
