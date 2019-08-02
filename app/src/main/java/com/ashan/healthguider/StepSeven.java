package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class StepSeven extends AppCompatActivity implements DataFetcher.DataResponse {
    Node result;
    TextView disease;
    PieChart pieChart ;
    ArrayList<PieEntry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData;
    String doc_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.four_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getIntExtra("res",0) > 5) {
                    Intent intent = new Intent(StepSeven.this, StepEight.class);
                    intent.putExtra("res", String.valueOf(getIntent().getIntExtra("res", 0)));
                    intent.putExtra("doc", doc_response);
                    startActivity(intent);
                } else {
                    Toast.makeText(StepSeven.this,"You don't need a doctor",Toast.LENGTH_LONG).show();
                }
            }
        });
//        result = (Node) getIntent().getSerializableExtra("res");
        disease = (TextView)findViewById(R.id.disease_name_txt);
        if(getIntent().getIntExtra("res",0) > 5) {
            disease.setText(getIntent().getStringExtra("dis"));
        } else {
            disease.setText("Normal");
        }
        Toast.makeText(StepSeven.this,""+getIntent().getIntExtra("res",0),Toast.LENGTH_LONG).show();
        DataFetcher fetcher = new DataFetcher(StepSeven.this,"doctors", String.valueOf(getIntent().getIntExtra("res",0)), null, null);
        fetcher.execute();
        pieChart = (PieChart) findViewById(R.id.chart);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY(getIntent().getIntExtra("res",0));

        AddValuesToPieEntryLabels(getIntent().getStringExtra("dis"));

        pieDataSet = new PieDataSet(entries, "Diseases");

        pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(3000);

    }

    public void AddValuesToPIEENTRY(int count){

        entries.add(new PieEntry(count, 300));
        entries.add(new PieEntry((11-count), 300));

    }

    public void AddValuesToPieEntryLabels(String disease){

        PieEntryLabels.add(disease);
        PieEntryLabels.add("Normal");

    }

    @Override
    public void processFinish(String output) throws JSONException {
        JSONArray doctors = new JSONArray(output);
//        disease.setText(doctors.getJSONObject(0).getString("disease"));
        doc_response = output;
    }
}
