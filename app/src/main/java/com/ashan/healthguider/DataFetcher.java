package com.ashan.healthguider;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DataFetcher extends AsyncTask<String,Integer,String> {

    public interface DataResponse {
        void processFinish(String output) throws JSONException;
    }
    public DataResponse delegate = null;
    private URL getUrl;
    public DataFetcher(DataResponse delegate, String cat, String sub_cat, String sub_val){
        this.delegate = delegate;
        String url = "https://healthguider.000webhostapp.com/api/DataOps.php";
//        if(cat!=null)
//            url = "https://satprojects.000webhostapp.com/healthguider/SymptomOperations.php?"+cat+"=1";
//        if(sub_cat!=null && sub_val!=null)
//            url = "https://satprojects.000webhostapp.com/healthguider/SymptomOperations.php?"+cat+"=1&"+sub_cat+"="+sub_val;
        try {
            this.getUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... request) {
        String responseString = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) this.getUrl.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                responseString = bufferedReader.readLine();
            } else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
