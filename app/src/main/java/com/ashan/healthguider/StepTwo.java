package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StepTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.eight_button);

        final EditText txtName = (EditText)findViewById(R.id.two_name);
        final EditText txtAge = (EditText)findViewById(R.id.two_age);
        final EditText txtBlood = (EditText)findViewById(R.id.two_blood);
        final RadioGroup gender = (RadioGroup)findViewById(R.id.two_gender);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String basicJson = "{name:"+txtName.getText().toString()+",age:"+txtAge.getText().toString()+",blood:"+txtBlood.getText().toString()+",gender:"+((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString()+"}";
                Intent intent = new Intent(StepTwo.this, StepThree.class);
                intent.putExtra("Basic",basicJson);
                startActivity(intent);
            }
        });
    }
}
