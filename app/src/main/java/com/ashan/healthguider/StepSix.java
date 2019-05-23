package com.ashan.healthguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class StepSix extends AppCompatActivity implements DataFetcher.DataResponse{
    private static int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    BinaryTree tree;
    public static boolean[] results;
    int counter = 0;
    Node result_node;
    private ArrayList<String> symptom_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        symptom_list = new ArrayList<>();
        setContentView(R.layout.activity_three);
        AppCompatImageButton button = (AppCompatImageButton)findViewById(R.id.three_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Node result = calculate_results(tree.root);
                Log.e("RES",result.toString());
                Intent intent = new Intent(StepSix.this, StepSeven.class);
                intent.putExtra("res",result);
                startActivity(intent);
            }
        });
        Log.e("SIX","Create MPager");
        mPager = (ViewPager) findViewById(R.id.ques_pager);
        DataFetcher fetcher = new DataFetcher(StepSix.this,"questions", getIntent().getStringExtra("symptom"),null,null);
        fetcher.execute();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void processFinish(String output) throws JSONException {
        Log.e("PROCESS",output);
        JSONArray jsonArray = new JSONArray(output);
        NUM_PAGES = jsonArray.length();
        pagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(),jsonArray);
        mPager.setAdapter(pagerAdapter);
        Log.e("OutArr",jsonArray.toString());
        results = new boolean[jsonArray.length()];

        //Create Binary Tree

        String previous_disease = null;
        ArrayList<ArrayList<String>> full_diseases = new ArrayList<>();
        ArrayList<String> disease = null;
        ArrayList<String> symptoms = new ArrayList<>();
        for(int i =0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            if(!(obj.getString("dise_id")).equals(previous_disease)){
                if(previous_disease != null){
                    full_diseases.add(disease);
                }
                disease = new ArrayList<>();
                disease.add(obj.getString("symp_id"));
                previous_disease = obj.getString("dise_id");
            } else {
                disease.add(obj.getString("symp_id"));
            }
            if (!symptoms.contains(obj.getString("symp_id"))) {
                symptoms.add(obj.getString("symp_id"));
                symptom_list.add(obj.getString("symp_id"));
            }
        }

        Log.e("FULL", Arrays.toString(full_diseases.toArray()));

        tree = new BinaryTree();
        tree.root = new Node(full_diseases,symptoms.get(0));
        Node lroot = tree.root;
        Node rroot = tree.root;
        for (int j = 1; j < symptoms.size(); j++) {
            ArrayList<ArrayList<String>> positive = new ArrayList<>();
            ArrayList<ArrayList<String>> negative = new ArrayList<>();
            for(int x = 0; x < full_diseases.size(); x++){
                if(full_diseases.get(x).contains(symptoms.get(j))){
                    positive.add(full_diseases.get(x));
                } else {
                    negative.add(full_diseases.get(x));
                }
            }
            lroot.left = new Node(positive,symptoms.get(j));
            lroot.right = new Node(negative,symptoms.get(j));
            rroot.left = new Node(positive,symptoms.get(j));
            rroot.right = new Node(negative,symptoms.get(j));
        }
    }

    private Node calculate_results(Node node){
        if(counter >= results.length){
            counter = 0;
            return result_node;
        }

        if(node == null){
            counter = 0;
            return result_node;
        }
        result_node = node;
        //traverse tree
        Node tempNode;
        if(results[counter]){
            tempNode = result_node.left;
        } else {
            tempNode = result_node.right;
        }
        counter++;
        calculate_results(tempNode);

        return null;
    }

    private class QuestionPagerAdapter extends FragmentStatePagerAdapter {
        JSONArray questions;
        public QuestionPagerAdapter(FragmentManager fm, JSONArray arr) {
            super(fm);
            this.questions = arr;
        }

        @Override
        public Fragment getItem(int position) {
            FragmentQuestion ques = new FragmentQuestion();
            JSONObject obj = null;
            try {
                obj = new JSONObject(String.valueOf(questions.getJSONObject(position)));
                Bundle param = new Bundle();
                param.putString("question",obj.getString("symptom"));
                param.putString("position",""+(position + 1));
                param.putString("qid",obj.getString("symp_id"));
                ques.setArguments(param);
                ques.setText(obj.getString("symptom"),obj.getString("symp_id"));
                Log.e("SYMPTOM",obj.getString("symptom"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ques;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
