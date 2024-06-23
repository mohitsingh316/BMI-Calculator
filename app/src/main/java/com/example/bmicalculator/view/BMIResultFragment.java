package com.example.bmicalculator.view;



import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bmicalculator.R;

public class BMIResultFragment extends Fragment {

    TextView mbmidisplay, mbmicategory, mgender;
    Button mgotomain;
    ImageView mimageview;
    String mbmi;
    String category;
    float intbmi;

    String height;
    String weight;

    float intheight, intweight;

    RelativeLayout mbackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_m_i_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve arguments passed to the fragment
        Bundle args = getArguments();
        if (args == null) {
            Toast.makeText(getContext(), "No data received", Toast.LENGTH_SHORT).show();
            return;
        }

        mbmidisplay = view.findViewById(R.id.bmidisplay);
        mbmicategory = view.findViewById(R.id.bmicategorydispaly);
        mgotomain = view.findViewById(R.id.gotomain);
        mimageview = view.findViewById(R.id.imageview);
        mgender = view.findViewById(R.id.genderdisplay);
        mbackground = view.findViewById(R.id.contentlayout);

        height = args.getString("height");
        weight = args.getString("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        intheight = intheight / 100;
        intbmi = intweight / (intheight * intheight);

        mbmi = Float.toString(intbmi);

        if (intbmi < 16) {
            mbmicategory.setText("Severe Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.crosss);
        } else if (intbmi < 16.9 && intbmi > 16) {
            mbmicategory.setText("Moderate Thinness");
            mbackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.halfwarn));
            mimageview.setImageResource(R.drawable.warning);
        } else if (intbmi < 18.4 && intbmi > 17) {
            mbmicategory.setText("Mild Thinness");
            mbackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.halfwarn));
            mimageview.setImageResource(R.drawable.warning);
        } else if (intbmi < 24.9 && intbmi > 18.5) {
            mbmicategory.setText("Normal");
            mimageview.setImageResource(R.drawable.ok);
        } else if (intbmi < 29.9 && intbmi > 25) {
            mbmicategory.setText("Overweight");
            mbackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.halfwarn));
            mimageview.setImageResource(R.drawable.warning);
        } else if (intbmi < 34.9 && intbmi > 30) {
            mbmicategory.setText("Obese Class I");
            mbackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.halfwarn));
            mimageview.setImageResource(R.drawable.warning);
        } else {
            mbmicategory.setText("Obese Class II");
            mbackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.warn));
            mimageview.setImageResource(R.drawable.crosss);
        }

        mgender.setText(args.getString("gender"));
        mbmidisplay.setText(mbmi);

        mgotomain.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_BMIResultFragment_to_BMIFragment);
        });
    }
}
