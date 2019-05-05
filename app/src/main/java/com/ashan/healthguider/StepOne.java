package com.ashan.healthguider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

public class StepOne extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sp.getString("uid",null) != null){
            Intent intent = new Intent(StepOne.this, StepFour.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_one);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.one_reg_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepOne.this, StepTwo.class);
                startActivity(intent);
            }
        });
    }
}
