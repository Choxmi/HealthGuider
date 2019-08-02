package com.ashan.healthguider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuestionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater li = LayoutInflater.from(this);
        final View viewB = li.inflate(R.layout.attribute_layout, null);
        setContentView(viewB);
        Button btn = (Button) viewB.findViewById(R.id.quesSubBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int min[] = {0,100,150,0,0,150,0,0,0,0,0};
                int max[] = {5,200,300,1,1,180,1,5,3,3,3};
                int ids[] = {R.id.cp,R.id.trestbps,R.id.chol,R.id.fbs,R.id.restecg,R.id.thalach,R.id.exang,R.id.oldpeak,R.id.slope,R.id.ca,R.id.thal};
                String vals[] = new String[ids.length];
                int true_count = 0;
                for(int i=0; i < ids.length; i++){
                    Log.e("ID",""+ids[i]);
                    int val = Integer.valueOf(((EditText)viewB.findViewById(ids[i])).getText().toString());
                    if(val < min[i] || val > max[i]){

                    } else {
                        true_count++;
                    }
                }
                Intent intent = new Intent(QuestionActivity.this, StepSeven.class);
                intent.putExtra("res",true_count);
                intent.putExtra("dis",getIntent().getStringExtra("dis"));
                startActivity(intent);
            }
        });
    }
}
