package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class StepEight extends AppCompatActivity {

    TextView disid, disname, docname, doccontact, dochospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.seven_button);
        disid = (TextView) findViewById(R.id.dis_id);
        disname = (TextView) findViewById(R.id.dis_name);
        docname = (TextView) findViewById(R.id.doc_name);
        doccontact = (TextView) findViewById(R.id.doc_contact);
        dochospital = (TextView) findViewById(R.id.doc_hospital);

        try {
            JSONArray doctors = new JSONArray(getIntent().getStringExtra("doc"));
            disid.setText(doctors.getJSONObject(0).getString("dise_id"));
            disname.setText(doctors.getJSONObject(0).getString("disease"));
            docname.setText(doctors.getJSONObject(0).getString("name"));
            doccontact.setText(doctors.getJSONObject(0).getString("contact"));
            dochospital.setText(doctors.getJSONObject(0).getString("hospitald"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepEight.this, StepFour.class);
                startActivity(intent);
            }
        });
    }
}
