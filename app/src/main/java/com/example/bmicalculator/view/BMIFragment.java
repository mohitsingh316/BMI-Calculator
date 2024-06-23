package com.example.bmicalculator.view;

import static com.example.bmicalculator.model.BMICalculator.calculateBMI;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bmicalculator.R;
import com.example.bmicalculator.model.BMIData;
import com.example.bmicalculator.viewmodel.BMIViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BMIFragment extends Fragment {

    private TextView mcurrentheight;
    private TextView mcurrentweight, mcurrentage;
    private ImageView mincrementage, mdecrementage, mincrementweight, mdecrementweight;
    private SeekBar mseekbarforheight;
    private Button mcalculatebmi;

    private ConstraintLayout mmale, mfemale;

    private BMIViewModel bmiViewModel;
    private BarChart barChart;

    private int intweight = 55;
    private int intage = 22;
    private int currentprogress;
    private String mintprogress = "170";
    private String typerofuser = "0";
    private String weight2 = "55";
    private String age2 = "22";

    private List<BarEntry> entries = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b_m_i, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        bmiViewModel = new ViewModelProvider(this).get(BMIViewModel.class);

        mcurrentage = view.findViewById(R.id.currentage);
        mcurrentweight = view.findViewById(R.id.currentweight);
        mcurrentheight = view.findViewById(R.id.currentheight);
        mincrementage = view.findViewById(R.id.incrementage);
        mdecrementage = view.findViewById(R.id.decrementage);
        mincrementweight = view.findViewById(R.id.incremetweight); // Corrected ID
        mdecrementweight = view.findViewById(R.id.decrementweight); // Corrected ID
        mcalculatebmi = view.findViewById(R.id.calculatebmi);
        mseekbarforheight = view.findViewById(R.id.seekbarforheight);
        mmale = view.findViewById(R.id.male);
        mfemale = view.findViewById(R.id.female);
        barChart = view.findViewById(R.id.barChart);

        // Set up chart
        setupBarChart();

        mmale.setOnClickListener(v -> {
            mmale.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.malefemalefocus));
            mfemale.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.malefemalenotfocus));
            typerofuser = "Male";
        });

        mfemale.setOnClickListener(v -> {
            mfemale.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.malefemalefocus));
            mmale.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.malefemalenotfocus));
            typerofuser = "Female";
        });

        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentprogress = progress;
                mintprogress = String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mincrementweight.setOnClickListener(v -> {
            intweight++;
            weight2 = String.valueOf(intweight);
            mcurrentweight.setText(weight2);
        });

        mincrementage.setOnClickListener(v -> {
            intage++;
            age2 = String.valueOf(intage);
            mcurrentage.setText(age2);
        });

        mdecrementage.setOnClickListener(v -> {
            if (intage > 0) {
                intage--;
                age2 = String.valueOf(intage);
                mcurrentage.setText(age2);
            } else {
                Toast.makeText(requireContext(), "Age cannot be less than 0", Toast.LENGTH_SHORT).show();
            }
        });

        mdecrementweight.setOnClickListener(v -> {
            if (intweight > 0) {
                intweight--;
                weight2 = String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            } else {
                Toast.makeText(requireContext(), "Weight cannot be less than 0", Toast.LENGTH_SHORT).show();
            }
        });

        mcalculatebmi.setOnClickListener(v -> {
            if (TextUtils.equals(typerofuser, "0")) {
                Toast.makeText(requireContext(), "Select Your Gender First", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.equals(mintprogress, "0")) {
                Toast.makeText(requireContext(), "Select Your Height First", Toast.LENGTH_SHORT).show();
            } else if (intage <= 0) {
                Toast.makeText(requireContext(), "Age is Incorrect", Toast.LENGTH_SHORT).show();
            } else if (intweight <= 0) {
                Toast.makeText(requireContext(), "Weight Is Incorrect", Toast.LENGTH_SHORT).show();
            } else {
                // Calculate BMI
                int heightInInches = Integer.parseInt(mintprogress);
                int heightFt = heightInInches / 12;
                int heightIn = heightInInches % 12;

                bmiViewModel.calculateBMI(intweight, heightFt, heightIn);

                // Navigate to BMIResultFragment
                Bundle bundle = new Bundle();
                bundle.putString("height", mintprogress);
                bundle.putString("weight", weight2);
                Navigation.findNavController(v).navigate(R.id.action_BMIFragment_to_BMIResultFragment, bundle);
            }
        });

        // Observe LiveData for last seven weight entries
        // Update BarChart with new data
        bmiViewModel.getLastSevenWeightData().observe(getViewLifecycleOwner(), this::updateBarChart);
    }

    // Setup BarChart with initial data
    private void setupBarChart() {
        entries = new ArrayList<>();
        entries.add(new BarEntry(0, 25f)); // Example entry

        BarDataSet dataSet = new BarDataSet(entries, "Weight Results");
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.chartColor)); // Set color from resources

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new String[]{"Weight"}));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getAxisRight().setEnabled(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(200f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        barChart.invalidate(); // Refresh chart
    }

    // Method to update BarChart with new weight data list
    private void updateBarChart(List<BMIData> bmiDataList) {
        entries.clear();
        for (int i = 0; i < bmiDataList.size(); i++) {
            BMIData bmiData = bmiDataList.get(i);
            entries.add(new BarEntry(i, bmiData.getWeight()));
        }

        // Create a new BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Weight Results");
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.chartColor));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        // Create new BarData and set it to the BarChart
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f); // Set bar width
        barChart.setData(barData);

        // Refresh the chart
        barChart.invalidate();
    }
}
