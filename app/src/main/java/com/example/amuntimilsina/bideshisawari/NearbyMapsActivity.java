package com.example.amuntimilsina.bideshisawari;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.amuntimilsina.bideshisawari.Helper.DirectionsJSONParser;
import com.example.amuntimilsina.bideshisawari.fragments.DetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NearbyMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> rating = new ArrayList<>();
    private ArrayList<String> place = new ArrayList<>();
    private ArrayList<String> temperature = new ArrayList<>();
    private ArrayList<String> place_id = new ArrayList<>();
    private ArrayList<String> lat = new ArrayList<>();
    private ArrayList<String> lang = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();
    double mainlat=27.674436;
    double mainlang=85.365128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        rating=getIntent().getStringArrayListExtra("rating");
        place=getIntent().getStringArrayListExtra("place");
        place_id=getIntent().getStringArrayListExtra("place_id");
        lat=getIntent().getStringArrayListExtra("lat");
        lang=getIntent().getStringArrayListExtra("lang");
        temperature=getIntent().getStringArrayListExtra("temperature");
        photo=getIntent().getStringArrayListExtra("photo_reference");
        final LatLng mylocation=new LatLng(mainlat,mainlang);
        mMap.addMarker(new MarkerOptions().position(mylocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 14));
        for (int i=0;i<place.size();i++) {
            // Add a marker in Sydney and move the camera
            LatLng markers = new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lang.get(i)));
            mMap.addMarker(new MarkerOptions().position(markers).title(place.get(i)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final Double a = marker.getPosition().latitude;
                final Double b = marker.getPosition().longitude;
                final LatLng mylocation=new LatLng(mainlat,mainlang);
                LatLng loc = new LatLng(a, b);
                if (loc.equals(mylocation)) {
                    return true;
                }
                LayoutInflater inflater = LayoutInflater.from(NearbyMapsActivity.this);
                final View yourCustomView = inflater.inflate(R.layout.mapsmarkerlayout, null);
                Button direction, details;
                direction = yourCustomView.findViewById(R.id.direction);
                details = yourCustomView.findViewById(R.id.details);
                final AlertDialog dialog = new AlertDialog.Builder(NearbyMapsActivity.this)
                        .setView(yourCustomView).create();
                direction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // String rating,place,temperature;
                        LatLng latLng = new LatLng(a,b);
                        String url = getDirectionsUrl(mylocation, latLng);
                        DownloadTask downloadTask = new DownloadTask();
                        downloadTask.execute(url);
                        dialog.dismiss();
                    }
                });
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i=0;i<place.size();i++) {
                        if((a.equals(lat.get(i)))&&(b.equals(lang.get(i)))){
                        Intent intent=new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("rating",rating.get(i));
                        intent.putExtra("place",place.get(i));
                        intent.putExtra("temperature",temperature.get(i));
                        intent.putExtra("photo_reference",photo.get(i));
                        startActivity(intent);
                        }
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    Log.i("hellohello", "" + position);
                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
