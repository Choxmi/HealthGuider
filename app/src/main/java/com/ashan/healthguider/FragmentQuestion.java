package com.ashan.healthguider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FragmentQuestion extends Fragment {

    private String text;
    private String id;
    public void setText(String text, String id){
        this.text=text;
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.item_question, container, false);
        ((TextView)rootView.findViewById(R.id.ques_txt)).setText(getArguments().getString("position") + "  " + getArguments().getString("question") + "  " + getArguments().getString("qid"));
        ((RadioGroup)rootView.findViewById(R.id.ques_radio)).check(R.id.ques_no);
        ((RadioGroup)rootView.findViewById(R.id.ques_radio)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                int pos = Integer.valueOf(getArguments().getString("position"));
                if(id == R.id.ques_no){
                    Log.e("Res","No");
                    StepSix.results[pos] = false;
                } else if(id == R.id.ques_yes){
                    Log.e("Res","Yes");
                    StepSix.results[pos] = true;
                }
            }
        });
        return rootView;
    }
}
