package com.ashan.healthguider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepThree extends AppCompatActivity implements UserHandler.AsyncResponse, DataFetcher.DataResponse {

    String basics,known;
    LinearLayout scroll;
    String[] selection;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        scroll = findViewById(R.id.two_linear);
        basics = getIntent().getCharSequenceExtra("Basic").toString();

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.two_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Button","Clicked");
                for(int i=0;i<10;i++){
                    if(selection[i]!=null){
                        known+=("Item "+i+"|");
                    }
                }
                if(known!=null){
                    known = known.substring(0, known.length() - 1);
                }
                UserHandler fetcher = new UserHandler(StepThree.this,basics,known);
                fetcher.execute();
            }
        });

        DataFetcher fetcher = new DataFetcher(StepThree.this,"symptoms","1",null,null);
        fetcher.execute();
    }

    @Override
    public void processFinish(String output) throws JSONException {
        Log.e("OUT","OUTPUT : "+output);
        Toast.makeText(StepThree.this,"OUT : "+output,Toast.LENGTH_LONG).show();
        JSONArray jsonArray = new JSONArray(output);
        selection = new String[jsonArray.length()];
        for(int j=0;j<10;j++){
            selection[j] = null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            final int point = i;
            final JSONObject jsonObj = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
            View child = getLayoutInflater().inflate(R.layout.item_prev_diagnose, null);
            for(int index = 0; index<((ViewGroup)child).getChildCount(); ++index) {
                View nextChild = ((ViewGroup)child).getChildAt(index);
                if (nextChild instanceof TextView) {
                    ((TextView)nextChild).setText(jsonObj.getString("disease"));
                }

                if (nextChild instanceof ImageButton) {
                    final ImageButton btn = ((ImageButton)nextChild);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(selection[point] == null) {
                                btn.setImageResource(R.drawable.success);
                                try {
                                    selection[point] = jsonObj.getString("dise_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                btn.setImageResource(R.drawable.failed);
                                selection[point] = null;
                            }
                        }
                    });
                }
            }
            scroll.addView(child);
        }
    }

    @Override
    public void dataReceived(String output) {
        Log.e("OUT","DATA : "+output);
        spEditor = sp.edit();
        spEditor.putString("uid",output);
        JSONObject usr = null;
        try {
            usr = new JSONObject(basics);
            spEditor.putString("usr",usr.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        spEditor.apply();
        Intent intent = new Intent(StepThree.this, StepFour.class);
        startActivity(intent);
    }
}
