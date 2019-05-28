package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class StepSeven extends AppCompatActivity {
    Node result;
    TextView disease;
    PieChart pieChart ;
    ArrayList<PieEntry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.four_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepSeven.this, StepEight.class);
                intent.putExtra("res",result.symptom_id);
                startActivity(intent);
            }
        });
        result = (Node) getIntent().getSerializableExtra("res");
        disease = (TextView)findViewById(R.id.disease_name_txt);
        disease.setText(result.symptom_id);

        pieChart = (PieChart) findViewById(R.id.chart);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "Diseases");

        pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(3000);

    }

    public void AddValuesToPIEENTRY(){

        for(int i = 0; i < result.data.size(); i++){
            entries.add(new PieEntry(100/result.data.size(), 300));
        }

    }

    public void AddValuesToPieEntryLabels(){

        for(int i = 0; i < result.data.size(); i++){
            PieEntryLabels.add(result.symptom_id);
        }

    }
}
