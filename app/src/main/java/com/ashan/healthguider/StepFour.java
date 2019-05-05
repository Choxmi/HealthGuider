package com.ashan.healthguider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;

public class StepFour extends AppCompatActivity {
    SharedPreferences sp;
    TextView welcome_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sp.getString("uid",null) == null){
            Intent intent = new Intent(StepFour.this, StepOne.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_six);
        welcome_txt =(TextView)findViewById(R.id.prof_txt);
        welcome_txt.setText("Hi "+sp.getString("usr",null));
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.six_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepFour.this, StepFive.class);
                startActivity(intent);
            }
        });
    }
}
