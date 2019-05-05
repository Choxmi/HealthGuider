package com.ashan.healthguider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ((TextView)rootView.findViewById(R.id.ques_txt)).setText(text);
        return rootView;
    }
}
