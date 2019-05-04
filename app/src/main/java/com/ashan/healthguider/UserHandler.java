package com.ashan.healthguider;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UserHandler extends AsyncTask<String,Integer,String> {

    public interface AsyncResponse {
        void dataReceived(String output);
    }

    public UserHandler.AsyncResponse delegate = null;
    String basic, advanced = "";
    URL getUrl = null;

    public UserHandler(AsyncResponse context, String basics, String details){
        this.delegate = context;
        this.basic = basics;
        this.advanced = details;
        String url = "https://healthguider.000webhostapp.com/api/UserOps.php?adduser="+basics+"&details="+details;
        try {
            this.getUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseString = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) this.getUrl.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                responseString = bufferedReader.readLine();
            }
            else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.dataReceived(s);
    }
}
