package com.ashan.healthguider;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ItemsFetcher extends AsyncTask<String,Integer,String> {

    public interface DataResponse {
        void itemReceived(String output, String code) throws JSONException;
    }
    public DataResponse delegate = null;
    private URL getUrl;
    String req_code = null;
    public ItemsFetcher(DataResponse delegate, String main_symp, String sub_symp){
        this.delegate = delegate;
        req_code = sub_symp;
        String url = "https://healthguider.000webhostapp.com/api/DataOps.php?main="+main_symp+"&item="+sub_symp;

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
                responseString = "FAILED";
            }
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            delegate.itemReceived(result,req_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
