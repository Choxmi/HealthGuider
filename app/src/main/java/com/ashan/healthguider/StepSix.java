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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepSix extends AppCompatActivity implements DataFetcher.DataResponse, ItemsFetcher.DataResponse, MainListFetcher.DataResponse{
    private static int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    BinaryTree tree;
    public static boolean[] results;
    int counter, itm_count, full_count = 0;
    Node result_node;
    private ArrayList<String> symptom_list;
    AppCompatImageButton proceed;
    ArrayList<Tree_Item> dise_map;
    Map<String,ArrayList<String>> full_map;
    JSONArray filtered_array;
    ArrayList<String> base_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        full_map = new HashMap<>();
        dise_map = new ArrayList<>();
        symptom_list = new ArrayList<>();
        setContentView(R.layout.activity_three);
        proceed = (AppCompatImageButton)findViewById(R.id.three_button);
        tree = new BinaryTree();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Node result = calculate_results(tree.root);
                if(result == null){
                    try {
                        Log.e("RES", "Generating Result");
                        result = generate_result();
                        Log.e("RES", "OUT : "+result.data.get(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    result = new Node();
                }
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

        filtered_array = new JSONArray();

        int sampling_rate = (jsonArray.length())/10;
        for(int i =0; i < jsonArray.length(); i=i+sampling_rate){
            filtered_array.put(jsonArray.get(i));
        }
        NUM_PAGES = filtered_array.length();
        pagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(),filtered_array);
        mPager.setAdapter(pagerAdapter);
        Log.e("OutArr",filtered_array.toString());
        results = new boolean[filtered_array.length()];
        full_count = filtered_array.length();
        dise_map.clear();
        for(int i =0; i < filtered_array.length(); i++){
            ItemsFetcher itemsFetcher = new ItemsFetcher(StepSix.this,getIntent().getStringExtra("symptom"),filtered_array.getJSONObject(i).getString("symp_id"));
            itemsFetcher.execute();
        }

        //Create Binary Tree

//        String previous_disease = null;
//        ArrayList<ArrayList<String>> full_diseases = new ArrayList<>();
//        ArrayList<String> disease = null;
//        ArrayList<String> symptoms = new ArrayList<>();
//        Log.e("Check","Symptom INIT"+jsonArray.length());
//        for(int i =0; i < jsonArray.length(); i++){
//            Log.e("Check","Inside For1");
//            JSONObject obj = jsonArray.getJSONObject(i);
//            Log.e("Check","Inside For2");
//            if(!(obj.getString("dise_id")).equals(previous_disease)){
//                Log.e("Check","Inside For3");
//                if(previous_disease != null){
//                    full_diseases.add(disease);
//                }
//                disease = new ArrayList<>();
//                disease.add(obj.getString("symp_id"));
//                previous_disease = obj.getString("dise_id");
//            } else {
//                Log.e("Check","Inside For4");
//                disease.add(obj.getString("symp_id"));
//            }
//            if (!symptoms.contains(obj.getString("symp_id"))) {
//                Log.e("Check","Inside For5");
//                symptoms.add(obj.getString("symp_id"));
//                symptom_list.add(obj.getString("symp_id"));
//            }
//            Log.e("Check","Inside For6 :: "+i);
//        }
//
//        Log.e("FULL", Arrays.toString(full_diseases.toArray()));
//
//        tree = new BinaryTree();
//        tree.root = new Node(full_diseases,symptoms.get(0));
//        Node lroot = tree.root;
//        Node rroot = tree.root;
//        for (int j = 1; j < symptoms.size(); j++) {
//            ArrayList<ArrayList<String>> positive = new ArrayList<>();
//            ArrayList<ArrayList<String>> negative = new ArrayList<>();
//            for(int x = 0; x < full_diseases.size(); x++){
//                if(full_diseases.get(x).contains(symptoms.get(j))){
//                    positive.add(full_diseases.get(x));
//                } else {
//                    negative.add(full_diseases.get(x));
//                }
//            }
//            lroot.left = new Node(positive,symptoms.get(j));
//            lroot.right = new Node(negative,symptoms.get(j));
//            rroot.left = new Node(positive,symptoms.get(j));
//            rroot.right = new Node(negative,symptoms.get(j));
//        }
    }

    private void build_tree(){
        tree = new BinaryTree();
        tree.root = new Node(dise_map.get(0).diseases,dise_map.get(0).symp);
        Node lroot = tree.root;
        Node rroot = tree.root;
        for (int j = 1; j < dise_map.size(); j++) {
            ArrayList<String> positive = dise_map.get(j-1).diseases;
            ArrayList<String> positive_ = dise_map.get(j).diseases;
            ArrayList<String> negative = dise_map.get(j-1).diseases;
            ArrayList<String> negative_ = dise_map.get(j).diseases;

            positive.retainAll(positive_);
            negative.retainAll(negative_);

            lroot.left = new Node(positive,dise_map.get(j).symp);
            lroot.right = new Node(negative,dise_map.get(j).symp);
            rroot.left = new Node(positive,dise_map.get(j).symp);
            rroot.right = new Node(negative,dise_map.get(j).symp);
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

    @Override
    public void itemReceived(String output, String code) throws JSONException {
        Log.e("DISE",code);
        JSONArray single_symptom = new JSONArray(output);
        ArrayList<String> diseases = new ArrayList<>();
        for(int i = 0; i < single_symptom.length(); i++) {
            diseases.add(single_symptom.getJSONObject(i).getString("dise_id"));
        }
        Tree_Item itm = new Tree_Item(diseases, code);
        dise_map.add(itm);
        full_map.put(code,diseases);
        itm_count++;
        if(itm_count == full_count){
            Toast.makeText(StepSix.this,"All Items received",Toast.LENGTH_LONG).show();
            itm_count = 0;
            build_tree();
        }
    }

    @Override
    public void mainList(String output) throws JSONException {
        base_list = new ArrayList<>();
        JSONArray single_symptom = new JSONArray(output);
        for(int i = 0; i < single_symptom.length(); i++) {
            base_list.add(single_symptom.getJSONObject(i).getString("dise_id"));
        }
    }

    private Node generate_result() throws JSONException {
        List<ArrayList> truelist = new ArrayList<>();
        for(int i = 0; i < results.length; i++){
            if(results[i]){
                truelist.add(full_map.get(filtered_array.getJSONObject(i).getString("symp_id")));
            }
        }

        Collections.sort(truelist, new Comparator<ArrayList>(){
            public int compare(ArrayList a1, ArrayList a2) {
                return a2.size() - a1.size(); // assumes you want biggest to smallest
            }
        });

        ArrayList<String> templist = truelist.get(0);
        ArrayList<String> outlist = new ArrayList<>();
        for (int j = 1; j < truelist.size(); j++) {
            templist.retainAll(truelist.get(0));
            Log.e("TAG",Arrays.toString(templist.toArray()));
            if(templist!=null){
                outlist = templist;
            }
        }

        Node out = new Node(outlist, outlist.get(0));
        return out;
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

    class Tree_Item{
        ArrayList<String> diseases;
        String symp;
        private Tree_Item(ArrayList<String> dis, String symptom){
            diseases = dis;
            symp = symptom;
        }
    }
}
