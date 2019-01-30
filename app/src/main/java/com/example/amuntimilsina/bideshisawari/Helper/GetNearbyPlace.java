package com.example.amuntimilsina.bideshisawari.Helper;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;

public class GetNearbyPlace extends AsyncTask<Object,String,String>{

    String googlePlacesData;
    GoogleMap googleMap;
    String url;
    @Override
    protected String doInBackground(Object... objects) {
       googleMap= (GoogleMap) objects[0];
       url= (String) objects[1];
       DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googlePlacesData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
