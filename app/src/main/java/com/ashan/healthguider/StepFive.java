package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepFive extends AppCompatActivity implements DataFetcher.DataResponse {

    LinearLayout scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        scroll = (LinearLayout) findViewById(R.id.symptom_linear);
        DataFetcher fetcher = new DataFetcher(StepFive.this,"mainsymptom", "1",null,null);
        fetcher.execute();
    }

    @Override
    public void processFinish(String output) throws JSONException {
        Log.e("OUT",output);
//        Toast.makeText(StepFive.this,"OUT : "+output,Toast.LENGTH_LONG).show();
        JSONArray jsonArray = new JSONArray(output);
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObj = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
            View child = getLayoutInflater().inflate(R.layout.item_symptoms, null);
            final View nextChild = ((ViewGroup)child).getChildAt(0);
            ((TextView)nextChild).setText(jsonObj.getString("symptom"));
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StepFive.this, StepSix.class);
                    try {
                        intent.putExtra("symptom",(jsonObj.getString("symp_id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
            scroll.addView(child);
        }
    }
}
