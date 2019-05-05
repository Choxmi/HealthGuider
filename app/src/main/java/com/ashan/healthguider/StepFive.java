package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepFive extends AppCompatActivity implements DataFetcher.DataResponse {

    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        scroll = (ScrollView)findViewById(R.id.symptom_scroll);
        DataFetcher fetcher = new DataFetcher(StepFive.this,"symptoms",null,null);
        fetcher.execute();
    }

    @Override
    public void processFinish(String output) throws JSONException {
        Toast.makeText(StepFive.this,"OUT : "+output,Toast.LENGTH_LONG).show();
        JSONArray jsonArray = new JSONArray(output);
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObj = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
            View child = getLayoutInflater().inflate(R.layout.item_symptoms, null);
            final View nextChild = ((ViewGroup)child).getChildAt(0);
            ((TextView)nextChild).setText(jsonObj.getString("disease"));
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StepFive.this, StepSix.class);
                    intent.putExtra("symptom",((TextView)nextChild).getText().toString());
                    startActivity(intent);
                }
            });
            scroll.addView(child);
        }
    }
}
