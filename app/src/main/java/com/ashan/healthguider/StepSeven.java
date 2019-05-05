package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;

public class StepSeven extends AppCompatActivity {
    Node result;
    TextView disease;
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
    }
}
